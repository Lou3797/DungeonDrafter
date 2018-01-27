package refactor.tool;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import refactor.layer.Layer;

import java.awt.Point;

public abstract class DrawTool {
    protected int toolSize;
    private SnapshotParameters sp;
    protected PixelReader snapshotReader;
    protected Point current;
    protected Point upperLeft;
    protected Point lowerRight;

    public DrawTool() {
        this.sp = new SnapshotParameters();
        this.sp.setFill(Color.TRANSPARENT);
    }

    public void mousePress(MouseEvent event, Layer layer) {
        snapshotReader = layer.getCanvas().snapshot(sp, null).getPixelReader();
        current = new Point((int)event.getX(), (int)event.getY());
        upperLeft = new Point(current.getX() - toolSize < 0 ? 0 : (int)current.getX() - toolSize, current.getY() - toolSize < 0 ? 0 : (int)current.getY() - toolSize);
        lowerRight = new Point(current.getX() + toolSize > layer.getCanvas().getWidth() ? (int) layer.getCanvas().getWidth() : (int)current.getX() + toolSize, current.getY() + toolSize > layer.getCanvas().getHeight() ? (int) layer.getCanvas().getHeight() : (int)current.getY() + toolSize);
        drawStep(event, layer);
    }

    public void mouseDrag(MouseEvent event, Layer layer) {
        current = new Point((int)event.getX(), (int)event.getY());
        if(current.getX() - toolSize < upperLeft.getX()) {
            if(current.getX() - toolSize < 0) {
                upperLeft.setLocation(0, upperLeft.getY());
            }else {
                upperLeft.setLocation(current.getX() - toolSize, upperLeft.getY());
            }
        } else if(current.getX() + toolSize > lowerRight.getX()) {
            if(current.getX() + toolSize > layer.getCanvas().getWidth()) {
                lowerRight.setLocation(layer.getCanvas().getWidth(), lowerRight.getY());
            }else {
                lowerRight.setLocation(current.getX() + toolSize, lowerRight.getY());
            }
        }
        if(current.getY() - toolSize < upperLeft.getY()) {
            if(current.getY() - toolSize < 0) {
                upperLeft.setLocation(upperLeft.getX(), 0);
            }else {
                upperLeft.setLocation(upperLeft.getX(), current.getY() - toolSize);
            }
        }else if(current.getY() + toolSize > lowerRight.getY()) {
            if(current.getY() + toolSize > layer.getCanvas().getHeight()) {
                lowerRight.setLocation(lowerRight.getX(), layer.getCanvas().getHeight());
            }else {
                lowerRight.setLocation(lowerRight.getX(), current.getY() + toolSize);
            }
        }

        drawStep(event, layer);
    }

    public void mouseRelease(MouseEvent event, Layer layer) {
        drawStep(event, layer);
        invokeCommand(layer);
    }

    abstract public void invokeCommand(Layer layer);

    abstract public void drawStep(MouseEvent event, Layer layer);

}
