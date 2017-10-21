package command;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.awt.*;

public class DrawCommand implements Command {
    private Point upperLeft;
    private Point bottomRight;
    private Canvas parent;
    private Canvas before;
    private Canvas after;
    //private SnapshotParameters sp;

    public DrawCommand(Point upperLeft, Point bottomRight, Canvas owner, PixelReader snapshotReader) {
        this.parent = owner;
        this.upperLeft = upperLeft;
        this.bottomRight = bottomRight;
        //this.sp = new SnapshotParameters();
        //this.sp.setFill(Color.TRANSPARENT);
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);
        before = new Canvas(bottomRight.getX() - upperLeft.getX(), bottomRight.getY() - upperLeft.getY());
        after = new Canvas(bottomRight.getX() - upperLeft.getX(), bottomRight.getY() - upperLeft.getY());

        PixelWriter beforeWriter = before.getGraphicsContext2D().getPixelWriter();
        for(int y = 0; y < before.getHeight(); y++) {
            for(int x = 0; x < before.getWidth(); x++) {
                int argb = snapshotReader.getArgb(x + (int)upperLeft.getX(), y + (int)upperLeft.getY());
                beforeWriter.setArgb(x, y, argb);
            }
        }
        PixelReader afterReader = parent.snapshot(sp, null).getPixelReader();
        PixelWriter afterWriter = after.getGraphicsContext2D().getPixelWriter();
        for(int y = 0; y < after.getHeight(); y++) {
            for(int x = 0; x < after.getWidth(); x++) {
                int argb = afterReader.getArgb(x + (int)upperLeft.getX(), y + (int)upperLeft.getY());
                afterWriter.setArgb(x, y, argb);
            }
        }
    }

    @Override
    public boolean execute() {
        return overwriteParentCanvas(after);
    }

    @Override
    public boolean unexecute() {
        return overwriteParentCanvas(before);
    }

    private boolean overwriteParentCanvas(Canvas overwriter) {
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);
        PixelWriter writer = parent.getGraphicsContext2D().getPixelWriter();
        PixelReader reader = overwriter.snapshot(sp, null).getPixelReader();
        for(int y = 0; y < overwriter.getHeight(); y++) {
            for(int x = 0; x < overwriter.getWidth(); x++) {
                int argb = reader.getArgb(x, y);
                writer.setArgb(x + (int)upperLeft.getX(), y + (int)upperLeft.getY(), argb);
            }
        }
        return true;
    }

    @Override
    public String getCommandName() {
        return "canvas Draw";
    }
}