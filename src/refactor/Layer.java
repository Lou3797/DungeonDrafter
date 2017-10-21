package refactor;

import javafx.scene.canvas.Canvas;

public class Layer {
    private Canvas canvas;
    private String name;

    Layer(int width, int height) {
        this.canvas = new Canvas(width, height);
        this.name = "Temp";
    }

    Layer(int width, int height, String name) {
        this.canvas = new Canvas(width, height);
        this.name = name;

        this.canvas.setOnMouseClicked( event -> System.out.println("Clicked on " + this.name));
    }

    public Canvas getCanvas() {
        return this.canvas;
    }
}
