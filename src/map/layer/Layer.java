package map.layer;

import javafx.scene.canvas.Canvas;
import map.Map;

public class Layer extends Canvas {
    private String name;
    private Map parentMap;

    public Layer(Map parentMap) {
        super(parentMap.getWidth(), parentMap.getHeight());
        this.parentMap = parentMap;
        this.name = "New Layer";
    }

    public Map getParentMap() {
        return parentMap;
    }
}
