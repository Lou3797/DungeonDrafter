package refactor;

import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;

public class Map {
    public static int gridSize;
    private int width;
    private int height;
    private DrawTool drawTool;
    private Invoker invoker;
    private List<Layer> layers; //{0,n} {Bottom, Top}
    private int selectedLayerIndex;

    public Map() {
        this(70, 700, 560);
    }

    public Map(int gridSize, int width, int height) {
        Map.gridSize = gridSize;
        this.width = width;
        this.height = height;
        this.invoker = new Invoker();
        this.layers = new ArrayList<>();
        this.selectedLayerIndex = 0;
    }

    public Invoker getInvoker() {
        return invoker;
    }

    public void addLayer(Layer layer) {
        this.layers.add(layer);
    }

    public void setSelectedLayer(int index) {
        selectedLayerIndex = index;
    }

    public Layer getSelectedLayer() {
        return this.layers.get(this.selectedLayerIndex);
    }
}
