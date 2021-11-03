package com.draw.canvas.labb3.shapes;

import com.draw.canvas.labb3.shapes.basicshapes.Circle;
import com.draw.canvas.labb3.shapes.basicshapes.Square;
import javafx.scene.paint.Color;

public class Shapes {
    public static Shape circleOf(Color color, double x, double y, double radius) {
        return new Circle(color, x, y, radius);
    }

    public static Shape squareOf(Color color, double x, double y, double length) {
        return new Square(color, x, y, length);
    }

    public static Shape copyOf(Shape shape) {

        if (shape instanceof Circle)
            return new Circle(shape.getColor(), shape.getX(), shape.getY(), ((Circle) shape).getRadius() * 2);
        else
            return new Square(shape.getColor(), shape.getX(), shape.getY(), ((Square) shape).getLength());

        //is default shape necessary as below OR just else new Square as above ...
//        else {
//            return new Circle(Color.BLACK, 0, 0, 0);
//        }
    }

    public static String toSvg(Shape shape) {
        if (shape instanceof Circle) {
            return svgCircle(shape).toString();
        } else
            return svgSquare(shape).toString();
    }

    private static StringBuilder svgCircle(Shape shape) {
        return new StringBuilder().append("<circle")
                .append(" cx=\"")
                .append(shape.getX())
                .append("\" cy=\"")
                .append(shape.getY())
                .append("\" r=\"")
                .append(((Circle) shape).getRadius())
                .append("\" fill=\"")
                .append(shape.getColor())
                .append("\" />");
    }

    private static StringBuilder svgSquare(Shape shape) {
        return new StringBuilder().append("<rect")
                .append(" x=\"")
                .append(shape.getX())
                .append("\" y=\"")
                .append(shape.getY())
                .append("\" width=\"")
                .append(((Square) shape).getLength())
                .append("\" height=\"")
                .append(((Square) shape).getLength())
                .append("\" fill=\"")
                .append(shape.getColor())
                .append("\" />");
    }


}
