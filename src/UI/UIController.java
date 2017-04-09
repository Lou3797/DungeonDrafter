package UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class UIController {

    public Text testText;
    public Pane mapcontainer;
    public ColorPicker colorPicker;
    private Canvas map;
    public ListView layerslist;
    private DrawPen pen;
    private ArrayList<Canvas> layers;

    public UIController() {
        //Before the FXML is loaded
        pen = new DrawPen();
        layers = new ArrayList<>();
    }

    @FXML
    public void initialize() {
        //After the FXML is loaded
        System.out.println(testText.getText());
        testText.setText("Constructor changed this");
        layerslist.getSelectionModel().select(0);
        Canvas temp = new Canvas(540, 720);
        temp.addEventHandler(MouseEvent.MOUSE_CLICKED, this::drawShape);
        layers.add(temp);
        //mapcontainer.getChildren().add(layers.get(0));

        //map = new Canvas(540, 720);
        mapcontainer.getChildren().add(temp);
        map = temp;
        map.getGraphicsContext2D().setFill(Color.BLACK);
        map.toFront();
        map.addEventHandler(MouseEvent.MOUSE_CLICKED, this::drawShape);
    }



    @FXML
    protected void changeTexture(ActionEvent event) {
        GraphicsContext gc = map.getGraphicsContext2D();
        gc.setGlobalBlendMode(BlendMode.OVERLAY);
    }

    @FXML
    private void drawShape(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)) {
            pen.addPoint(event.getX(), event.getY());
            if(pen.getSize() > 1) {
                map.getGraphicsContext2D().strokeLine(pen.getxPoints()[pen.getSize()-2], pen.getyPoints()[pen.getSize()-2],
                        pen.getxPoints()[pen.getSize()-1], pen.getyPoints()[pen.getSize()-1]);
            } else if(pen.getSize() == 1) {
                map.getGraphicsContext2D().strokeLine(pen.getxPoints()[pen.getSize()-1], pen.getyPoints()[pen.getSize()-1],
                        pen.getxPoints()[pen.getSize()-1], pen.getyPoints()[pen.getSize()-1]);
            }
            if(event.isShiftDown()) {
                map.getGraphicsContext2D().fillPolygon(pen.getxPoints(), pen.getyPoints(), pen.getSize());
                pen.clear();
            }
        }
        else if(!event.isShiftDown() && event.getButton().equals(MouseButton.SECONDARY)) {
            System.out.println("Clicked " + event.getX() + ", " + event.getY());
        } else if(event.isShiftDown() && event.getButton().equals(MouseButton.SECONDARY)) {
            System.out.println("Clearing");
            map.getGraphicsContext2D().clearRect(0, 0, map.getWidth(), map.getHeight());
            pen.clear();
        }

    }

    public void changeLayer(MouseEvent mouseEvent) {
        System.out.println(layerslist.getSelectionModel().getSelectedIndex());
        map = layers.get(layerslist.getSelectionModel().getSelectedIndex());
        //map.addEventHandler(MouseEvent.MOUSE_CLICKED, this::drawShape);
    }

    public void addLayer(ActionEvent actionEvent) {
        String newLayer = "Layer " + (layers.size()+1);
        layerslist.getItems().add(newLayer);
        Canvas newCanvas = new Canvas(540, 720);
        newCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, this::drawShape);
        //newCanvas.getGraphicsContext2D().setFill(Color.BLACK);
        layers.add(newCanvas);
        mapcontainer.getChildren().add(layers.get(layers.size()-1));
    }

    public void changeColor(ActionEvent actionEvent) {
        map.getGraphicsContext2D().setFill(colorPicker.getValue());
        map.getGraphicsContext2D().setStroke(colorPicker.getValue());
    }
}