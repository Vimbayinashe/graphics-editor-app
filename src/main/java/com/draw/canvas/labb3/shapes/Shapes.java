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

}
