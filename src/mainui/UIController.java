package mainui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import mainui.popups.newfile.NewFilePopup;
import mainui.popups.newlayer.NewLayerPopup;
import mapeditor.MapFile;
import mainui.cells.LayerListCell;

import java.io.IOException;
import java.util.ArrayList;

public class UIController {

    private ArrayList<MapFile> loadedFiles;
    private ArrayList<Pane> mapPanes;
    @FXML private VBox rightPane;
    @FXML private Pane mapPane;
    @FXML private ListView<LayerListCell> layersListView;
    @FXML private MenuBar menuBar;
    @FXML private TabPane mapTabPane;

    public UIController() {
        //Before the FXML is loaded
        loadedFiles = new ArrayList<>();
        mapPanes = new ArrayList<>();
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
                LayerListCell cell = new LayerListCell(loadedFiles.get(0), controller.getLayerName(), controller.getSelectedTexture().getPath());
                layersListView.getItems().add(layersListView.getSelectionModel().getSelectedIndex()+1, cell);
                //mapTabPane.getTabs().get(0).setContent(cell.getLayer().getCanvas());
                mapPanes.get(0).getChildren().add(cell.getLayer().getCanvas());
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
                Pane pane = new Pane();
                mapPanes.add(pane);
                scrollPane.setContent(pane);
                pane.setStyle("-fx-background-color: grey");
                Tab tab = new Tab(controller.getMapName(), scrollPane);
                tab.setOnSelectionChanged(event -> changeMapFile());
                //tab.onSelectionChangedProperty().addListener(event -> changeMapFile());
                mapTabPane.getTabs().add(tab);
                rightPane.setDisable(false);
                //layersListView = mapFile.getLayerList();

            }

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    private void changeMapFile() {
        //int selected = mapTabPane.getSelectionModel().getSelectedIndex();
        //layersListView = loadedFiles.get(selected).getLayerList();


        //layersListView.getItems().setAll(loadedFiles.get(mapTabPane.getSelectionModel().getSelectedIndex()).getLayerList().getItems());
    }

}