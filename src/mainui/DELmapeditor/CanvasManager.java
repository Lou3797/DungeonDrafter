package mainui.DELmapeditor;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.ArrayList;

@Deprecated
public class CanvasManager {

    private int width;
    private int height;
    private int curPos;
    private Pane parent;
    private ArrayList<ClippingGroup> layers;
    private Color color;
    private DrawPen pen;

    public CanvasManager(Pane parent, int width, int height) {
        this.parent = parent;
        this.width = width;
        this.height = height;
        this.layers = new ArrayList<>();
        this.color = Color.BLACK;
        this.pen = new DrawPen();
        this.curPos = 0;
    }

    /**
     * Inserts a new Canvas above the current position
     * @return The newly created canvas
     */
    public Canvas addLayer(Image texture) {
        return createCanvas(texture);
    }

    public Canvas deleteLayer() {
        if(layers.size() == 1) {
            clearCanvas();
            return null;
        } else if(curPos == 0) {
            parent.getChildren().remove(curPos);
            return layers.remove(curPos).getBase();
        } else{
            parent.getChildren().remove(curPos);
            return layers.remove(curPos--).getBase();
        }
    }

    public void shiftUp() {

    }

    public void shiftDown() {

    }

    public Canvas changeLayer(int curPos) {
        this.curPos = curPos;
        updateGraphicsContext();
        return layers.get(curPos).getBase();
    }

    private void updateGraphicsContext() {
        getGraphicsContext2D().setStroke(this.color);
        getGraphicsContext2D().setFill(this.color);
    }

    private Canvas createCanvas(Image texture) {
        Canvas temp = new Canvas(width, height);
        parent.getChildren().add(curPos, temp);
        layers.add(curPos, new ClippingGroup(temp, texture));
        attachDrawListener(temp);
        updateGraphicsContext();
        return temp;
    }

    public GraphicsContext getGraphicsContext2D() {
        return layers.get(curPos).getBase().getGraphicsContext2D();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        updateGraphicsContext();
    }

    public void clearCanvas() {
        getGraphicsContext2D().clearRect(0, 0, width, height);
    }

    public int getCurPos() {
        return curPos;
    }

    private void attachDrawListener(Canvas current) {
        current.addEventHandler(MouseEvent.MOUSE_CLICKED, this::click);
    }

    private void click(MouseEvent event) {
        /*if(event.getButton().equals(MouseButton.PRIMARY)) {
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
        }*/
    }

    public void applyClipping() {
        layers.get(curPos).applyClipping();
    }
}