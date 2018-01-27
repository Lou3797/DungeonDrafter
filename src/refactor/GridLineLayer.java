package refactor;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class GridLineLayer {
    private Canvas canvas;
    private int width;
    private int height;
    private int gridSize;

    public GridLineLayer(int width, int height, int gridSize) {
        this.canvas = new Canvas(width, height);
        this.width = width;
        this.height = height;
        this.gridSize = gridSize;
        drawGrid();

    }

    private void drawGrid() {
        canvas.getGraphicsContext2D().setFill(Color.TRANSPARENT);
        for(int x = 1; x < width/gridSize; x++) {
            canvas.getGraphicsContext2D().strokeLine(x*gridSize, 0, x*gridSize, height);
        }
        for(int y = 1; y < height/gridSize; y++) {
            canvas.getGraphicsContext2D().strokeLine(0, y*gridSize, width, y*gridSize);
        }
        canvas.setOpacity(0.7);
    }

    public void setVisible(boolean visible) {
        canvas.setVisible(visible);
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

}