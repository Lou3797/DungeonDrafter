package drawtool;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class BrushTool extends PenTool {
    @Override
    public void mousePressed(MouseEvent event, GraphicsContext gc) {
        System.out.println("BrushTool:MousePressed @ " + getEventCoordinates(event));
        drawCircle(event, gc);
    }

    @Override
    public void mouseDragged(MouseEvent event, GraphicsContext gc) {
        System.out.println("BrushTool:MouseDragged @ " + getEventCoordinates(event));
        drawCircle(event, gc);
    }

    @Override
    public void mouseReleased(MouseEvent event, GraphicsContext gc) {
        System.out.println("BrushTool:MouseReleased @ " + getEventCoordinates(event));
        drawCircle(event, gc);
    }

    private void drawCircle(MouseEvent event, GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        int brushSize = 10;
        gc.fillOval(event.getX()-brushSize, event.getY()-brushSize, brushSize*2, brushSize*2);
    }

    private String getEventCoordinates(MouseEvent event) {
        return "(" + event.getX() + ", " + event.getY() + ")";
    }
}
