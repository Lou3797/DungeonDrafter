package mainui.popups.newlayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mainui.cells.TextureCell;

import java.io.File;

public class NewLayerPopup {

    @FXML private ComboBox<TextureCell> textureCombo;
    @FXML private Button okButton;
    @FXML private TextField layerNameField;
    private File selectedTexture;
    private boolean createLayer;

    public NewLayerPopup() {
        createLayer = false;
    }

    @FXML
    public void initialize() {
        textureCombo.getItems().add(new TextureCell(new File("textures/cw1.jpg")));
        textureCombo.getItems().add(new TextureCell(new File("textures/cw2.jpg")));
        textureCombo.getItems().add(new TextureCell(new File("textures/drt1.jpg")));
        textureCombo.getItems().add(new TextureCell(new File("textures/gr1.png")));
    }

    @FXML
    public void createNewLayer(ActionEvent actionEvent) {
        createLayer = true;
        selectedTexture = textureCombo.getSelectionModel().getSelectedItem().getTextureFile();
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public String getLayerName() {
        return layerNameField.getText();
    }

    public boolean isNewCreated() {
        return createLayer;
    }

    public File getSelectedTexture() {
        return selectedTexture;
    }
}