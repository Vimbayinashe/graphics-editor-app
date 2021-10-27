package com.draw.canvas.labb3;

import com.draw.canvas.labb3.shapes.ShapeOption;
import com.draw.canvas.labb3.shapes.Shapes;
import com.draw.canvas.labb3.shapes.basicshapes.Circle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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
            case CIRCLE -> model.shapes.add(Shapes.circleOf(model.getColor(), event.getX(), event.getY(), 10.0));
            case SQUARE ->  model.shapes.add(Shapes.squareOf(model.getColor(), event.getX(), event.getY(), 20.0));
        }
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