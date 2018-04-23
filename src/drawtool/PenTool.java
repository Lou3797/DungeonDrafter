package drawtool;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import map.Map;

public abstract class PenTool implements DrawStrategy {
    protected int brushSize = 10;
    protected Point2D min;
    protected Point2D max;

    @Override
    public void mousePressed(MouseEvent event, Map map) {
        System.out.println("PenTool:MousePressed");
    }

    @Override
    public void mouseDragged(MouseEvent event, Map map) {
        System.out.println("PenTool:MouseDragged");
    }

    @Override
    public void mouseReleased(MouseEvent event, Map map) {
        System.out.println("PenTool:MouseReleased");
    }

    protected void comparePoints(MouseEvent event, Map map) {
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

        if(event.getX() < 0) {
            min = new Point2D(0, min.getY());
        } else if (event.getX() > map.getWidth()) {
            max = new Point2D(map.getWidth(), max.getY());
        }

        if(event.getY() < 0) {
            min = new Point2D(min.getX(), 0);
        } else if(event.getY() > map.getHeight()) {
            max = new Point2D(max.getX(), map.getHeight());
        }
    }
}
