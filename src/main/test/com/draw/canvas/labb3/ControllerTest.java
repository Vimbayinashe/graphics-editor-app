package com.draw.canvas.labb3;

import com.draw.canvas.labb3.shapes.Shape;
import com.draw.canvas.labb3.shapes.ShapeOption;
import com.draw.canvas.labb3.shapes.Shapes;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    Model model = new Model();
    Controller controller = new Controller(model);
    Shape shape = Shapes.squareOf(Color.BLUEVIOLET, 25.6, 45.7, 15.0);

    @Test
    void drawCircleSetsActionToDrawCircleAndShapeOptionToCircle() {
        controller.drawCircle();

        assertEquals(Action.DRAWCIRCLE, model.getAction());
        assertEquals(ShapeOption.CIRCLE, model.getShapeOption());
    }

    @Test
    void setShapesReturnsShapesListWithSameLengthAsAddedList() {
        List<Shape> newShapes = List.of(
                Shapes.circleOf(Color.BLACK, 10.0, 10.0, 20),
                Shapes.circleOf(Color.PINK, 100, 100, 30),
                Shapes.squareOf(Color.RED, 10, 100, 30),
                Shapes.squareOf(Color.WHITESMOKE, 200, 100, 40)
        );
        model.setShapes(newShapes);

        assertEquals(newShapes.size(), model.getShapes().size());
    }

    @Test
    void callingUndoRemovesTheLastAddedShape() {
        model.saveCurrentStatus();
        model.addShape(shape);

        model.undo();

        assertEquals(0, model.getShapes().size());
        assertFalse(model.getShapes().contains(shape));
    }


    @Test
    void  callingRedoReversesUndoneChanges() {
        model.saveCurrentStatus();
        model.addShape(shape);

        model.undo();
        model.redo();

        assertEquals(1, model.getShapes().size());
        assertTrue(model.shapes.contains(shape));
    }

}