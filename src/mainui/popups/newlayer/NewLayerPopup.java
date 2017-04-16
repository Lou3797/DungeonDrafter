package mainui.popups.newlayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewLayerPopup {

    @FXML private ComboBox textureCombo;
    @FXML private Button okButton;
    @FXML private TextField layerNameField;
    private boolean createLayer;

    public NewLayerPopup() {
        createLayer = false;
    }

    @FXML
    public void initialize() {
        textureCombo.getItems().add("One");
        textureCombo.getItems().add("Two");
        textureCombo.getItems().add("Three");
    }

    @FXML
    public void createNewLayer(ActionEvent actionEvent) {
        createLayer = true;
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public String getLayerName() {
        return layerNameField.getText();
    }

    public boolean isNewCreated() {
        return createLayer;
    }
}