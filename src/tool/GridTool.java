package tool;

import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import layer.Layer;

public class GridTool extends DrawTool {

    public GridTool() {
        super();
        this.toolSize = 1;
    }

    @Override
    public void mergeScratchLayer(Rectangle2D bounds, Layer scratchLayer, Layer drawLayer) {

    }

    @Override
    public void drawStep(MouseEvent event, Layer layer) {
        int gridSize = layer.getParent().getGridSize();
        layer.getCanvas().getGraphicsContext2D().fillRect(Math.floor(event.getX()/gridSize)*gridSize,Math.floor(event.getY()/gridSize)*gridSize, gridSize, gridSize);
    }

    @Override
    public void drawFX(MouseEvent event, Layer fxLayer) {
        fxLayer.getCanvas().getGraphicsContext2D().setFill(Color.BLACK);
        fxLayer.getCanvas().getGraphicsContext2D().fillRect((int)(event.getX() / fxLayer.getParent().getGridSize()) * fxLayer.getParent().getGridSize(),
                (int)(event.getY() / fxLayer.getParent().getGridSize()) * fxLayer.getParent().getGridSize(),
                fxLayer.getParent().getGridSize(), fxLayer.getParent().getGridSize());
    }

}
