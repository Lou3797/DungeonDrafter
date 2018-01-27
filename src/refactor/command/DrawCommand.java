package refactor.command;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class DrawCommand implements Command {
    private Rectangle2D bounds;
    private Canvas parent;
    private WritableImage before;
    private WritableImage after;

    public DrawCommand(Rectangle2D bounds, Canvas parent, Canvas scratch) {
        System.out.println(bounds);

        this.bounds = bounds;
        this.parent = parent;
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);
        sp.setViewport(this.bounds);
        this.before = this.parent.snapshot(sp, null);
        this.after = scratch.snapshot(sp, null);
    }

    @Override
    public boolean execute() {
        return overwriteParentCanvas(after);
    }

    @Override
    public boolean unexecute() {
        return overwriteParentCanvas(before);
    }

    private boolean overwriteParentCanvas(WritableImage overwrite) {
        //parent.setBlendMode(BlendMode.SRC_OVER);
        //parent.getGraphicsContext2D().clearRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
        parent.getGraphicsContext2D().drawImage(overwrite, bounds.getMinX(), bounds.getMinY());
        return true;

    }

    @Override
    public String getCommandName() {
        return "DrawCommand";
    }
}