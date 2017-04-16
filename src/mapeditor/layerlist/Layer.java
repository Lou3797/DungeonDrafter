package mapeditor.layerlist;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import mapeditor.MapFile;

public class Layer {

    private String layerName;
    private ImagePattern texture;
    private Canvas canvas;
    private double opacity;

    public Layer(MapFile parent, String layerName, Image image) {
        this.layerName = layerName;
        texture = new ImagePattern(image);
        canvas = new Canvas(parent.getWidth(), parent.getHeight());
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, this::doStuff);
    }

    private void doStuff(MouseEvent event) {
        System.out.println("Mouse was clicked " + event.getX() + ", " + event.getY());
    }

    public String getLayerName() {
        return layerName;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public double getOpacity() {
        return canvas.getOpacity();
    }

    public void setOpacity(double value) {
        canvas.setOpacity(value);
    }

}