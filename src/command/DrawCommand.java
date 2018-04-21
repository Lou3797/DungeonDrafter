package command;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class DrawCommand implements Command {
    private Image after;
    private Image before;
    private Canvas parent;
    private Rectangle2D viewPort;

    public DrawCommand(Canvas scratch, Canvas parent, Rectangle2D viewPort) {
        this.viewPort = viewPort;
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        params.setViewport(viewPort);
        this.after = scratch.snapshot(params, null);
        this.before = parent.snapshot(params, null);
        this.parent = parent;
    }

    @Override
    public boolean execute() {
        //System.out.println("executing " + getCommandName());
        this.parent.getGraphicsContext2D().drawImage(this.after, viewPort.getMinX(), viewPort.getMinY());
        return true;
    }

    @Override
    public boolean unexecute() {
        //System.out.println("unexecuting " + getCommandName());
        PixelReader pr = this.before.getPixelReader();
        PixelWriter pw = this.parent.getGraphicsContext2D().getPixelWriter();

        for(int x = (int)viewPort.getMinX(); x < viewPort.getMaxX(); x++) {
            for(int y = (int)viewPort.getMinY(); y < viewPort.getMaxY(); y++) {
                pw.setArgb(x, y, pr.getArgb(x - (int)viewPort.getMinX(), y - (int)viewPort.getMinY()));
            }
        }
        return true;
    }

    @Override
    public String getCommandName() {
        return "DrawCommand";
    }
}
