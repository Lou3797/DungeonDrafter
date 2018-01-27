package refactor.tool;

import javafx.scene.input.MouseEvent;
import refactor.layer.Layer;
import refactor.command.DrawCommand;

import java.awt.Point;

public class GridTool extends DrawTool {

    public GridTool() {
        super();
        this.toolSize = 1;
    }

    @Override
    public void invokeCommand(Layer layer) {
        int gridSize = layer.getParent().getGridSize();
        Point topLeft = new Point((int)Math.floor(upperLeft.getX()/gridSize)*gridSize,(int)Math.floor(upperLeft.getY()/gridSize)*gridSize);
        Point bottomRight = new Point((int)Math.ceil(lowerRight.getX()/gridSize)*gridSize,(int)Math.ceil(lowerRight.getY()/gridSize)*gridSize);
        layer.getParent().getInvoker().invoke(new DrawCommand(topLeft, bottomRight, layer.getCanvas(), snapshotReader));

    }

    @Override
    public void drawStep(MouseEvent event, Layer layer) {
        int gridSize = layer.getParent().getGridSize();
        layer.getCanvas().getGraphicsContext2D().fillRect(Math.floor(event.getX()/gridSize)*gridSize,Math.floor(event.getY()/gridSize)*gridSize, gridSize, gridSize);
    }
}
