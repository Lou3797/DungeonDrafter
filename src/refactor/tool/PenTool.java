package refactor.tool;

import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import refactor.command.DrawCommand;
import refactor.layer.Layer;

public class PenTool extends DrawTool {

    public PenTool() {
        super();
        this.toolSize = 70;
    }

    @Override
    public void mergeScratchLayer(Rectangle2D bounds, Layer scratchLayer, Layer drawLayer) {
        drawLayer.getParent().getInvoker().invoke(new DrawCommand(bounds, drawLayer.getCanvas(), scratchLayer.getCanvas()));

        scratchLayer.getCanvas().getGraphicsContext2D().clearRect(bounds.getMinX(), bounds.getMinY(),
                bounds.getMaxX() - bounds.getMinX(), bounds.getMaxY() - bounds.getMinY());
    }

    @Override
    public void drawStep(MouseEvent event, Layer layer) {
        layer.getCanvas().getGraphicsContext2D().fillOval(getDrawPoint2D(event).getX(), getDrawPoint2D(event).getY(), toolSize, toolSize);
    }

    public void increaseToolSize() {
        this.toolSize += 10;
    }

    public void decreaseToolSize() {
        this.toolSize -= 10;
    }
}
