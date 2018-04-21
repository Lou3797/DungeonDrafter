package map;

import command.Invoker;
import javafx.scene.canvas.Canvas;
import map.layer.Layer;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private static final int DEFAULT_WIDTH = 1120;
    private static final int DEFAULT_HEIGHT = 700;
    private static final int DEFAULT_GRIDSIZE = 70;
    private static final String DEFAULT_NAME = "New Map";

    private Invoker invoker;
    private List<Layer> layers;
    private int width;
    private int height;
    private int gridSize;
    private String name;

    public Map(int width, int height, int gridSize, String name) {
        this.invoker = new Invoker();
        this.layers = new ArrayList<>();
        this.width = width;
        this.height = height;
        this.gridSize = gridSize;
        this.name = name;
        rigCanvas();
    }

    public Map(int width, int height) {
        this(width, height, DEFAULT_GRIDSIZE, DEFAULT_NAME);
    }

    public Map() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_GRIDSIZE, DEFAULT_NAME);
    }

    private void rigCanvas() {
        Layer temp = new Layer(this);
        temp.setOnMousePressed(event -> {
            drawTool.mousePressed(event, canvas.getGraphicsContext2D());
        });
        temp.setOnMouseDragged(event -> {
            drawTool.mouseDragged(event, canvas.getGraphicsContext2D());
        });
        temp.setOnMouseReleased(event -> {
            drawTool.mouseReleased(event, canvas.getGraphicsContext2D());
        });
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
