package drawtool;

import command.DrawCommand;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import map.Map;

public class BrushTool extends PenTool {
    private Rectangle2D viewPort;
    private int brushSize = 10;
    private Point2D min;
    private Point2D max;

    @Override
    public void mousePressed(MouseEvent event, Map map) {
        drawCircle(event, map.getScratchLayer().getGraphicsContext2D());
        this.min = new Point2D(event.getX()-this.brushSize, event.getY()-this.brushSize);
        this.max = new Point2D(event.getX()+this.brushSize, event.getY()+this.brushSize);
    }

    @Override
    public void mouseDragged(MouseEvent event, Map map) {
        drawCircle(event, map.getScratchLayer().getGraphicsContext2D());
        comparePoints(event);
    }

    @Override
    public void mouseReleased(MouseEvent event, Map map) {
        drawCircle(event, map.getScratchLayer().getGraphicsContext2D());
        comparePoints(event);

        map.getInvoker().invoke(new DrawCommand(map.getScratchLayer(), map.getCurrentLayer(),
                new Rectangle2D(min.getX(), min.getY(), (max.getX()-min.getX()), (max.getY()-min.getY()))));

        map.getScratchLayer().getGraphicsContext2D().clearRect(0, 0, 600, 450);
    }

    private void drawCircle(MouseEvent event, GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillOval(event.getX()-this.brushSize, event.getY()-this.brushSize, this.brushSize*2, this.brushSize*2);
    }

    private void comparePoints(MouseEvent event) {
        if(event.getX()-this.brushSize < min.getX()) {
            min = new Point2D(event.getX()-this.brushSize, min.getY());
        } else if(event.getX()+this.brushSize > max.getX()) {
            max = new Point2D(event.getX()+this.brushSize, max.getY());
        }

        if(event.getY()-this.brushSize < min.getY()) {
            min = new Point2D(min.getX(), event.getY()-this.brushSize);
        } else if (event.getY()+this.brushSize > max.getY()) {
            max = new Point2D(max.getX(), event.getY()+this.brushSize);
        }
    }

    private String getEventCoordinates(MouseEvent event) {
        return "(" + event.getX() + ", " + event.getY() + ")";
    }
}
