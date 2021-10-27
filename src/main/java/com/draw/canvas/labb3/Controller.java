package com.draw.canvas.labb3;

import com.draw.canvas.labb3.shapes.ShapeOption;
import com.draw.canvas.labb3.shapes.Shapes;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class Controller {


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

        //add canvas Listener to re-draw when re-sized      //may not be necessary!
        canvas.widthProperty().addListener(observable -> draw());
        canvas.heightProperty().addListener(observable -> draw());
    }

    public void canvasClicked(MouseEvent event) {
        addNewShape(event);
        draw();
    }

    private void draw() {
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

    private double length() {
        return model.getSize() * model.getSizeRatio() * 2;
    }

    private void addCircle(MouseEvent event) {
        model.shapes.add(Shapes.circleOf(model.getColor(), event.getX(), event.getY(), radius()));
    }

    private double radius() {
        return model.getSize() * model.getSizeRatio();
    }

    public void onCircleSelected() {
        model.setShape(ShapeOption.CIRCLE);
    }

    public void onSquareSelected() {
        model.setShape(ShapeOption.SQUARE);
    }
}

//todo
/*
*  Shapes.of Factory class
*  add size object to Model
 */