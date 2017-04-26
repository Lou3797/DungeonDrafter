package mainui.cells;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;

public class TextureCell extends HBox {

    private File textureFile;
    private ImageView textureView;
    private Label textureName;

    public TextureCell(File imageFile) {
        super(6);
        textureFile = imageFile;
        Image texture = new Image(textureFile.getPath(), 100, 100, true, false);
        textureView = new ImageView(texture);
        textureName = new Label(imageFile.getName());
        setAlignment(Pos.CENTER_LEFT);
        getChildren().add(textureView);
        getChildren().add(textureName);
    }

    public File getTextureFile() {
        return textureFile;
    }

    public ImageView getTextureView() {
        return textureView;
    }

    public Label getTextureName() {
        return textureName;
    }

}