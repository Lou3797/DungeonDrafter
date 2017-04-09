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
    private int curPos;
    private ArrayList<Canvas> layers;
    private Color color;


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
        layers.add(createCanvas(curPos));
        return null;
    }

    public Canvas deleteLayer() {
        return layers.get(curPos);
    }

    public void shiftUp() {

    }

    public void shiftDown() {

    }

    private Canvas createCanvas(int curPos) {
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
    }
}