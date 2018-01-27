package refactor;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import refactor.layer.DrawLayer;
import refactor.layer.FXLayer;
import refactor.layer.Layer;
import refactor.tool.DrawTool;
import refactor.tool.GridTool;
import refactor.tool.PenTool;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class Window extends Application{
    private Map map; //Only support one map at a time for now
    private int width;
    private int height;
    private Pane layersPane;
    private DrawTool drawTool;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("re:Dungeon Drafter <Final Mix>");
        Group root = new Group();
        this.width = 1260;
        this.height = 840;
        this.drawTool = new PenTool();
        this.map = new Map(70, this.width, this.height); //BAD!! Change so Window w,h and Map w,h can be different!

        BorderPane borderPane = new BorderPane();
        this.layersPane = new Pane();
        addLayer("Layer 0", new Image("img/textures/drt1.jpg"));
        addLayer("Layer 1", new Image("img/textures/grs1.jpg"));
        addLayer("Layer 2", new Image("img/textures/cw1.jpg"));
        addLayer("Layer 3", new Image("img/textures/cw2.jpg"));

        switchLayer(0);

        FXLayer fxLayer = new FXLayer(this.width, this.height, this.map);

        fxLayer.getCanvas().setOnMouseMoved(event -> {
            drawFx(fxLayer, event);
        });
        fxLayer.getCanvas().setOnMousePressed(event -> this.drawTool.mousePress(event, this.map.getSelectedLayer()));
        fxLayer.getCanvas().setOnMouseDragged(event -> {
            drawFx(fxLayer, event);
            this.drawTool.mouseDrag(event, this.map.getSelectedLayer());
        });
        fxLayer.getCanvas().setOnMouseReleased(event -> this.drawTool.mouseRelease(event, this.map.getSelectedLayer()));

        this.layersPane.getChildren().add(fxLayer.getGridLayer());
        this.layersPane.getChildren().add(fxLayer.getCanvas());
        fxLayer.getCanvas().toFront();

        borderPane.setCenter(layersPane);

        /*
         *  The following is pure menu bar code. All temporary, could use helper methods
         */
        Menu edit = new Menu("Edit");
        MenuItem undo = new MenuItem("Undo");
        MenuItem redo = new MenuItem("Redo");
        edit.getItems().addAll(undo, redo);
        undo.setOnAction(this::undo);
        redo.setOnAction(this::redo);
        undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        Menu drawMode = new Menu("Draw Mode");
        MenuItem gridDrawMode = new MenuItem("Grid Mode");
        MenuItem penDrawMode = new MenuItem("Pen Mode");
        penDrawMode.setAccelerator(new KeyCodeCombination(KeyCode.B));
        gridDrawMode.setAccelerator(new KeyCodeCombination(KeyCode.G));
        drawMode.getItems().addAll(gridDrawMode, penDrawMode);
        gridDrawMode.setOnAction(event -> setDrawTool(new GridTool()));
        penDrawMode.setOnAction(event -> setDrawTool(new PenTool()));
        Menu layersMenu = new Menu("Layer");
        MenuItem layer0MenuItem = new MenuItem("Dirt");
        MenuItem layer1MenuItem = new MenuItem("Grass");
        MenuItem layer2MenuItem = new MenuItem("Cave Floor");
        MenuItem layer3MenuItem = new MenuItem("Cave Wall");
        MenuItem layerPrintItem = new MenuItem("Snapshot Layer");
        layersMenu.getItems().addAll(layer0MenuItem, layer1MenuItem, layer2MenuItem, layer3MenuItem, layerPrintItem);
        layer0MenuItem.setOnAction(event -> switchLayer(0));
        layer1MenuItem.setOnAction(event -> switchLayer(1));
        layer2MenuItem.setOnAction(event -> switchLayer(2));
        layer3MenuItem.setOnAction(event -> switchLayer(3));
        layerPrintItem.setOnAction(event -> snapshotLayer());
        layer0MenuItem.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT1));
        layer1MenuItem.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT2));
        layer2MenuItem.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT3));
        layer3MenuItem.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT4));
        layerPrintItem.setAccelerator(new KeyCodeCombination(KeyCode.P));
        Menu settingsMenu = new Menu("Settings");
        CheckMenuItem shadowsCheck = new CheckMenuItem("Shadows");
        MenuItem increaseTool = new MenuItem("Increase Tool Size");
        MenuItem decreaseTool = new MenuItem("Decrease Tool Size");
        CheckMenuItem hideCheck = new CheckMenuItem("Hide");
        CheckMenuItem gridCheck = new CheckMenuItem("Show Grid");
        MenuItem increaseOpac = new MenuItem("Increase Tool Opacity");
        MenuItem decreaseOpac = new MenuItem("Decrease Tool Opacity");
        shadowsCheck.setOnAction(event -> toggleShadow(shadowsCheck.isSelected()));
        hideCheck.setOnAction(event -> toggleHide(hideCheck.isSelected()));
        increaseTool.setOnAction(event -> increaseToolSize());
        decreaseTool.setOnAction(event -> decreaseToolSize());
        increaseOpac.setOnAction(event -> increaseOpacity());
        decreaseOpac.setOnAction(event -> decreaseOpacity());
        //gridCheck.setOnAction(event -> toggleGrid(gridCheck.isSelected(), gridFX));
        shadowsCheck.setAccelerator(new KeyCodeCombination(KeyCode.S));
        hideCheck.setAccelerator(new KeyCodeCombination(KeyCode.H));
        increaseTool.setAccelerator(new KeyCodeCombination(KeyCode.CLOSE_BRACKET));
        decreaseTool.setAccelerator(new KeyCodeCombination(KeyCode.OPEN_BRACKET));
        increaseOpac.setAccelerator(new KeyCodeCombination(KeyCode.EQUALS));
        decreaseOpac.setAccelerator(new KeyCodeCombination(KeyCode.MINUS));
        gridCheck.setAccelerator(new KeyCodeCombination(KeyCode.T));
        settingsMenu.getItems().addAll(shadowsCheck, hideCheck, gridCheck, increaseTool, decreaseTool, increaseOpac, decreaseOpac);
        //toggleGrid(false, gridFX);
        MenuBar menuBar = new MenuBar(edit, layersMenu, drawMode, settingsMenu);
        borderPane.setTop(menuBar);

        root.getChildren().add(borderPane);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void snapshotLayer() {
        File file = new File("sample.png");
        SnapshotParameters temp = new SnapshotParameters();
        temp.setViewport(new Rectangle2D(300, 300, 300, 300));
        temp.setFill(Color.TRANSPARENT);
        WritableImage wi = map.getSelectedLayer().getCanvas().snapshot(temp, null);

        RenderedImage ri = SwingFXUtils.fromFXImage(wi, null);
        try {
            ImageIO.write(ri, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawFx(FXLayer fxLayer, MouseEvent event) {
        fxLayer.getCanvas().getGraphicsContext2D().clearRect(0, 0, this.width, this.height);
        this.drawTool.drawFX(event, fxLayer);
    }

    private void toggleGrid(boolean selected) {
        //setVisible(selected);
    }


    private void addLayer(String name, Image image) {
        Layer temp = new DrawLayer(this.width, this.height, name, this.map, image);
        temp.getCanvas().setOnMousePressed(event -> drawTool.mousePress(event, this.map.getSelectedLayer()));
        temp.getCanvas().setOnMouseDragged(event -> drawTool.mouseDrag(event, this.map.getSelectedLayer()));
        temp.getCanvas().setOnMouseReleased(event -> drawTool.mouseRelease(event, this.map.getSelectedLayer()));
        this.map.addLayer(temp);
        this.layersPane.getChildren().add(temp.getCanvas());
    }

    private void undo(ActionEvent event) {
        this.map.getInvoker().undo();
    }

    private void redo(ActionEvent event) {
        this.map.getInvoker().redo();
    }

    private void setDrawTool(DrawTool tool) {
        this.drawTool = tool;
    }

    private void switchLayer(int index) {
        this.map.setSelectedLayer(index);
    }

    private void toggleHide(boolean selected) {
        if(selected) {
            this.map.getSelectedLayer().getCanvas().setOpacity(0);
        }else {
            this.map.getSelectedLayer().getCanvas().setOpacity(1);
        }
    }

    private void toggleShadow(boolean selected) {
        //this.map.getInvoker().invoke(new ShadowEffectCommand(this.map.getSelectedLayer().getCanvas(), 55.0));
        if(selected) {
            //this.map.getSelectedLayer().getCanvas().setEffect(new DropShadow(55.0, Color.BLACK));
        } else {
            //this.map.getSelectedLayer().getCanvas().setEffect(null);
        }
    }

    private void increaseToolSize() {
        if(drawTool instanceof PenTool) {
            ((PenTool) drawTool).increaseToolSize();
        }
    }

    private void decreaseToolSize() {
        if(drawTool instanceof PenTool) {
            ((PenTool) drawTool).decreaseToolSize();
        }
    }

    private void increaseOpacity() {
        this.map.getSelectedLayer().getCanvas().getGraphicsContext2D().setGlobalAlpha(this.map.getSelectedLayer().getCanvas().getGraphicsContext2D().getGlobalAlpha() + 0.1);
        if(this.map.getSelectedLayer().getCanvas().getGraphicsContext2D().getGlobalAlpha() > 1) {
            this.map.getSelectedLayer().getCanvas().getGraphicsContext2D().setGlobalAlpha(1);
        }
    }

    private void decreaseOpacity() {
        this.map.getSelectedLayer().getCanvas().getGraphicsContext2D().setGlobalAlpha(this.map.getSelectedLayer().getCanvas().getGraphicsContext2D().getGlobalAlpha() - 0.1);
        if(this.map.getSelectedLayer().getCanvas().getGraphicsContext2D().getGlobalAlpha() < 0) {
            this.map.getSelectedLayer().getCanvas().getGraphicsContext2D().setGlobalAlpha(0);
        }
    }


}
