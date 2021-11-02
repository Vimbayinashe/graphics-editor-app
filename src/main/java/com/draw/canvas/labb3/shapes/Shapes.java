package com.draw.canvas.labb3.shapes;

import com.draw.canvas.labb3.shapes.basicshapes.Circle;
import com.draw.canvas.labb3.shapes.basicshapes.Square;
import javafx.scene.paint.Color;

public class Shapes {
    public static Shape circleOf(Color color, double x, double y, double radius) {
        return  new Circle(color, x, y, radius);
    }

    public static Shape squareOf(Color color, double x, double y, double length) {
        return  new Square(color, x, y, length);
    }

    public static Shape copyOf(Shape shape) {

        if(shape instanceof Circle)
            return new Circle(shape.getColor(), shape.getX(), shape.getY(), ((Circle) shape).getRadius() * 2);
        else if(shape instanceof Square)
            return new Square(shape.getColor(), shape.getX(), shape.getY(), ((Square) shape).getLength());

        // can default Shape be avoided?
        return new Circle(Color.BLACK, 0, 0, 20);
    }

}
