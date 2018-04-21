package drawtool;

import javafx.scene.input.MouseEvent;

public interface DrawStrategy {

    void mousePressed(MouseEvent event);

    void mouseDragged(MouseEvent event);

    void mouseReleased(MouseEvent event);

}
