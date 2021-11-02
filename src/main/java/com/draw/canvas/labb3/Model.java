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



    private Socket socket;
    private PrintWriter writer;         //
    private BufferedReader reader;      //reads from server
    public BooleanProperty connected = new SimpleBooleanProperty();
    ExecutorService executorService;

    public void connect() {
        try{
            socket = new Socket("192.168.1.137", 8000);
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);


            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            connected.set(true);
            System.out.println("Connected to server on port 8000");

            //write to console
            // update shapes in model & re-draw canvas
            // send information to server

            executorService = Executors.newSingleThreadExecutor();  //use factory thread object; re-usable (threads use up lot of memory)
            executorService.submit(this::incomingNetworkHandler);

            /*
            new Thread(() -> {
                while (true) {
                    String line = reader.readLine();    // reads a line of text
                    System.out.println(line);
                }
            });

             */

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void incomingNetworkHandler() {
        try {
            while (true) {
                String line = reader.readLine();
                System.out.println(line);

                //parse incoming shape SVG format -> Shape object
                //shapes.add(parsedShape)

                //avoid interfering with JavaFX GUI thread in the separate ExecutorService here => use Platform .runLater
                //  ->  sends to JavaFX Platform Thread queue   todo: (add Notes to other file)

                //add fException handling for x, y , color, radius (double values)
                Platform.runLater(() ->
                        shapes.add(Shapes.circleOf(Color.BLUEVIOLET, Math.random() * 800, Math.random() * 600,20.0))  //dummy data
                );

                //todo: why am I creating two new shapes for each click?

            }
        } catch (IOException e) {
            System.out.println("Disconnected from server - I/O Error");
            Platform.runLater(() -> connected.set(false));  //because connected to GUI

        }
    }

    public void disconnect() {      //Add a disconnect from server button
        try {
            socket.close();
            connected.set(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //add separate executor service for "sendToServer" -> avoid lag experience
    public void sendTosServer(Shape shape) {
        if(connected.get()) {
            writer.println("Created a new shape with co-ords, x: " + shape.getX() + ", y: " + shape.getY());
            //send svg format
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
}
