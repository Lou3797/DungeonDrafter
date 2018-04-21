package window;

import drawtool.BrushTool;
import drawtool.DrawStrategy;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.drawTool = new BrushTool();
        this.width = 600;
        this.height = 450;
        this.maps = new ArrayList<>();

        Map map = new Map(this.width, this.height);
        maps.add(map);

        primaryStage.setTitle("Re:Dungeon Drafter <Final Mix> Re:coded");
        Group root = new Group();

        //Canvas canvas = new Canvas(this.width, this.height);
        //Canvas scratchCanvas = new Canvas(this.width, this.height);
        //rigCanvas(canvas); //Only the scratch canvas needs to be rigged
        rigCanvas(scratchCanvas);
        this.canvasStack.add(canvas);
        this.canvasStack.add(scratchCanvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().addAll(canvasStack);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        Window.primaryStage = primaryStage;
    }
}
