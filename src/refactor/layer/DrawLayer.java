package refactor.layer;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import refactor.Map;

public class DrawLayer extends Layer {
    private ImagePattern texture;

    public DrawLayer(int width, int height, Map parent, Image image) {
        this(width, height, "New DrawLayer", parent, image);
    }

    public DrawLayer(int width, int height, String name, Map parent, Image image) {
        super(width, height, name, parent);
        this.texture = new ImagePattern(image, 0, 0, image.getWidth(), image.getHeight(), false);
        resetTextureFill();
    }

    public void resetTextureFill() {
        getCanvas().getGraphicsContext2D().setFill(this.texture);
        getCanvas().getGraphicsContext2D().setStroke(this.texture);
    }


}
