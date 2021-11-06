package com.draw.canvas.labb3;

import com.draw.canvas.labb3.shapes.Shape;
import com.draw.canvas.labb3.shapes.ShapeOption;
import com.draw.canvas.labb3.shapes.Shapes;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;


import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class Model {

    private final ObjectProperty<Color> color;
    private final ObjectProperty<ShapeOption> shape;
    private final ObjectProperty<Action> action;
    private final DoubleProperty size;
    private final ObjectProperty<Double> sizeRatio;
    private final Deque<List<Shape>> undoList = new ArrayDeque<>();
    private final Deque<List<Shape>> redoList = new ArrayDeque<>();
    ObservableList<Shape> shapes = FXCollections.observableArrayList();
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    public BooleanProperty connected = new SimpleBooleanProperty();
    private ExecutorService executorService;


    public Model() {
        this.color = new SimpleObjectProperty<>(Color.BLACK);
        this.shape = new SimpleObjectProperty<>(ShapeOption.CIRCLE);
        this.action = new SimpleObjectProperty<>(Action.DRAWCIRCLE);
        this.size = new SimpleDoubleProperty(20.0);
        this.sizeRatio = new SimpleObjectProperty<>(1.0);
    }

    public Color getColor() {
        return color.get();
    }

    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public ShapeOption getShapeOption() {
        return shape.get();
    }

    public ObjectProperty<ShapeOption> shapeOptionProperty() {
        return shape;
    }

    public void setShapeOption(ShapeOption shape) {
        this.shape.set(shape);
    }

    public List<Shape> getShapes() {
        return shapes.stream()
                .map(Shapes::copyOf)
                .toList();
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = FXCollections.observableArrayList(shapes);
    }

    public void saveCurrentStatus() {
        undoList.addLast(getShapes());
        redoList.clear();
    }

    public void undo() {
        if (undoList.isEmpty())
            return;
        redoList.addLast(getShapes());

        List<Shape> previousState = undoList.removeLast();
        setShapes(previousState);
    }

    public void redo() {
        if (redoList.isEmpty())
            return;
        undoList.addLast(getShapes());

        List<Shape> redoState = redoList.removeLast();
        setShapes(redoState);
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public double getSize() {
        return size.get();
    }

    public DoubleProperty sizeProperty() {
        return size;
    }

    public void setSize(double size) {
        this.size.set(size);
    }

    public double getSizeRatio() {
        return sizeRatio.get();
    }

    public ObjectProperty<Double> sizeRatioProperty() {
        return sizeRatio;
    }

    public void setSizeRatio(double sizeRatio) {
        this.sizeRatio.set(sizeRatio);
    }

    public Action getAction() {
        return action.get();
    }

    public ObjectProperty<Action> actionProperty() {
        return action;
    }

    public void setAction(Action action) {
        this.action.set(action);
    }

    public Optional<Shape> getSelectedShape(double x, double y) {
        return shapes.stream()
                .filter(shape -> shape.isInside(x, y))
                .reduce((first, second) -> second);
    }

    public boolean getConnected() {
        return connected.get();
    }

    public void connect() {
        try {
            socket = new Socket("178.174.162.51", 8000);    //todo: change to localhost
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            connected.set(true);
            System.out.println("Connected to server on port 8000");

            executorService = Executors.newSingleThreadExecutor();
            executorService.submit(this::incomingNetworkHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean skipLine(String line) {
        return line.toLowerCase().contains("server");
    }

    private void incomingNetworkHandler() {
        String line;
        try {
            while (true) {
                line = reader.readLine();
                if(skipLine(line))
                    continue;
                handleInput(line);
            }
        } catch (IOException e) {
            System.out.println("Disconnected from server - I/O Error");
            Platform.runLater(() -> connected.set(false));
        }
    }

    private void handleInput(String line) {
        String trimmedLine = line.substring(line.indexOf("]") + 1).trim().toLowerCase();

        if(trimmedLine.contains("rect") || trimmedLine.contains("circle"))
            convertToShape(trimmedLine);
        else
            System.out.println("Svg format not recognised: " + trimmedLine);
    }

    private void convertToShape(String trimmedLine) {
        try {
            Shape shape = parseShape(trimmedLine);
            Platform.runLater(() -> shapes.add(shape));
        } catch (Exception e) {
            System.out.println("Error in line: " + trimmedLine);
            e.printStackTrace();
        }
    }

    private Shape parseShape(String trimmedLine) {
        if(trimmedLine.contains("rect"))
            return Shapes.parseSquare(trimmedLine);
        else
            return Shapes.parseCircle(trimmedLine);
    }

    public void disconnect() {
        try {
            socket.close();
            connected.set(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToServer(String svg) {
        if (connected.get()) {
            writer.println(svg);
        }
    }

    private List<String> shapesAsSvgList() {
        return shapes.stream()
                .map(Shapes::toSvg)
                .toList();
    }

    private List<String> svgData() {
        List<String> list = new ArrayList<>();
        List<String> shapesAsSvgList = shapesAsSvgList();

        list.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        list.add("<svg viewBox=\"0 0 1150 700\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        list.addAll(shapesAsSvgList);
        list.add("</svg>");

        return list;
    }

    public void save(MenuItem button) {
        List<String> svgData =svgData();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName("canvas");

        File file = fileChooser.showSaveDialog(button.getParentPopup().getScene().getWindow());

        if(file == null)
            return;
        saveFile(svgData, file);
    }

    private void saveFile(List<String> list, File file) {
        Path path = getPath(file);

        try {
            Files.write(path, list, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path getPath(File file) {
        return Path.of(file.getPath().concat(".svg"));
    }

    public void importFile(MenuItem button) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Vector Files", "*.svg"));

        File file = fileChooser.showOpenDialog(button.getParentPopup().getScene().getWindow());

        if(file == null)
            return;

        List<String> fileContents = readFile(file);
        drawImportedShapes(fileContents);
    }

    private void drawImportedShapes(List<String> fileContents) {
        fileContents.stream()
                .filter(this::isSvgShape)
                .forEach(this::convertToShape);
    }

    private List<String> readFile(File file) {
        Path path = file.toPath();

        try(Stream<String> contents = Files.lines(path)) {
            return contents.toList();
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private boolean isSvgShape(String line) {
        return !line.contains("svg") && !line.contains("xml") && !line.isEmpty();
    }
}
