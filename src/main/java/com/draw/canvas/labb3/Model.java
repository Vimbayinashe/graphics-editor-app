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

    //todo: a variable that holds ratio (size) of original shape size

    private final ObjectProperty<Color> color;
    private final ObjectProperty<ShapeOption> shape;
    ObservableList<Shape> shapes = FXCollections.observableArrayList();

    public Model() {
        this.color = new SimpleObjectProperty<>(Color.BLACK);
        this.shape = new SimpleObjectProperty<>(ShapeOption.CIRCLE);
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

    public ShapeOption getShape() {
        return shape.get();
    }

    public ObjectProperty<ShapeOption> shapeProperty() {
        return shape;
    }

    public void setShape(ShapeOption shape) {
        this.shape.set(shape);
    }

    public ObservableList<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(Shape shape) {
        shapes.add(shape);
    }

    //add getters & setters & property

    //test drawing & saving shape object  -> compare with calsswork first
}
