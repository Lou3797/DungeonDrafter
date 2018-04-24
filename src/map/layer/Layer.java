package map.layer;

import javafx.scene.canvas.Canvas;
import map.Map;

/**
 * A single drawable Layer of a Map.
 *
 * @author rothnj (Lou3797@github)
 * @version 2018.4.21
 * @since 4/21/2018
 */
public class Layer extends Canvas {
    private Map parentMap;
    private String name;

    public Layer(Map parentMap) {
        super(parentMap.getWidth(), parentMap.getHeight());
        this.parentMap = parentMap;
        this.name = "New Layer";
    }

    public Map getParentMap() {
        return this.parentMap;
    }

    public String getName() {
        return this.name;
    }
}
