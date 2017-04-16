package mapeditor;

import javafx.scene.control.ListView;
import mapeditor.layerlist.LayerListCell;

public class MapFile {
    private String mapName;
    private int width;
    private int height;
    private ListView<LayerListCell> layerList;

    public MapFile(String mapName, int width, int height) {
        this.mapName = mapName;
        this.width = width;
        this.height = height;
        layerList = new ListView<>();
    }


    public ListView<LayerListCell> getLayerList() {
        return layerList;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getMapName() {
        return mapName;
    }



}