package com.draw.canvas.labb3.shapes;

import com.draw.canvas.labb3.shapes.basicshapes.Circle;
import com.draw.canvas.labb3.shapes.basicshapes.Square;
import javafx.scene.paint.Color;

import java.util.Arrays;

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

    public static Shape parseCircle(String line) {
        String[] attributes = line.split(" ");

        double x = parseAttribute(attributes, "cx");
        double y = parseAttribute(attributes, "cy");
        double diameter = parseAttribute(attributes, "r=") * 2;
        Color color = parseColorAttribute(attributes);

        return circleOf(color, x, y, diameter);
    }

    public static Shape parseSquare(String line) {
        String[] attributes = line.split(" ");

        double x = parseAttribute(attributes, "x=");
        double y = parseAttribute(attributes, "y=");
        double length = parseAttribute(attributes, "width");
        Color color = parseColorAttribute(attributes);

        return squareOf(color, x, y, length);
    }

    private static double parseAttribute(String[] attributes, String name) {
        String substring = Arrays.stream(attributes)
                .filter(string -> string.contains(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        return getValue(substring);
    }

    private static double getValue(String attribute) {
        return Double.parseDouble(attribute.substring(attribute.indexOf("\"") + 1, attribute.lastIndexOf("\"")));
    }

    private static Color parseColorAttribute(String[] attributes) {
        String color = Arrays.stream(attributes)
                .filter(attribute -> attribute.contains("fill"))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        return Color.web(color.substring(color.indexOf("\"") + 1, color.lastIndexOf("\"")));
    }

}
