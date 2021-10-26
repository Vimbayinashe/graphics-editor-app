package com.draw.canvas.labb3.shapes;

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

    public Square setLength(double length) {
        this.length = length;
        return this;
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
                "length=" + length +
                '}';
    }
}
