package mainui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UI extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("UI.fxml"));
        Scene scene = new Scene(root, 1080, 720);
        primaryStage.setTitle("Dungeon Drafter v0.0.3");
        primaryStage.setScene(scene);
        primaryStage.show();
        UI.primaryStage = primaryStage;
    }
}