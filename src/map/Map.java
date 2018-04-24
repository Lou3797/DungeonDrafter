package map;

import command.Invoker;
import drawtool.DrawStrategy;
import javafx.scene.paint.Color;
import map.layer.Layer;

import java.util.ArrayList;
import java.util.List;

/**
 * A single map. Composed of multiple Layers.
 *
 * @author rothnj (Lou3797@github)
 * @version 2018.4.24
 * @since 4/21/2018
 */
public class Map {
    private static final int DEFAULT_WIDTH = 1120;
    private static final int DEFAULT_HEIGHT = 700;
    private static final int DEFAULT_GRIDSIZE = 70;
    private static final String DEFAULT_NAME = "New Map";

    private Invoker invoker;

    /**
     * Internal list of layers the map has.
     */
    private List<Layer> layers;
    private int currentLayerIndex;
    private int width;
    private int height;
    private int gridSize;
    private String name;

    public Map(int width, int height, int gridSize, String name) {
        this.invoker = new Invoker();
        this.width = width;
        this.height = height;
        this.gridSize = gridSize;
        this.name = name;

        Layer temp = new Layer(this);
        temp.getGraphicsContext2D().setFill(Color.LIGHTBLUE);
        temp.getGraphicsContext2D().fillRect(0, 0, this.width, this.height);

        this.layers = new ArrayList<>();
        this.layers.add(temp);
        this.currentLayerIndex = 0;
        addScratchLayer();
    }

    public Map(int width, int height) {
        this(width, height, DEFAULT_GRIDSIZE, DEFAULT_NAME);
    }

    public Map() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_GRIDSIZE, DEFAULT_NAME);
    }

    /*
    public Map(File file) throws FileNotFoundException {
        DDMReader reader = new DDMReader(file);
    }
    */

    public void rigCanvasScratchLayer(DrawStrategy drawTool) {
        getScratchLayer().setOnMousePressed(event -> {
            drawTool.mousePressed(event, this);
        });
        getScratchLayer().setOnMouseDragged(event -> {
            drawTool.mouseDragged(event, this);
        });
        getScratchLayer().setOnMouseReleased(event -> {
            drawTool.mouseReleased(event, this);
        });
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String getName() {
        return this.name;
    }

    public Layer getCurrentLayer() {
        return this.layers.get(this.currentLayerIndex);
    }

    public Layer getScratchLayer() {
        return this.layers.get(this.currentLayerIndex+1);
    }

    public List<Layer> getLayers() {
        return this.layers;
    }

    public Invoker getInvoker() {
        return this.invoker;
    }

    public void importLayers(List<Layer> layers) {
        this.layers = layers;
        this.currentLayerIndex = this.layers.size()-1;
        addScratchLayer();
    }

    private void addScratchLayer() {
        Layer scratch = new Layer(this);
        this.layers.add(scratch);
    }
}
