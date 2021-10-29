package com.draw.canvas.labb3;

import com.draw.canvas.labb3.shapes.Shape;
import com.draw.canvas.labb3.shapes.ShapeOption;
import com.draw.canvas.labb3.shapes.Shapes;
import com.draw.canvas.labb3.shapes.basicshapes.Circle;
import com.draw.canvas.labb3.shapes.basicshapes.Square;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.Optional;

public class Controller {


    public Spinner<Double> sizeSpinner;
    public Label sizeLabel;
    public Label colorLabel;
    Model model;

    @FXML
    private Canvas canvas;
    @FXML
    private StackPane canvasParent;
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

        //add canvas Listener to re-draw when re-sized IF ResizableCanvas implemented
//        canvas.widthProperty().addListener(observable -> draw());
//        canvas.heightProperty().addListener(observable -> draw());
    }

    public void canvasClicked(MouseEvent event) {
        performAction(event);
        draw();
    }

    private void performAction(MouseEvent event) {
        switch (model.getAction()) {
            case DRAWCIRCLE, DRAWSQUARE -> addNewShape(event);
            case CHANGECOLOR -> changeShapeColor(event);
            case CHANGESIZE -> changeShapeSize(event);
        }
    }

    private void changeShapeSize(MouseEvent event) {
        System.out.println(length());
        Optional<Shape> selectedShape = model.getSelectedShape(event.getX(), event.getY());
        selectedShape.ifPresent(shape -> shape.setDimensions(length()));

//        selectedShape.ifPresent(shape -> {
//            shape.setDimensions(length());
//
//            if(shape instanceof Circle)
//                System.out.println("Length: " + ((Circle) shape).getRadius());
//            if(shape instanceof Square)
//                System.out.println("Length: " + ((Square) shape).getLength());
//        });
    }

    private void changeShapeColor(MouseEvent event) {
        Optional<Shape> selectedShape = model.getSelectedShape(event.getX(), event.getY());
        selectedShape.ifPresent(shape -> shape.setColor(model.getColor()));
    }

    private void draw() {

        //re-draw smaller shapes a) return gc here & reset it before each re-drawing; b) clear "global" gc declared in Controller
        //How to clear graphicsContext?

        //add event listener to re-draw canvas when any shape's fields in model.shapes changes???

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        for (var shape : model.shapes) {
            shape.draw(graphicsContext);
        }
    }

    private void addNewShape(MouseEvent event) {
        switch (model.getShape()) {
            case CIRCLE -> addCircle(event);
            case SQUARE -> addSquare(event);
        }
    }

    private void addSquare(MouseEvent event) {
        model.shapes.add(Shapes.squareOf(model.getColor(), event.getX(), event.getY(), length()));
    }

    private void addCircle(MouseEvent event) {
        model.shapes.add(Shapes.circleOf(model.getColor(), event.getX(), event.getY(), length()));
    }

    private double length() {
        return model.getSize() * model.getSizeRatio();
    }

    public void drawCircle() {
        model.setAction(Action.DRAWCIRCLE);
        model.setShape(ShapeOption.CIRCLE);
    }

    public void drawSquare() {
        model.setAction(Action.DRAWSQUARE);
        model.setShape(ShapeOption.SQUARE);
    }

    public void changeSize() {
        model.setAction(Action.CHANGESIZE);
    }

    public void changeColor() {
        model.setAction(Action.CHANGECOLOR);
    }

    //todo:

    //1. use an enum to determine actions
    //switch: case editColor ->  shape.setColor && shape.draw()

    // case changeSize ->  shape.setX && shape.setY && shape.draw();   use drag functionality

}


//todo maybe
/*
*  Shapes Builder
*  model.shapes.add(Circle.getBuilder().setColor(model.getColor()).setCoords(x, y).setRadius(radius()) => returns a Shape object
*
 */