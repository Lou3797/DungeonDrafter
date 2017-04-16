package mapeditor.layerlist.fxmlcell;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mapeditor.MapFile;
import mapeditor.layerlist.Layer;

public class LayerListCellFXML extends Parent {

    @FXML private ImageView textureThumbnail;
    @FXML private ToggleButton visibleToggle;
    @FXML private ToggleButton shadowsToggle;
    @FXML private Image displayImage;
    @FXML private Label layerNameLabel;
    @FXML private Label opacityLabel;
    @FXML private Button optionsButton;
    private Layer layer;

    public LayerListCellFXML() {
        super();
    }

    @FXML
    public void initialize() {

    }

    public void initData(MapFile owner, String layerName, String imageURL) {
        layerNameLabel.setText(layerName);
        Image image = new Image(imageURL);
        layer = new Layer(owner, layerName, image);
    }

    public Layer getLayer() {
        return layer;
    }

}