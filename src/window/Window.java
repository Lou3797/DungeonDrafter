package window;

import drawtool.BrushTool;
import drawtool.DrawStrategy;
import filetype.reader.DDMReader;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import map.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * The main window of Dungeon Drafter. Holds all UI elements.
 *
 * @author rothnj (Lou3797@github)
 * @version 2018.4.24
 * @since 4/21/2018
 */
public class Window extends Application {
    /**
     * The primary stage for this application. Static so popup windows can refer to it.
     */
    public static Stage primaryStage;

    /**
     * The drawing tool selected by the user.
     */
    private DrawStrategy drawTool;

    /**
     * Window width.
     */
    private int width;

    /**
     * Window height.
     */
    private int height;

    /**
     * Internal list of opened Maps.
     */
    private List<Map> maps;

    /**
     * Maps are added to this as they are opened.
     */
    private TabPane mapTabs;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.drawTool = new BrushTool();
        this.width = 600;
        this.height = 450;
        this.maps = new ArrayList<>();

        primaryStage.setTitle("Re:Dungeon Drafter <Final Mix> Re:coded");
        Group root = new Group();
        BorderPane borderPane = new BorderPane();
        this.mapTabs = new TabPane();
        borderPane.setCenter(this.mapTabs);

        //Create Menu for "File" options
        Menu file = new Menu("File");
        MenuItem newMap = new MenuItem("New Map");
        MenuItem load = new MenuItem("Open");
        newMap.setOnAction(this::newMap);
        load.setOnAction(this::open);
        newMap.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        load.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        file.getItems().addAll(newMap, load);

        //Create Menu for "Edit" options
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
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        borderPane.setTop(menuBar);

        root.getChildren().add(borderPane);
        primaryStage.setScene(new Scene(root, this.width, this.height));
        primaryStage.show();
        Window.primaryStage = primaryStage;
    }

    /**
     * Refers to the TabPane selection model and fetches the map from the internal List at the same index.
     * @return The currently selected Map in the TabPane.
     */
    public Map getCurrentMap() {
        if(this.maps.size() == 0) {
            return null;
        } else {
            return this.maps.get(this.mapTabs.getSelectionModel().getSelectedIndex());
        }

    }

    /**
     * Creates a new Map using the default constructor.
     * @param event The event Object for this action.
     */
    private void newMap(ActionEvent event) {
        System.out.println("New Map");
        Map map = new Map(this.width, this.height);
        addMapToTabs(map);
    }

    /**
     * Adds the given map to the TabPane as a new Tab and sets its mouse events to use the DrawTool events.
     * @param map The Map being added.
     */
    private void addMapToTabs(Map map) {
        this.maps.add(map);
        map.rigCanvasScratchLayer(this.drawTool);
        Pane mapPane = new Pane();
        ScrollPane scrollPane = new ScrollPane(mapPane);

        mapPane.getChildren().addAll(map.getLayers());
        Tab tab = new Tab();
        tab.setOnClosed(event1 -> {

        });
        tab.setOnSelectionChanged(event1 -> {

        });
        tab.setText(map.getName());
        tab.setContent(scrollPane);
        this.mapTabs.getTabs().add(tab);
        this.mapTabs.getSelectionModel().select(this.maps.size()-1);
    }

    /**
     * Opens a FileChooser and allows the user to load an external map from a file.
     * @param event The event Object for this action.
     */
    private void open(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Dungeon Drafter Map File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Dungeon Drafter Maps", "*.ddm"));
        File file = fileChooser.showOpenDialog(primaryStage);
        if(file != null) {
            try {
                DDMReader reader = new DDMReader(file);
                addMapToTabs(reader.getMap());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Calls the Invoker's undo method for the currently selected Map.
     * @param event The event Object for this action.
     * @return True if the undo was successful.
     */
    private boolean undo(ActionEvent event) {
        return getCurrentMap().getInvoker().undo();
    }

    /**
     * Calls the Invoker's redo method for the currently selected Map.
     * @param event The event Object for this action.
     * @return True if the redo was successful.
     */
    private boolean redo(ActionEvent event) {
        return getCurrentMap().getInvoker().redo();
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
