package refactor.tool;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import refactor.layer.Layer;
import refactor.command.DrawCommand;

public class EraserTool extends PenTool {
    public EraserTool() {
        super();
    }

    @Override
    public void invokeCommand(Layer layer) {
        layer.getCanvas().getGraphicsContext2D().setFill(Color.TRANSPARENT);
        layer.getParent().getInvoker().invoke(new DrawCommand(upperLeft, lowerRight, layer.getCanvas(), snapshotReader));
    }

    @Override
    public void drawStep(MouseEvent event, Layer layer) {
        layer.getCanvas().getGraphicsContext2D().fillOval(event.getX()-(toolSize/2), event.getY()-(toolSize/2), toolSize, toolSize);
    }

}
