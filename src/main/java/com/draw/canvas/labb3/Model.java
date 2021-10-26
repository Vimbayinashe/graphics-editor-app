package com.draw.canvas.labb3;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

public class Model {

    //color //shape //co-ords

    private final ObjectProperty<Color> color;
    private final ObjectProperty<ShapeOption> selectedShape;
    ObservableList<Shape> observableShapes = FXCollections.observableArrayList();

    public Model() {
        this.color = new SimpleObjectProperty<>(Color.BLACK);
        this.selectedShape = new SimpleObjectProperty<>(ShapeOption.CIRCLE);
    }

    //add getters & setters & property

    //test drawing & saving shape object  -> compare with calsswork first
}
