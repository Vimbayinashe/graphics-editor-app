package com.draw.canvas.labb3;

import com.draw.canvas.labb3.shapes.Shape;
import com.draw.canvas.labb3.shapes.ShapeOption;
import com.draw.canvas.labb3.shapes.Shapes;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        this.shapes = FXCollections.observableArrayList(shapes);      //todo: do I need new arrayList here?
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

    public void connect() {
        try {
            socket = new Socket("192.168.1.137", 8000);
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);


            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            connected.set(true);
            System.out.println("Connected to server on port 8000");

            //todo: send information to server a) here b)  Controller (where sendToServer is called)

            //    -> a new variable called newShape ? =>

            executorService = Executors.newSingleThreadExecutor();  //use factory thread object; re-usable (threads use up lot of memory)
            executorService.submit(this::incomingNetworkHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void incomingNetworkHandler() {
        try {
            while (true) {
                String line = reader.readLine();
                System.out.println(line);       //remove

                Platform.runLater(() ->
                        //parse incoming shape SVG format -> Shape object
                        //shapes.add(parsedShape)
                        //add Exception handling for x, y , color, radius (double values)

                        // update shapes in model & re-draw canvas
                        shapes.add(Shapes.circleOf(Color.BLUEVIOLET, Math.random() * 800, Math.random() * 600, 20.0))  //dummy data

                        //avoid re-writing your own shapes [YOU] OR only write shapes once message received from server for all users
                        // => remove all shapes signed [you] from server
                );

            }
        } catch (IOException e) {
            System.out.println("Disconnected from server - I/O Error");
            Platform.runLater(() -> connected.set(false));

        }
    }

    public void disconnect() {
        try {
            socket.close();
            connected.set(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //add separate executor service for "sendToServer" -> avoid lag experience
    public void sendToServer(Shape shape) {
        if (connected.get()) {
            writer.println(Shapes.toSvg(shape));
        }
    }


    /*
     * send shapes between server & client via svg format
     * reconvert to Shape object & vice versa
     *
     * avoid re-writing your own shapes [YOU] OR only write shapes once message received from server for all users
     */


    //add getters & setters & property

    //test drawing & saving shape object  -> compare with classwork first

    public void save() {
        String openingTag = """
                    <svg viewBox="0 0 1150 700" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:ev="http://www.w3.org/2001/xml-events">
                """;
        String closingTag = "</svg>";



    }
}
