package mainui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mainui.mapeditor.CanvasManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import mainui.popups.newfile.NewFilePopup;
import mainui.popups.newlayer.NewLayerPopup;
import mapeditor.MapFile;
import mapeditor.layerlist.LayerListCell;

import java.io.IOException;
import java.util.ArrayList;

public class UIController {

    private ArrayList<MapFile> loadedFiles;
    @FXML VBox rightPane;
    @FXML private Pane mapPane;
    @FXML private ColorPicker colorPicker;
    @FXML private ListView<LayerListCell> layersListView;
    @FXML private MenuBar menuBar;
    @FXML private TabPane mapTabPane;
    @FXML private CanvasManager canvasManager;
    @FXML private Image selectedTexture;

    public UIController() {
        //Before the FXML is loaded
        loadedFiles = new ArrayList<>();
    }

    @FXML
    public void initialize() {
        //After the FXML is loaded

    }

    @FXML
    protected void changeTexture(ActionEvent event) {

    }

    @FXML
    public void changeLayer(MouseEvent mouseEvent) {
        //System.out.println("Selected " + layersListView.getSelectionModel().getSelectedItem().getLayer().getLayerName());
    }

    @FXML
    public void addLayer(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("popups/newlayer/NewLayerPopup.fxml"));
            Parent root = loader.load();
            NewLayerPopup controller = loader.getController();
            Scene scene = new Scene(root, NewFilePopup.prefWidth, NewFilePopup.prefHeight);
            stage.setTitle("New Layer");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(UI.primaryStage);
            stage.showAndWait();
            if(controller.isNewCreated()) {
                LayerListCell cell = new LayerListCell(loadedFiles.get(0), controller.getLayerName(), "mainui/mapeditor/cw1.jpg");
                layersListView.getItems().add(cell);
                mapTabPane.getTabs().get(0).setContent(cell.getLayer().getCanvas());
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void changeColor(ActionEvent actionEvent) {

    }

    @FXML
    public void deleteLayer(ActionEvent actionEvent) {

    }

    @FXML
    public void shiftLayerUp(ActionEvent actionEvent) {


    }

    @FXML
    public void shiftLayerDown(ActionEvent actionEvent) {

    }

    public void applyClipping(ActionEvent actionEvent) {

    }

    public void newMapFile(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("popups/newfile/NewFilePopup.fxml"));
            Parent root = loader.load();
            NewFilePopup controller = loader.getController();
            Scene scene = new Scene(root, NewFilePopup.prefWidth, NewFilePopup.prefHeight);
            stage.setTitle("New Map File");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(UI.primaryStage);
            stage.showAndWait();
            if(controller.isNewCreated()) {
                System.out.println(controller.getMapName()+ " width: " + controller.getWidth() + ", height: " + controller.getHeight());
                MapFile mapFile = new MapFile(controller.getMapName(), controller.getWidth(), controller.getHeight());
                loadedFiles.add(mapFile);
                ScrollPane scrollPane = new ScrollPane();
                scrollPane.setStyle("-fx-background-color: grey");
                Tab tab = new Tab(controller.getMapName(), scrollPane);
                mapTabPane.getTabs().add(tab);
                rightPane.setDisable(false);
                //layersListView = mapFile.getLayerList();

            }

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

}