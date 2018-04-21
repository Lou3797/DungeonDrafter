package window;

import drawtool.BrushTool;
import drawtool.DrawStrategy;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Window extends Application {
    public static Stage primaryStage;
    private DrawStrategy drawTool;
    private int width;
    private int height;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.drawTool = new BrushTool();
        this.width = 600;
        this.height = 450;
        primaryStage.setTitle("Re:Dungeon Drafter <Final Mix> Re:coded");
        Group root = new Group();

        Canvas canvas = new Canvas(this.width, this.height);
        Canvas scratchCanvas = new Canvas(this.width, this.height);
        //rigCanvas(canvas); //Only the scratch canvas needs to be rigged
        rigCanvas(scratchCanvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().addAll(canvas, scratchCanvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        Window.primaryStage = primaryStage;
    }

    private void rigCanvas(Canvas canvas) {
        canvas.setOnMousePressed(event -> {
            drawTool.mousePressed(event);
        });
        canvas.setOnMouseDragged(event -> {
            drawTool.mouseDragged(event);
        });
        canvas.setOnMouseReleased(event -> {
            drawTool.mouseReleased(event);
        });
    }
}
