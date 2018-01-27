package refactor;

import refactor.command.Invoker;
import refactor.layer.Layer;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private int gridSize;
    private int width;
    private int height;
    private Invoker invoker;
    private List<Layer> layers; //{0,n} {Bottom, Top}
    private int selectedLayerIndex;

    public Map() {
        this(70, 700, 560);
    }

    public Map(int width, int height) {
        this(70, width, height);
    }

    public Map(int gridSize, int width, int height) {
        this.gridSize = gridSize;
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
        this.selectedLayerIndex = index;
    }

    public Layer getSelectedLayer() {
        return this.layers.get(this.selectedLayerIndex);
    }

    public int getGridSize() {
        return this.gridSize;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
