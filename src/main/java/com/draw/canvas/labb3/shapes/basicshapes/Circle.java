package com.draw.canvas.labb3.shapes.basicshapes;

import com.draw.canvas.labb3.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Circle extends Shape {
    private double radius;

    public Circle(Color color, double x, double y, double length) {
        super(color, x, y);
        this.radius = length / 2;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double length) {
        radius = length / 2;
    }

    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(getColor());
        graphicsContext.fillOval(getX() - radius, getY() - radius, radius * 2, radius * 2);
    }

    public boolean isInside(double x, double y) {
        double dx = x - getX();
        double dy = y - getY();

        double distanceFromCenterSquared = dx * dx + dy * dy;

        return distanceFromCenterSquared <= radius * radius;
    }

    @Override
    public void setDimensions(double length) {
        setRadius(length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Circle circle = (Circle) o;
        return Double.compare(circle.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), radius);
    }

    @Override
    public String toString() {
        return "Circle{" +
                "radius=" + radius +
                '}';
    }
}
