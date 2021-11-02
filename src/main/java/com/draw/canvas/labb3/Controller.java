package com.draw.canvas.labb3;

import com.draw.canvas.labb3.shapes.Shape;
import com.draw.canvas.labb3.shapes.ShapeOption;
import com.draw.canvas.labb3.shapes.Shapes;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

public class Controller {

    private Model model;
    private GraphicsContext graphicsContext;

    @FXML
    private Spinner<Double> sizeSpinner;
    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colorPicker;


    public Controller() {}

    public Controller(Model model) {
        this.model = model;
    }

    public void initialize() {
        model = new Model();

        colorPicker.valueProperty().bindBidirectional(model.colorProperty());
        sizeSpinner.getValueFactory().valueProperty().bindBidirectional(model.sizeRatioProperty());

        //undoMenuItem.setDisable(model.statusIsEmpty());
        //undoMenuItem.disableProperty().bind(model.statusIsEmpty());

        graphicsContext = canvas.getGraphicsContext2D();

        model.shapes.addListener((ListChangeListener<Shape>) change ->  {
            draw();
        });

        //add canvas Listener to re-draw when re-sized IF ResizableCanvas implemented
//        canvas.widthProperty().addListener(observable -> draw());
//        canvas.heightProperty().addListener(observable -> draw());
    }

    public void canvasClicked(MouseEvent event) {
        performAction(event);
        draw();
    }

    private void performAction(MouseEvent event) {
        model.saveCurrentStatus();

        switch (model.getAction()) {
            case DRAWCIRCLE, DRAWSQUARE -> addNewShape(event);
            case CHANGECOLOR -> changeShapeColor(event);
            case CHANGESIZE -> changeShapeSize(event);
        }
    }

    private void changeShapeSize(MouseEvent event) {
        Optional<Shape> selectedShape = model.getSelectedShape(event.getX(), event.getY());
        selectedShape.ifPresent(shape -> shape.setDimensions(length()));
    }

    private void changeShapeColor(MouseEvent event) {
        Optional<Shape> selectedShape = model.getSelectedShape(event.getX(), event.getY());
        selectedShape.ifPresent(shape -> shape.setColor(model.getColor()));
    }

    private void draw() {

        graphicsContext.clearRect(0 , 0, canvas.getWidth(), canvas.getHeight());

        for (var shape : model.shapes) {
            shape.draw(graphicsContext);
        }
    }

    private void addNewShape(MouseEvent event) {
        Shape shape = getNewShape(event);

        model.shapes.add(shape);
        model.sendTosServer(shape);
    }

    private Shape getNewShape(MouseEvent event) {
        return switch (model.getShapeOption()) {
            case CIRCLE -> Shapes.circleOf(model.getColor(), event.getX(), event.getY(), length());
            case SQUARE -> Shapes.squareOf(model.getColor(), event.getX(), event.getY(), length());
        };
    }

    private double length() {
        return model.getSize() * model.getSizeRatio();
    }

    public void drawCircle() {
        model.setAction(Action.DRAWCIRCLE);
        model.setShapeOption(ShapeOption.CIRCLE);
    }

    public void drawSquare() {
        model.setAction(Action.DRAWSQUARE);
        model.setShapeOption(ShapeOption.SQUARE);
    }

    public void changeSize() {
        model.setAction(Action.CHANGESIZE);
    }

    public void changeColor() {
        model.setAction(Action.CHANGECOLOR);
    }

    public void connectToServer() {
        model.connect();
    }

    public void disconnectFromServer() {
        model.disconnect();
    }

    public void undo() {
        model.undo();
        draw();
    }

    public void redo() {
        model.redo();
        draw();
    }



}


//todo: maybe
/*
*  Shapes Builder
*  model.shapes.add(Circle.getBuilder().setColor(model.getColor()).setCoords(x, y).setRadius(radius()) => returns a Shape object
*
*  disable Undo button when model.status.isEmpty(),     // requires a SimpleBooleanProperty
 */