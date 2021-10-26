package com.draw.canvas.labb3;

import com.draw.canvas.labb3.shapes.Shape;
import com.draw.canvas.labb3.shapes.ShapeOption;
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

    public Color getColor() {
        return color.get();
    }

    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public ShapeOption getSelectedShape() {
        return selectedShape.get();
    }

    public ObjectProperty<ShapeOption> selectedShapeProperty() {
        return selectedShape;
    }

    public void setSelectedShape(ShapeOption selectedShape) {
        this.selectedShape.set(selectedShape);
    }

    public ObservableList<Shape> getObservableShapes() {
        return observableShapes;
    }

    public void setObservableShapes(Shape shape) {
        observableShapes.add(shape);
    }

    //add getters & setters & property

    //test drawing & saving shape object  -> compare with calsswork first
}
