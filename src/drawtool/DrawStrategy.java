package drawtool;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public interface DrawStrategy {

    void mousePressed(MouseEvent event, GraphicsContext gc);

    void mouseDragged(MouseEvent event, GraphicsContext gc);

    void mouseReleased(MouseEvent event, GraphicsContext gc);
}
