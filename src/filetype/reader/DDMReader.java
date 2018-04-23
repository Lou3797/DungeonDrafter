package filetype.reader;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import map.Map;
import map.layer.Layer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DDMReader {
    private Map map;

    public DDMReader(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        Dimension2D dimensions = new Dimension2D(scanner.nextInt(), scanner.nextInt());
        int gridSize = scanner.nextInt();
        String mapName = scanner.nextLine();
        Map map = new Map((int)dimensions.getWidth(), (int)dimensions.getHeight(), gridSize, mapName);

        Layer layer = createLayer(map, scanner);

        List<Layer> temp = new ArrayList<>();
        temp.add(layer);

        map.importLayers(temp);
        this.map = map;
    }

    private Layer createLayer(Map map, Scanner scanner) {
        Layer temp = new Layer(map);
        GraphicsContext gc = temp.getGraphicsContext2D();
        while(scanner.hasNextLine()) {
            gc.setFill(Color.BLACK);
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int len = scanner.nextInt();
            System.out.println(x + " " + y + " " + len);
            drawLine(x, y, len, gc);
        }

        return temp;
    }

    private void drawLine(int xo, int y, int len, GraphicsContext gc) {
        for(int x = xo; x < xo+len; x++) {
            gc.getPixelWriter().setColor(x, y, Color.BLACK);
            //gc.fillRect(0, 0, 50, 50);
            System.out.println("Pixel at " + x + ", " + y + " set to BLACK");
        }
    }

    public Map getMap() {
        return this.map;
    }

}
