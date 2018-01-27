package refactor.layer;

import javafx.scene.paint.Color;
import refactor.Map;

public class GridLineLayer extends Layer{

    public GridLineLayer(int width, int height, Map parent) {
        super(width, height, parent);
        drawGrid();
    }

    private void drawGrid() {
        getCanvas().getGraphicsContext2D().setFill(Color.TRANSPARENT);
        for(int x = 1; x < getParent().getWidth()/getParent().getGridSize(); x++) {
            getCanvas().getGraphicsContext2D().strokeLine(x*getParent().getGridSize(), 0, x*getParent().getGridSize(), getParent().getHeight());
        }
        for(int y = 1; y < getParent().getHeight()/getParent().getGridSize(); y++) {
            getCanvas().getGraphicsContext2D().strokeLine(0, y*getParent().getGridSize(), getParent().getWidth(), y*getParent().getGridSize());
        }
        getCanvas().setOpacity(0.7);
    }

}