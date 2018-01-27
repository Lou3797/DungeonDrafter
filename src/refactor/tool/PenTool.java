package refactor.tool;

import javafx.scene.input.MouseEvent;
import refactor.command.DrawCommand;
import refactor.layer.Layer;

public class PenTool extends DrawTool {

    public PenTool() {
        super();
        this.toolSize = 70;
    }

    @Override
    public void invokeCommand(Layer layer) {
        layer.getParent().getInvoker().invoke(new DrawCommand(upperLeft, lowerRight, layer.getCanvas(), snapshotReader));
    }

    @Override
    public void drawStep(MouseEvent event, Layer layer) {
        layer.getCanvas().getGraphicsContext2D().fillOval(getFillPoint(event).getX(), getFillPoint(event).getY(), toolSize, toolSize);
    }

    public void increaseToolSize() {
        this.toolSize += 10;
    }

    public void decreaseToolSize() {
        this.toolSize -= 10;
    }
}
