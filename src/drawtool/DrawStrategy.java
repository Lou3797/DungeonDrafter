package drawtool;

import javafx.scene.input.MouseEvent;
import map.Map;

/**
 * Necessary methods for a drawing tool.
 *
 * @author rothnj (Lou3797@github)
 * @version 2018.4.21
 * @since 4/21/2018
 */
public interface DrawStrategy {

    void mousePressed(MouseEvent event, Map map);

    void mouseDragged(MouseEvent event, Map map);

    void mouseReleased(MouseEvent event, Map map);
}
