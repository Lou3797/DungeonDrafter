package refactor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
        this.width = 700;
        this.height = 560;
        this.drawTool = new PenTool();
        this.map = new Map(70, width, height); //BAD!! Change so Window w,h and Map w,h can be different!

        BorderPane borderPane = new BorderPane();
        this.layersPane = new Pane();
        Layer layer1 = new Layer(width, height, "Layer 1");
        Layer layer2 = new Layer(width, height, "Layer 2");
        Layer layer3 = new Layer(width, height, "Layer 3");
        this.map.addLayer(layer1);
        this.map.addLayer(layer2);
        this.map.addLayer(layer3);
        this.layersPane.getChildren().addAll(layer1.getCanvas(), layer2.getCanvas(), layer3.getCanvas());
        switchLayer(2);


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
        MenuItem layer0MenuItem = new MenuItem("Layer 1");
        MenuItem layer1MenuItem = new MenuItem("Layer 2");
        MenuItem layer2MenuItem = new MenuItem("Layer 3");
        layersMenu.getItems().addAll(layer0MenuItem, layer1MenuItem, layer2MenuItem);
        layer0MenuItem.setOnAction(event -> switchLayer(0));
        layer1MenuItem.setOnAction(event -> switchLayer(1));
        layer2MenuItem.setOnAction(event -> switchLayer(2));
        layer0MenuItem.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT1));
        layer1MenuItem.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT2));
        layer2MenuItem.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT3));

        MenuBar menuBar = new MenuBar(edit, layersMenu, drawMode);
        borderPane.setTop(menuBar);

        root.getChildren().add(borderPane);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
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
        this.map.getSelectedLayer().getCanvas().toFront();
    }

}
