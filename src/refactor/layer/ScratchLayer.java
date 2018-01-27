package refactor.layer;

import refactor.Map;

public class ScratchLayer extends Layer {

    ScratchLayer(int width, int height, Map parent) {
        this(width, height, "New ScratchLayer", parent);
    }

    ScratchLayer(int width, int height, String name, Map parent) {
        super(width, height, name, parent);
    }

    private void clearCanvas() {
        getCanvas().getGraphicsContext2D().clearRect(0, 0, getParent().getWidth(), getParent().getHeight());
    }


}
