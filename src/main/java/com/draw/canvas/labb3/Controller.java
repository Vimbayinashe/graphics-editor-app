package com.draw.canvas.labb3;

import com.draw.canvas.labb3.shapes.Circle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class Controller {


    Model model;

    @FXML
    private Canvas canvas;
    @FXML
    private StackPane canvasParent;

    public Controller() {}

    public Controller(Model model) {
        this.model = model;
    }

    public void initialize() {
        model = new Model();

        //add canvas Listener to re-draw when re-sized
        canvas.widthProperty().addListener(observable -> draw());
        canvas.heightProperty().addListener(observable -> draw());
    }

    private void draw() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        for (var shape : model.observableShapes) {
            shape.draw(graphicsContext);
        }
    }

    @FXML
    public void onCanvasClicked(MouseEvent event) {


//        model.observableShapes.add(new Circle(model.getColor(), event.getX(), event.getY(), 10.0));
//        //addNewShape(event);
//        draw();
    }

    private void addNewShape(MouseEvent event) {
//        switch (model.getShape()) {
//            case CIRCLE -> model.shapes.add(Shapes.circleOf(model.getColor(), event.getX(), event.getY(), 10.0));
//            case RECTANGLE ->  model.shapes.add(Shapes.rectangleOf(model.getColor(), event.getX(), event.getY(), 50.0, 20.0));
//            case TRIANGLE -> model.shapes.add(Shapes.triangleOf(model.getColor(), event.getX(), event.getY()));
//        }
    }

    public void onCircleSelected(ActionEvent actionEvent) {
        System.out.println(actionEvent);
    }

    public void canvasClicked(MouseEvent mouseEvent) {
        System.out.println("on Canvas clicked!");

        model.observableShapes.add(new Circle(model.getColor(), mouseEvent.getX(), mouseEvent.getY(), 10.0));
        //addNewShape(event);
        draw();
    }
}

//todo
/*
*  Shapes.of Factory class
*  add size object to Model
 */