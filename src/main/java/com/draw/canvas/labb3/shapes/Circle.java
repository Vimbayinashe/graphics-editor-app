package com.draw.canvas.labb3.shapes;

import javafx.scene.paint.Color;

import java.util.Objects;

public class Circle extends Shape{
    private double radius;

    public Circle(Color color, double x, double y, double radius) {
        super(color, x, y);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    // dropdown menu option to have x2, x3 , x0.5, x 0.75 of shape size
    public void setRadius(double factor) {
        this.radius *= factor;
    }

    //todo
    public void draw() {

    }

    //todo
    public boolean isInside(double x, double y) {


        return false;
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
