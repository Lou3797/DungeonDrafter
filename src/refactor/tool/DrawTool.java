package refactor.tool;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import refactor.layer.Layer;

public abstract class DrawTool {
    protected int toolSize;
    protected boolean isBlocking;
    protected Rectangle2D bounds;

    public DrawTool() {
        this.isBlocking = false;
    }

    public void mousePress(MouseEvent event, Layer scratchLayer) {
        isBlocking = true;

        bounds = new Rectangle2D(getDrawPoint2D(event).getX(), getDrawPoint2D(event).getY(), toolSize, toolSize);

        drawStep(event, scratchLayer);
    }

    public void mouseDrag(MouseEvent event, Layer scratchLayer) {
        isBlocking = true;

        double minX = bounds.getMinX();
        double minY = bounds.getMinY();
        double maxX = bounds.getMaxX();
        double maxY = bounds.getMaxY();

        Rectangle2D currentRectangle = new Rectangle2D(getDrawPoint2D(event).getX(), getDrawPoint2D(event).getY(), toolSize, toolSize);
        if(!bounds.contains(currentRectangle)) {
            if(currentRectangle.getMinX() < bounds.getMinX()) {
                minX = currentRectangle.getMinX();
            }
            if(currentRectangle.getMinY() < bounds.getMinY()) {
                minY = currentRectangle.getMinY();
            }
            if(currentRectangle.getMaxX() > bounds.getMaxX()) {
                maxX = currentRectangle.getMaxX();
            }
            if(currentRectangle.getMaxY() > bounds.getMaxY()) {
                maxY = currentRectangle.getMaxY();
            }
        }

        bounds = new Rectangle2D(minX, minY, maxX - minX, maxY - minY);
        //System.out.println(bounds);

        drawStep(event, scratchLayer);
    }

    public void mouseRelease(MouseEvent event, Layer scratchLayer, Layer drawLayer) {
        //drawStep(event, scratchLayer);
        mergeScratchLayer(bounds, scratchLayer, drawLayer);
        isBlocking = false;
    }

    abstract public void mergeScratchLayer(Rectangle2D bounds, Layer scratchLayer, Layer drawLayer);

    abstract public void drawStep(MouseEvent event, Layer layer);

    /**
     * Calculates the upper left and top bound for the current tool.
     * @param event The MouseEvent for this action
     * @return The lower X and Y coordinates at which to begin drawing.
     */
    protected Point2D getDrawPoint2D(MouseEvent event) {
        return new Point2D(event.getX() - Math.floor(toolSize/2), event.getY() - Math.floor(toolSize/2));
    }

    public void drawFX(MouseEvent event, Layer fxLayer) {
        fxLayer.getCanvas().getGraphicsContext2D().setStroke(Color.BLACK);
        fxLayer.getCanvas().getGraphicsContext2D().strokeArc(getDrawPoint2D(event).getX(), getDrawPoint2D(event).getY(), toolSize, toolSize, 0, 360, ArcType.OPEN);
    }

}
