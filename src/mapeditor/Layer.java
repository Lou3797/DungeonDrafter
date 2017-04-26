package mapeditor;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import mainui.DELmapeditor.DrawPen;

public class Layer {

    private String layerName;
    private ImagePattern texture;
    private Canvas canvas;
    private double opacity;
    private DrawPen pen;

    public Layer(MapFile parent, String layerName, Image image) {
        this.layerName = layerName;
        texture = new ImagePattern(image, 0, 0, image.getWidth(), image.getHeight(), false);
        canvas = new Canvas(parent.getWidth(), parent.getHeight());
        pen = new DrawPen();
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, this::doStuff);
        canvas.getGraphicsContext2D().setFill(texture);
        canvas.getGraphicsContext2D().setStroke(texture);
        opacity = 100;
    }

    public GraphicsContext getGraphicsContext2D() {
        return canvas.getGraphicsContext2D();
    }

    public void clearCanvas() {
        getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void doStuff(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)) {
            pen.addPoint(event.getX(), event.getY());
            if(pen.getSize() > 1) {
                getGraphicsContext2D().strokeLine(pen.getxPoints()[pen.getSize()-2],
                        pen.getyPoints()[pen.getSize()-2], pen.getxPoints()[pen.getSize()-1],
                        pen.getyPoints()[pen.getSize()-1]);
            } else if(pen.getSize() == 1) {
                getGraphicsContext2D().strokeLine(pen.getxPoints()[pen.getSize()-1],
                        pen.getyPoints()[pen.getSize()-1], pen.getxPoints()[pen.getSize()-1],
                        pen.getyPoints()[pen.getSize()-1]);
            }
            if(event.isShiftDown()) {
                getGraphicsContext2D().fillPolygon(pen.getxPoints(), pen.getyPoints(), pen.getSize());
                pen.clear();
            }
        }
        else if(!event.isShiftDown() && event.getButton().equals(MouseButton.SECONDARY)) {
            System.out.println("Clicked " + event.getX() + ", " + event.getY());
        } else if(event.isShiftDown() && event.getButton().equals(MouseButton.SECONDARY)) {
            clearCanvas();
            pen.clear();
        }
    }

    public String getLayerName() {
        return layerName;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double value) {
        opacity = value;
        canvas.setOpacity(value);
    }

    public void hideLayer() {
        canvas.setOpacity(0);
    }

    public void showLayer() {
        canvas.setOpacity(opacity);
    }

    public void setTexture(String imageURL) {
        Image image = new Image(imageURL);
        texture = new ImagePattern(image, 0, 0, image.getWidth(), image.getHeight(), false);
    }

    public void reapplyTexture() {
        Canvas textureFill = new Canvas(canvas.getWidth(), canvas.getHeight());
        textureFill.getGraphicsContext2D().setFill(texture);
        textureFill.getGraphicsContext2D().fillRect(0, 0, textureFill.getWidth(), textureFill.getHeight());
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);
        PixelReader textureReader = textureFill.snapshot(sp, null).getPixelReader();
        PixelReader baseReader = canvas.snapshot(sp, null).getPixelReader();
        PixelWriter writer = canvas.getGraphicsContext2D().getPixelWriter();
        for(int y = 0; y < canvas.getHeight(); y++) {
            for(int x = 0; x < canvas.getWidth(); x++) {
                int textureARGB = textureReader.getArgb(x, y);
                int textureAlpha = 0xFF & (textureARGB >> 24);
                int baseARGB = baseReader.getArgb(x, y);
                int baseAlpha = 0xFF & (baseARGB >> 24);
                if(baseAlpha != 0 && textureAlpha != 0) {
                    int resultARGB = (baseAlpha << 24) | ((0xFF & ( textureARGB >> 16)) << 16 ) | ((0xFF & (textureARGB >> 8 )) << 8) | 0xFF & (textureARGB >> 0);
                    writer.setArgb(x, y, resultARGB);
                }
            }
        }
    }

}