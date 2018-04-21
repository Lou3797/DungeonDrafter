package drawtool;

import javafx.scene.input.MouseEvent;

public class BrushTool extends PenTool {
    @Override
    public void mousePressed(MouseEvent event) {
        System.out.println("BrushTool:MousePressed");
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        System.out.println("BrushTool:MouseDragged");
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        System.out.println("BrushTool:MouseReleased");
    }
}
