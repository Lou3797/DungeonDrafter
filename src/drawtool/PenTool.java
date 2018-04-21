package drawtool;

import javafx.scene.input.MouseEvent;

public abstract class PenTool implements DrawStrategy {
    @Override
    public void mousePressed(MouseEvent event) {
        System.out.println("PenTool:MousePressed");
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        System.out.println("PenTool:MouseDragged");
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        System.out.println("PenTool:MouseReleased");
    }
}
