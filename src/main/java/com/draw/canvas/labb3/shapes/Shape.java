package com.draw.canvas.labb3.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    private double x;
    private double y;
    private Color color;

    public Shape(Color color, double x, double y) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public  abstract void draw(GraphicsContext graphicsContext);

    public abstract boolean isInside(double x, double y);

    public abstract void setDimensions(double factor);

}

