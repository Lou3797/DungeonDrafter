package refactor;

import javafx.scene.input.MouseEvent;

public class PenTool extends DrawTool {

    PenTool() {
        super();
        this.toolSize = 50;
    }

    @Override
    void invokeCommand(Layer layer) {
        layer.getParent().getInvoker().invoke(new DrawCommand(upperLeft, lowerRight, layer.getCanvas(), snapshotReader));
    }

    @Override
    void drawStep(MouseEvent event, Layer layer) {
        layer.getCanvas().getGraphicsContext2D().fillOval(event.getX()-(toolSize/2), event.getY()-(toolSize/2), toolSize, toolSize);
    }

    void increaseToolSize() {
        this.toolSize += 5;
    }

    void decreaseToolSize() {
        this.toolSize -= 5;
    }
}
