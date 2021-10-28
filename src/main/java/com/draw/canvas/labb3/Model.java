package com.draw.canvas.labb3;

import com.draw.canvas.labb3.shapes.Shape;
import com.draw.canvas.labb3.shapes.ShapeOption;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.util.Collections;
import java.util.Optional;

public class Model {

    private final ObjectProperty<Color> color;
    private final ObjectProperty<ShapeOption> shape;
    private final ObjectProperty<Action> action;
    private final DoubleProperty size;
    private final DoubleProperty sizeRatio;

    ObservableList<Shape> shapes = FXCollections.observableArrayList();

    public Model() {
        this.color = new SimpleObjectProperty<>(Color.BLACK);
        this.shape = new SimpleObjectProperty<>(ShapeOption.CIRCLE);
        this.action = new SimpleObjectProperty<>(Action.DRAWCIRCLE);
        this.size = new SimpleDoubleProperty(10.0);
        this.sizeRatio = new SimpleDoubleProperty(1.0);
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

    public DoubleProperty sizeRatioProperty() {
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


    //add getters & setters & property

    //test drawing & saving shape object  -> compare with calsswork first
}
