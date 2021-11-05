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
                .append(formatColor(shape.getColor()))
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
                .append(formatColor(shape.getColor()))
                .append("\" />");
    }

    private static String formatColor(Color color) {
        return "#" + color.toString().substring(2);
    }

    public static Shape parseCircle(String line) throws IllegalArgumentException {
        String substring = line.substring(8);
        double x = parseAttribute(substring, "cx");
        double y = parseAttribute(substring, "cy");
        double diameter = parseAttribute(substring, "r") * 2;
        Color color = parseColorAttribute(substring);

        return circleOf(color, x, y, diameter);
    }

    public static Shape parseSquare(String line) throws IllegalArgumentException {
        String substring = line.substring(6);
        double x = parseAttribute(substring, "x");
        double y = parseAttribute(substring, "y");
        double length = parseAttribute(substring, "width");
        Color color = parseColorAttribute(substring);

        return squareOf(color, x, y, length);
    }

    private static double parseAttribute(String line, String attribute) {
        int index = line.indexOf(attribute);
        System.out.println(line);
        String substring = line.substring(index, line.indexOf(" "));
        try {
            return Double.parseDouble(substring.substring(substring.indexOf("\"") + 1, substring.lastIndexOf("\"")));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid value in attribute - " + attribute);
        }
    }

    private static Color parseColorAttribute(String line) {
        int index = line.indexOf("fill");
        String substring = line.substring(index, line.lastIndexOf("\""));
        try {
            return Color.web(substring.substring(substring.indexOf("\"") + 1));
        } catch (Exception e) {
            throw new IllegalArgumentException("Value of fill attribute could not be converted to a valid color");
        }
    }
}
