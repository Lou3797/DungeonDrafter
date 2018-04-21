package drawtool;

import javafx.scene.input.MouseEvent;
import map.Map;

public abstract class PenTool implements DrawStrategy {
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
}
