package UI;

import UI.MapEditor.CanvasManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class UIController {

    public Pane mapcontainer;
    public ColorPicker colorPicker;
    public ListView layerslist;
    private CanvasManager canvasManager;
    private Image selectedTexture;

    public UIController() {
        //Before the FXML is loaded
        selectedTexture = new Image("UI/MapEditor/cw1.jpg");
    }

    @FXML
    public void initialize() {
        //After the FXML is loaded
        canvasManager = new CanvasManager(mapcontainer, 540, 720);
        layerslist.getSelectionModel().select(0);
        canvasManager.addLayer(selectedTexture);
    }

    @FXML
    protected void changeTexture(ActionEvent event) {
        switch(((Control)event.getSource()).getId()) {
            case "texture1":
                selectedTexture = new Image("UI/MapEditor/cw1.jpg");
                break;
            case "texture2":
                selectedTexture = new Image("UI/MapEditor/cw2.jpg");
                break;
        }


    }

    @FXML
    public void changeLayer(MouseEvent mouseEvent) {
        //System.out.println("Choosing Layer " + layerslist.getSelectionModel().getSelectedIndex());
        canvasManager.changeLayer(layerslist.getSelectionModel().getSelectedIndex());
    }

    @FXML
    public void addLayer(ActionEvent actionEvent) {
        String newLayer = "Layer " + (layerslist.getItems().size());
        layerslist.getItems().add(layerslist.getSelectionModel().getSelectedIndex(), newLayer);
        canvasManager.addLayer(selectedTexture);
        layerslist.getSelectionModel().select(canvasManager.getCurPos());
        layerslist.getSelectionModel().select(canvasManager.getCurPos());

    }

    @FXML
    public void changeColor(ActionEvent actionEvent) {
        canvasManager.setColor(colorPicker.getValue());
    }

    @FXML
    public void deleteLayer(ActionEvent actionEvent) {
        if(layerslist.getItems().size() != 1) {
            layerslist.getItems().remove(canvasManager.getCurPos());
        }
        canvasManager.deleteLayer();
        canvasManager.setColor(canvasManager.getColor());
    }

    @FXML
    public void shiftLayerUp(ActionEvent actionEvent) {
        canvasManager.shiftUp();
        layerslist.getSelectionModel().select(canvasManager.getCurPos());

    }

    @FXML
    public void shiftLayerDown(ActionEvent actionEvent) {
        canvasManager.shiftDown();
        layerslist.getSelectionModel().select(canvasManager.getCurPos());
    }

    public void applyClipping(ActionEvent actionEvent) {
        canvasManager.applyClipping();
    }
}