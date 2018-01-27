package refactor.layer;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import refactor.Map;

public class FXLayer extends Layer {
    private Canvas gridLayer;

    public FXLayer(int width, int height, Map parent) {
        this(width, height, "New FXLayer", parent);
    }

    public FXLayer(int width, int height, String name, Map parent) {
        super(width, height, name, parent);
        this.gridLayer = new Canvas(width, height);
        drawGrid();
        setGridLayerOpacity(0.7);
        getCanvas().setOpacity(0.33);
    }

    public void setGridLayerOpacity(double opacity) {
        gridLayer.setOpacity(opacity);
    }

    private void drawGrid() {
        gridLayer.getGraphicsContext2D().clearRect(0, 0, getParent().getWidth(), getParent().getHeight());
        gridLayer.getGraphicsContext2D().setStroke(Color.BLACK);
        gridLayer.getGraphicsContext2D().setFill(Color.TRANSPARENT);
        for(int x = 1; x < getParent().getWidth()/getParent().getGridSize(); x++) {
            gridLayer.getGraphicsContext2D().strokeLine(x*getParent().getGridSize(), 0, x*getParent().getGridSize(), getParent().getHeight());
        }
        for(int y = 1; y < getParent().getHeight()/getParent().getGridSize(); y++) {
            gridLayer.getGraphicsContext2D().strokeLine(0, y*getParent().getGridSize(), getParent().getWidth(), y*getParent().getGridSize());
        }
    }

    public Canvas getGridLayer() {
        return gridLayer;
    }

}
