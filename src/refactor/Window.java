package refactor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
        primaryStage.setTitle("RE:Dungeon Drafter <Final Mix>");
        Group root = new Group();
        this.width = 1260;
        this.height = 840;
        this.drawTool = new PenTool();
        this.map = new Map(70, width, height); //BAD!! Change so Window w,h and Map w,h can be different!

        BorderPane borderPane = new BorderPane();
        this.layersPane = new Pane();
        /*
        Layer layer1 = new Layer(width, height, "Layer 1");
        Layer layer2 = new Layer(width, height, "Layer 2");
        Layer layer3 = new Layer(width, height, "Layer 3");
        this.map.addLayer(layer1);
        this.map.addLayer(layer2);
        this.map.addLayer(layer3);
        this.layersPane.getChildren().addAll(layer1.getCanvas(), layer2.getCanvas(), layer3.getCanvas());
        */
        addLayer("Layer 0", new Image("img/textures/drt1.jpg"));
        addLayer("Layer 1", new Image("img/textures/grs1.jpg"));
        addLayer("Layer 2", new Image("img/textures/cw1.jpg"));
        addLayer("Layer 3", new Image("img/textures/cw2.jpg"));

        switchLayer(0);

        GridFXLayer gridFX = new GridFXLayer(this.width, this.height, this.map.getGridSize());
        layersPane.getChildren().add(gridFX.getCanvas());
        gridFX.getCanvas().setOnMousePressed(event -> drawTool.mousePress(event, this.map.getSelectedLayer()));
        gridFX.getCanvas().setOnMouseDragged(event -> drawTool.mouseDrag(event, this.map.getSelectedLayer()));
        gridFX.getCanvas().setOnMouseReleased(event -> drawTool.mouseRelease(event, this.map.getSelectedLayer()));


        //gridFX.getCanvas().toFront();

        borderPane.setCenter(layersPane);

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
        layersMenu.getItems().addAll(layer0MenuItem, layer1MenuItem, layer2MenuItem, layer3MenuItem);
        layer0MenuItem.setOnAction(event -> switchLayer(0));
        layer1MenuItem.setOnAction(event -> switchLayer(1));
        layer2MenuItem.setOnAction(event -> switchLayer(2));
        layer3MenuItem.setOnAction(event -> switchLayer(3));
        layer0MenuItem.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT1));
        layer1MenuItem.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT2));
        layer2MenuItem.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT3));
        layer3MenuItem.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT4));

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
        gridCheck.setOnAction(event -> toggleGrid(gridCheck.isSelected(), gridFX));
        shadowsCheck.setAccelerator(new KeyCodeCombination(KeyCode.S));
        hideCheck.setAccelerator(new KeyCodeCombination(KeyCode.H));
        increaseTool.setAccelerator(new KeyCodeCombination(KeyCode.CLOSE_BRACKET));
        decreaseTool.setAccelerator(new KeyCodeCombination(KeyCode.OPEN_BRACKET));
        increaseOpac.setAccelerator(new KeyCodeCombination(KeyCode.EQUALS));
        decreaseOpac.setAccelerator(new KeyCodeCombination(KeyCode.MINUS));
        gridCheck.setAccelerator(new KeyCodeCombination(KeyCode.T));

        settingsMenu.getItems().addAll(shadowsCheck, hideCheck, gridCheck, increaseTool, decreaseTool, increaseOpac, decreaseOpac);
        toggleGrid(false, gridFX);

        MenuBar menuBar = new MenuBar(edit, layersMenu, drawMode, settingsMenu);
        borderPane.setTop(menuBar);

        root.getChildren().add(borderPane);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void toggleGrid(boolean selected, GridFXLayer gridFXLayer) {
        gridFXLayer.setVisible(selected);
    }


    private void addLayer(String name, Image image) {
        Layer temp = new Layer(this.width, this.height, name, this.map, image);
        temp.getCanvas().setOnMousePressed(event -> drawTool.mousePress(event, this.map.getSelectedLayer()));
        temp.getCanvas().setOnMouseDragged(event -> drawTool.mouseDrag(event, this.map.getSelectedLayer()));
        temp.getCanvas().setOnMouseReleased(event -> drawTool.mouseRelease(event, this.map.getSelectedLayer()));
        this.map.addLayer(temp);
        this.layersPane.getChildren().add(temp.getCanvas());
    }

    private void undo(ActionEvent event) {
        map.getInvoker().undo();
    }

    private void redo(ActionEvent event) {
        map.getInvoker().redo();
    }

    private void setDrawTool(DrawTool tool) {
        drawTool = tool;
    }

    private void switchLayer(int index) {
        this.map.setSelectedLayer(index);
        //this.map.getSelectedLayer().getCanvas().toFront(); //This is used to manipulate draw order
    }

    private void toggleHide(boolean selected) {
        if(selected) {
            this.map.getSelectedLayer().getCanvas().setOpacity(0);
        }else {
            this.map.getSelectedLayer().getCanvas().setOpacity(1);
        }
    }

    private void toggleShadow(boolean selected) {
        if(selected) {
            this.map.getSelectedLayer().getCanvas().setEffect(new DropShadow(55.0, Color.BLACK));
        } else {
            this.map.getSelectedLayer().getCanvas().setEffect(null);
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
        //System.out.println(this.map.getSelectedLayer().getCanvas().getGraphicsContext2D().getGlobalAlpha());
    }

    private void decreaseOpacity() {
        this.map.getSelectedLayer().getCanvas().getGraphicsContext2D().setGlobalAlpha(this.map.getSelectedLayer().getCanvas().getGraphicsContext2D().getGlobalAlpha() - 0.1);
        if(this.map.getSelectedLayer().getCanvas().getGraphicsContext2D().getGlobalAlpha() < 0) {
            this.map.getSelectedLayer().getCanvas().getGraphicsContext2D().setGlobalAlpha(0);
        }
        //System.out.println(this.map.getSelectedLayer().getCanvas().getGraphicsContext2D().getGlobalAlpha());
    }


}
