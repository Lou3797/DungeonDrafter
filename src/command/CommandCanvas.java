package command;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Stack;

public class CommandCanvas extends Application {

    private GraphicsContext gc;
    private int penSize;
    private Canvas layer;
    private Point current;
    private Point upperLeft;
    private Point lowerRight;
    private PixelReader snapshotReader;
    private Invoker invoker;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        invoker = new Invoker();
        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        int width = 500;
        int height = 450;
        layer = new Canvas(width, height);

        BorderPane borderPane = new BorderPane();
        Pane pane = new Pane();
        pane.getChildren().add(layer);
        layer.toFront();
        borderPane.setCenter(pane);

        Menu edit = new Menu("Edit");
        MenuItem undo = new MenuItem("Undo");
        MenuItem redo = new MenuItem("Redo");
        edit.getItems().addAll(undo, redo);
        undo.setOnAction(this::undo);
        redo.setOnAction(this::redo);
        undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));


        Menu color = new Menu("Color");
        MenuItem red = new MenuItem("Red");
        MenuItem green = new MenuItem("Green");
        MenuItem blue = new MenuItem("Blue");
        MenuItem black = new MenuItem("Black");
        MenuItem white = new MenuItem("White");
        red.setId("red");
        green.setId("green");
        blue.setId("blue");
        black.setId("black");
        white.setId("white");
        Menu pen = new Menu("Pen Size");

        MenuItem six = new MenuItem("6 px");
        MenuItem twelve = new MenuItem("12 px");
        MenuItem eighteen = new MenuItem("18 px");
        MenuItem twentyfour = new MenuItem("24 px");
        MenuItem thirty = new MenuItem("30 px");
        MenuItem sixty = new MenuItem("60 px");
        six.setId("6");
        twelve.setId("12");
        eighteen.setId("18");
        twentyfour.setId("24");
        thirty.setId("30");
        sixty.setId("60");
        six.addEventHandler(ActionEvent.ACTION, this::setPenSize);
        twelve.addEventHandler(ActionEvent.ACTION, this::setPenSize);
        eighteen.addEventHandler(ActionEvent.ACTION, this::setPenSize);
        twentyfour.addEventHandler(ActionEvent.ACTION, this::setPenSize);
        thirty.addEventHandler(ActionEvent.ACTION, this::setPenSize);
        sixty.addEventHandler(ActionEvent.ACTION, this::setPenSize);
        pen.getItems().addAll(six, twelve, eighteen, twentyfour, thirty, sixty);

        red.addEventHandler(ActionEvent.ACTION, this::setColor);
        green.addEventHandler(ActionEvent.ACTION, this::setColor);
        blue.addEventHandler(ActionEvent.ACTION, this::setColor);
        black.addEventHandler(ActionEvent.ACTION, this::setColor);
        white.addEventHandler(ActionEvent.ACTION, this::setColor);
        color.getItems().addAll(red, green, blue, black, white);
        MenuBar menuBar = new MenuBar(edit, color, pen);
        borderPane.setTop(menuBar);

        penSize = 30;
        gc = layer.getGraphicsContext2D();
        gc.setFill(Color.RED);

        layer.addEventHandler(MouseEvent.MOUSE_PRESSED, this::startDraw);
        layer.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::dragDraw);
        layer.addEventHandler(MouseEvent.MOUSE_RELEASED, this::endDraw);


        root.getChildren().add(borderPane);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void drawOval(MouseEvent event) {
        gc.fillOval(event.getX()-(penSize/2), event.getY()-(penSize/2), penSize, penSize);
    }

    private void startDraw(MouseEvent event) {
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);
        snapshotReader = layer.snapshot(sp, null).getPixelReader();
        current = new Point((int)event.getX(), (int)event.getY());
        upperLeft = new Point(current.getX() - penSize < 0 ? 0 : (int)current.getX() - penSize, current.getY() - penSize < 0 ? 0 : (int)current.getY() - penSize);
        lowerRight = new Point(current.getX() + penSize > layer.getWidth() ? (int) layer.getWidth() : (int)current.getX() + penSize, current.getY() + penSize > layer.getHeight() ? (int) layer.getHeight() : (int)current.getY() + penSize);
        drawOval(event);
    }

    private void dragDraw(MouseEvent event) {
        current = new Point((int)event.getX(), (int)event.getY());
        if(current.getX() - penSize < upperLeft.getX()) {
            if(current.getX() - penSize < 0) {
                upperLeft.setLocation(0, upperLeft.getY());
            }else {
                upperLeft.setLocation(current.getX() - penSize, upperLeft.getY());
            }
        } else if(current.getX() + penSize > lowerRight.getX()) {
            if(current.getX() + penSize > layer.getWidth()) {
                lowerRight.setLocation(layer.getWidth(), lowerRight.getY());
            }else {
                lowerRight.setLocation(current.getX() + penSize, lowerRight.getY());
            }
        }
        if(current.getY() - penSize < upperLeft.getY()) {
            if(current.getY() - penSize < 0) {
                upperLeft.setLocation(upperLeft.getX(), 0);
            }else {
                upperLeft.setLocation(upperLeft.getX(), current.getY() - penSize);
            }
        }else if(current.getY() + penSize > lowerRight.getY()) {
            if(current.getY() + penSize > layer.getHeight()) {
                lowerRight.setLocation(lowerRight.getX(), layer.getHeight());
            }else {
                lowerRight.setLocation(lowerRight.getX(), current.getY() + penSize);
            }
        }

        drawOval(event);
    }

    private void endDraw(MouseEvent event) {
        dragDraw(event);
        invoker.invoke(new DrawCommand(upperLeft, lowerRight, layer, snapshotReader));

    }

    private void undo(ActionEvent event) {
        invoker.undo();
    }

    private void redo(ActionEvent event) {
        invoker.redo();
    }

    private void setColor(ActionEvent event) {
        Object temp = event.getSource();
        if(temp instanceof MenuItem) {
            switch(((MenuItem) temp).getId()) {
                case "red":
                    gc.setFill(Color.RED);
                    break;
                case "green":
                    gc.setFill(Color.GREEN);
                    break;
                case "blue":
                    gc.setFill(Color.BLUE);
                    break;
                case "black":
                    gc.setFill(Color.BLACK);
                    break;
                case "white":
                    gc.setFill(Color.WHITE);
                    break;
            }
        }
    }

    private void setPenSize(ActionEvent event) {
        Object temp = event.getSource();
        if(temp instanceof MenuItem) {
            switch(((MenuItem) temp).getId()) {
                case "6":
                    penSize = 6;
                    break;
                case "12":
                    penSize = 12;
                    break;
                case "18":
                    penSize = 18;
                    break;
                case "24":
                    penSize = 24;
                    break;
                case "30":
                    penSize = 30;
                    break;
                case "60":
                    penSize = 60;
                    break;
            }
        }
    }


}
