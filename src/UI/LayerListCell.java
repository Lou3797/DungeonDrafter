package UI;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;

public class LayerListCell extends GridPane {

    private ToggleButton visibleToggle;
    private ToggleButton shadowsToggle;
    private ImagePattern texturePattern;
    private Image displayImage;
    private Label layerNameLabel;
    private Label opacityLabel;
    private Button optionsButton;

    public LayerListCell(String layerName, String imageURL) {
        super();
        visibleToggle = new ToggleButton("V");
        visibleToggle.setSelected(true);
        shadowsToggle = new ToggleButton("S");
        texturePattern = new ImagePattern(new Image(imageURL));
        displayImage = new Image(imageURL, 50, 50, true, false);
        layerNameLabel = new Label(layerName);
        ImageView imageView = new ImageView(displayImage);
        opacityLabel = new Label("100%");
        optionsButton = new Button("O");

        setAlignment(Pos.CENTER_LEFT);
        setVgap(5);
        setHgap(3);
        setPadding(new Insets(2, 2, 2, 2));

        add(visibleToggle, 0, 0);
        add(shadowsToggle, 0, 1);
        add(imageView, 1, 0, 1, 2);
        add(layerNameLabel, 2, 0, 2, 1);
        add(opacityLabel, 2, 1);

        setGridLinesVisible(true);
        ColumnConstraints column0 = new ColumnConstraints();
        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        ColumnConstraints column3 = new ColumnConstraints();

        //column3.setFillWidth(true);
        column3.setPercentWidth(50);
        //column3.setMinWidth(50);
        getColumnConstraints().addAll(column0, column1, column2, column3);

        add(optionsButton, 3, 1);
        GridPane.setHalignment(optionsButton, HPos.RIGHT);

    }

}