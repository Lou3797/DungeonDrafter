package UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class UIController {

    public Text testText;
    public Pane mapcontainer;
    public ColorPicker colorPicker;
    public ListView layerslist;
    private CanvasManager canvasManager;
    private DrawPen pen;

    public UIController() {
        //Before the FXML is loaded
        pen = new DrawPen();
    }

    @FXML
    public void initialize() {
        //After the FXML is loaded
        canvasManager = new CanvasManager(mapcontainer, 540, 720);
        layerslist.getSelectionModel().select(0);
        Canvas temp = canvasManager.addLayer();
        temp.addEventHandler(MouseEvent.MOUSE_CLICKED, this::drawShape);
    }

    @FXML
    protected void changeTexture(ActionEvent event) {
    }

    @FXML
    private void drawShape(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)) {
            pen.addPoint(event.getX(), event.getY());
            if(pen.getSize() > 1) {
                canvasManager.getGraphicsContext2D().strokeLine(pen.getxPoints()[pen.getSize()-2],
                        pen.getyPoints()[pen.getSize()-2], pen.getxPoints()[pen.getSize()-1],
                        pen.getyPoints()[pen.getSize()-1]);
            } else if(pen.getSize() == 1) {
                canvasManager.getGraphicsContext2D().strokeLine(pen.getxPoints()[pen.getSize()-1],
                        pen.getyPoints()[pen.getSize()-1], pen.getxPoints()[pen.getSize()-1],
                        pen.getyPoints()[pen.getSize()-1]);
            }
            if(event.isShiftDown()) {
                canvasManager.getGraphicsContext2D().fillPolygon(pen.getxPoints(), pen.getyPoints(), pen.getSize());
                pen.clear();
            }
        }
        else if(!event.isShiftDown() && event.getButton().equals(MouseButton.SECONDARY)) {
            System.out.println("Clicked " + event.getX() + ", " + event.getY());
        } else if(event.isShiftDown() && event.getButton().equals(MouseButton.SECONDARY)) {
            canvasManager.clearCanvas();
            pen.clear();
        }

    }

    public void changeLayer(MouseEvent mouseEvent) {
        System.out.println("Choosing Layer " + layerslist.getSelectionModel().getSelectedIndex());
        canvasManager.changeLayer(layerslist.getSelectionModel().getSelectedIndex());
    }

    public void addLayer(ActionEvent actionEvent) {
        String newLayer = "Layer " + (layerslist.getItems().size());
        layerslist.getItems().add(layerslist.getSelectionModel().getSelectedIndex(), newLayer);
        Canvas temp = canvasManager.addLayer();
        temp.addEventHandler(MouseEvent.MOUSE_CLICKED, this::drawShape);
        layerslist.getSelectionModel().select(canvasManager.getCurPos());

    }

    public void changeColor(ActionEvent actionEvent) {
        canvasManager.setColor(colorPicker.getValue());
    }
}