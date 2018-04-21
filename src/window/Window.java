package window;

import drawtool.BrushTool;
import drawtool.DrawStrategy;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import map.Map;
import map.layer.Layer;

import java.util.ArrayList;
import java.util.List;

public class Window extends Application {
    public static Stage primaryStage;
    private DrawStrategy drawTool;
    public int width;
    public int height;
    private List<Map> maps;
    private int currentMapIndex;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.drawTool = new BrushTool();
        this.width = 600;
        this.height = 450;
        this.maps = new ArrayList<>();

        Map map = new Map(this.width, this.height);
        this.maps.add(map);
        this.currentMapIndex = 0;

        primaryStage.setTitle("Re:Dungeon Drafter <Final Mix> Re:coded");
        Group root = new Group();

        BorderPane borderPane = new BorderPane();
        Pane layersPane = new Pane();
        layersPane.getChildren().addAll(map.getLayers());
        borderPane.setCenter(layersPane);

        Menu edit = new Menu("Edit");
        MenuItem undo = new MenuItem("Undo");
        MenuItem redo = new MenuItem("Redo");
        undo.setOnAction(this::undo);
        redo.setOnAction(this::redo);
        edit.getItems().addAll(undo, redo);
        undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        MenuBar menuBar = new MenuBar(edit);

        root.getChildren().add(borderPane);
        rigCanvas(map.getScratchLayer());

        borderPane.setTop(menuBar);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        Window.primaryStage = primaryStage;
    }

    private void rigCanvas(Layer layer) {
        layer.setOnMousePressed(event -> {
            this.drawTool.mousePressed(event, getCurrentMap());
        });
        layer.setOnMouseDragged(event -> {
            this.drawTool.mouseDragged(event, getCurrentMap());
        });
        layer.setOnMouseReleased(event -> {
            this.drawTool.mouseReleased(event, getCurrentMap());
        });
    }

    public Map getCurrentMap() {
        return this.maps.get(this.currentMapIndex);
    }

    private boolean undo(ActionEvent event) {
        return this.maps.get(this.currentMapIndex).getInvoker().undo();
    }

    private boolean redo(ActionEvent event) {
        return this.maps.get(this.currentMapIndex).getInvoker().redo();
    }
}
