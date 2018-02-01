package layer;

import map.Map;

public class ScratchLayer extends Layer {

    public ScratchLayer(int width, int height, Map parent) {
        this(width, height, "New ScratchLayer", parent);
    }

    public ScratchLayer(int width, int height, String name, Map parent) {
        super(width, height, name, parent);
    }

}
