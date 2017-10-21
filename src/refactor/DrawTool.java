package refactor;

import command.DrawCommand;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.awt.Point;

public abstract class DrawTool {
    private SnapshotParameters sp;

    DrawTool() {
        this.sp = new SnapshotParameters();
        this.sp.setFill(Color.TRANSPARENT);
    }

    void mousePress(MouseEvent event, Canvas layer) {

    }

    void mouseDrag(MouseEvent event) {

    }

    void mouseRelease(MouseEvent event) {

    }

    abstract void drawStep(MouseEvent event);

}
