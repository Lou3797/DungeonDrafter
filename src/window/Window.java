package window;

import drawtool.BrushTool;
import drawtool.DrawStrategy;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import map.Map;

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
        TabPane tabPane = new TabPane();
        Tab tab = new Tab();
        Pane layersPane = new Pane();
        layersPane.getChildren().addAll(map.getLayers());
        borderPane.setCenter(layersPane);

        Menu file = new Menu("File");
        MenuItem newMap = new MenuItem("New Map");
        newMap.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        newMap.setOnAction(this::newMap);
        file.getItems().setAll(newMap);


        Menu edit = new Menu("Edit");
        MenuItem undo = new MenuItem("Undo");
        MenuItem redo = new MenuItem("Redo");
        MenuItem zoom = new MenuItem("Zoom");
        undo.setOnAction(this::undo);
        redo.setOnAction(this::redo);
        zoom.setOnAction(this::zoom);
        edit.getItems().addAll(undo, redo, zoom);
        undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        zoom.setAccelerator(new KeyCodeCombination(KeyCode.Z));
        MenuBar menuBar = new MenuBar(file, edit);
        borderPane.setTop(menuBar);

        root.getChildren().add(borderPane);
        getCurrentMap().rigCanvasScratchLayer(this.drawTool);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        Window.primaryStage = primaryStage;
    }



    public Map getCurrentMap() {
        return this.maps.get(this.currentMapIndex);
    }

    private void newMap(ActionEvent event) {
        System.out.println("New Map");
    }

    private boolean undo(ActionEvent event) {
        return this.maps.get(this.currentMapIndex).getInvoker().undo();
    }

    private boolean redo(ActionEvent event) {
        return this.maps.get(this.currentMapIndex).getInvoker().redo();
    }

    private void zoom(ActionEvent event) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image temp = getCurrentMap().getCurrentLayer().snapshot(params, null);
        PixelReader pr = temp.getPixelReader();

        getCurrentMap().getCurrentLayer().getGraphicsContext2D().clearRect(0, 0, this.width, this.height);
        PixelWriter pw = getCurrentMap().getCurrentLayer().getGraphicsContext2D().getPixelWriter();
        for(int x = 0; x < this.width; x+=2) {
            for(int y = 0; y < this.height; y+=2) {
                int argb = pr.getArgb(x, y) + pr.getArgb(x+1, y+1) + pr.getArgb(x, y+1) + pr.getArgb( x+1, y);
                argb = argb/4;
                pw.setArgb((x/2)+(this.width/4), (y/2)+(this.height/4), argb);
            }
        }

    }
}
