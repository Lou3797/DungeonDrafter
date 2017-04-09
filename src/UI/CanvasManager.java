package UI;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class CanvasManager {

    private Pane parent;
    private int width;
    private int height;
    private ArrayList<Canvas> layers;
    private Color color;
    private int curPos;

    public CanvasManager(Pane parent, int width, int height) {
        this.parent = parent;
        this.width = width;
        this.height = height;
        this.layers = new ArrayList<>();
        this.color = Color.BLACK;
        this.curPos = 0;
    }

    /**
     * Inserts a new Canvas above the current position
     * @return The newly created canvas
     */
    public Canvas addLayer() {
        return createCanvas();
    }

    public Canvas deleteLayer() {
        parent.getChildren().remove(curPos);
        return layers.get(curPos);
    }

    public void shiftUp() {

    }

    public void shiftDown() {

    }

    public Canvas changeLayer(int curPos) {
        this.curPos = curPos;
        updateGraphicsContext();
        return layers.get(curPos);
    }

    private void updateGraphicsContext() {
        getGraphicsContext2D().setStroke(this.color);
        getGraphicsContext2D().setFill(this.color);
    }

    private Canvas createCanvas() {
        Canvas temp = new Canvas(width, height);
        parent.getChildren().add(curPos, temp);
        layers.add(curPos, temp);
        return temp;
    }

    public GraphicsContext getGraphicsContext2D() {
        return layers.get(curPos).getGraphicsContext2D();
    }

    public void setColor(Color color) {
        this.color = color;
        updateGraphicsContext();
    }

    public void clearCanvas() {
        getGraphicsContext2D().clearRect(0, 0, width, height);
    }

    public int getCurPos() {
        return curPos;
    }
}