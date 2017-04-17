package mainui.mapeditor;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class ClippingGroup {
    private ImagePattern texture;
    private Canvas base;

    public ClippingGroup(Canvas base, Image texture) {
        this.base = base;
        setTexture(texture);
    }

    public Canvas getBase() {
        return base;
    }

    public void setTexture(Image texture) {
        this.texture = new ImagePattern(texture, 0, 0, texture.getWidth(), texture.getHeight(), false);
    }

    public void applyClipping() {
        /*Canvas textureFill = new Canvas(base.getWidth(), base.getHeight());
        textureFill.getGraphicsContext2D().setFill(texture);
        textureFill.getGraphicsContext2D().fillRect(0, 0, textureFill.getWidth(), textureFill.getHeight());

        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);
        PixelReader textureReader = textureFill.snapshot(sp, null).getPixelReader();
        PixelReader baseReader = base.snapshot(sp, null).getPixelReader();
        PixelWriter writer = base.getGraphicsContext2D().getPixelWriter();

        for(int y = 0; y < base.getHeight(); y++) {
            for(int x = 0; x < base.getWidth(); x++) {
                int textureARGB = textureReader.getArgb(x, y);
                int textureAlpha = 0xFF & (textureARGB >> 24);
                int baseARGB = baseReader.getArgb(x, y);
                int baseAlpha = 0xFF & (baseARGB >> 24);
                if(baseAlpha != 0 && textureAlpha != 0) {
                    int resultARGB = (baseAlpha << 24) | ((0xFF & ( textureARGB >> 16)) << 16 ) | ((0xFF & (textureARGB >> 8 )) << 8) | 0xFF & (textureARGB >> 0);
                    writer.setArgb(x, y, resultARGB);
                }
            }
        }*/

    }

}