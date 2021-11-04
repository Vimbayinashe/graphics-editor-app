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
            return circleToSvg(shape).toString();
        } else
            return squareToSvg(shape).toString();
    }

    private static StringBuilder circleToSvg(Shape shape) {
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

    private static StringBuilder squareToSvg(Shape shape) {
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
//                .append("rgb(")
//                .append(shape.getColor().getRed())
//                .append(", ")
//                .append(shape.getColor().getGreen())
//                .append(", ")
//                .append(shape.getColor().getBlue())
//                .append(")")
                .append("\" />");
    }

    public static Shape parseCircle(String line){
        double x = 0, y = 0, diameter = 0;
        Color color = Color.BLACK;

        try {
            x = parseAttribute(line, "cx");
            y = parseAttribute(line, "cy");
            diameter = parseAttribute(line, "r") * 2;
            color = parseColorAttribute(line);
        } catch (Exception e) {
            e.printStackTrace();
        }

       return circleOf(color, x, y, diameter);
    }

    public static Shape parseSquare(String line){
        double x = 0, y = 0, length = 0;
        Color color = Color.BLACK;

        try {
            x = parseAttribute(line, "x");
            y = parseAttribute(line, "y");
            length = parseAttribute(line, "width");
            color = parseColorAttribute(line);
        } catch (Exception e) {
            e.printStackTrace();
        }

       return squareOf(color, x, y, length);
    }

    private static double parseAttribute(String line, String attribute) {
        int index = line.indexOf(attribute);
        String substring = line.substring(index, line.indexOf(" "));
        return Double.parseDouble(substring.substring(substring.indexOf("\"") + 1, substring.lastIndexOf("\"")));
    }

    private static Color parseColorAttribute(String line) {
        int index = line.indexOf("fill");
        String substring = line.substring(index, line.lastIndexOf("\"") );
        return Color.web(substring.substring(substring.indexOf("\"") + 1));
    }
}
