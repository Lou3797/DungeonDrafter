package mainui.popups.newfile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class NewFilePopup {
    public static final int prefWidth = 500;
    public static final int prefHeight = 175;

    @FXML public Button okButton;
    @FXML private Label mirrorGridLabel;
    @FXML private TextField gridSizeTextField;
    @FXML private TextField mapNameTextField;
    @FXML private TextField widthTextField;
    @FXML private TextField heightTextField;
    private boolean createNew;

    public NewFilePopup() {
        createNew = false;
    }

    @FXML
    private void createNewFile(ActionEvent actionEvent) {
        createNew = true;
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void updateGridLabel(KeyEvent actionEvent) {
        mirrorGridLabel.setText(gridSizeTextField.getText());
    }

    public String getMapName() {
        return mapNameTextField.getText();
    }

    public int getWidth() {
        return new Integer(widthTextField.getText());
    }

    public int getHeight() {
        return new Integer(heightTextField.getText());
    }

    public boolean isNewCreated() {
        return createNew;
    }
}