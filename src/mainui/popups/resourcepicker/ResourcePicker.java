package mainui.popups.resourcepicker;

import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;
import mainui.UI;

import java.io.File;

public class ResourcePicker {
    public void selectFolder(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Textures Folder");
        chooser.setInitialDirectory(new File(System.getProperty("user.dir") + File.separator));
        File selectedDirectory = chooser.showDialog(UI.primaryStage);
    }
}