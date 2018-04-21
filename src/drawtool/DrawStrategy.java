package drawtool;

import javafx.scene.input.MouseEvent;
import map.Map;

public interface DrawStrategy {

    void mousePressed(MouseEvent event, Map map);

    void mouseDragged(MouseEvent event, Map map);

    void mouseReleased(MouseEvent event, Map map);
}
