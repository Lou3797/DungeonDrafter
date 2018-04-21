package command;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class DrawCommand implements Command {
    private Image snapshot;
    private Canvas parent;
    private Rectangle2D viewPort;

    public DrawCommand(Canvas scratch, Canvas parent, Rectangle2D viewPort) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        //params.setViewport(boundingBox);
        Image snapshot = scratch.snapshot(params, null);
    }

    @Override
    public boolean execute() {

        return false;
    }

    @Override
    public boolean unexecute() {

        return false;
    }

    @Override
    public String getCommandName() {
        return "DrawCommand";
    }
}
