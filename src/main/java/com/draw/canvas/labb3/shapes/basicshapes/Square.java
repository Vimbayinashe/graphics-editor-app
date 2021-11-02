package com.draw.canvas.labb3.shapes.basicshapes;

import com.draw.canvas.labb3.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Square extends Shape {
    private double length;

    public Square(Color color, double x, double y, double length) {
        super(color, x, y);
        this.length = length;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(getColor());
        graphicsContext.fillRect(getX() - length / 2, getY() - length / 2, length, length);
    }

    public boolean isInside(double x, double y) {
        double graphicalX = getX() - length / 2;
        double graphicalY = getY() - length / 2;

        boolean withinX = graphicalX <= x && x <= graphicalX + length;
        boolean withinY = graphicalY <= y && y <= graphicalY + length;

        return withinX && withinY;
    }

    @Override
    public void setDimensions(double length) {
        setLength(length);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Square square = (Square) o;
        return Double.compare(square.length, length) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), length);
    }

    @Override
    public String toString() {
        return "Square{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", color=" + getColor() +
                "length=" + length +
                '}';
    }
}
