package refactor;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Layer {
    private Canvas canvas;
    private String name;
    private Invoker invoker;
    private Map parent;
    private ImagePattern texture;

    Layer(int width, int height, Map parent) {
        this.canvas = new Canvas(width, height);
        this.name = "Temp";
        this.parent = parent;
    }

    Layer(int width, int height, String name, Map parent) {
        this.canvas = new Canvas(width, height);
        this.name = name;
        this.parent = parent;
        //this.canvas.setOnMouseClicked( event -> System.out.println("Clicked on " + this.name)); //Delete later
    }

    Layer(int width, int height, String name, Map parent, Image image) {
        this.canvas = new Canvas(width, height);
        this.name = name;
        this.parent = parent;
        //this.canvas.setOnMouseClicked( event -> System.out.println("Clicked on " + this.name)); //Delete later
        this.texture = new ImagePattern(image, 0, 0, image.getWidth(), image.getHeight(), false);
        this.canvas.getGraphicsContext2D().setFill(texture);
        this.canvas.getGraphicsContext2D().setStroke(texture);
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



}
