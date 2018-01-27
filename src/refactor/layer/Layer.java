package refactor.layer;

import javafx.scene.canvas.Canvas;
import refactor.Map;

public abstract class Layer {
    private Canvas canvas;
    private String name;
    private Map parent;

    Layer(int width, int height, Map parent) {
        this(width, height, "New Layer of unknown type", parent);
    }

    Layer(int width, int height, String name, Map parent) {
        this.canvas = new Canvas(width, height);
        this.name = name;
        this.parent = parent;
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public String getName() {
        return this.name;
    }

    public Map getParent() {
        return this.parent;
    }

    public void setVisible(boolean visible) {
        this.canvas.setVisible(visible);
    }

}
