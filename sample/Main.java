package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private static Stage ps;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ps = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../fxmls/start.fxml"));
        primaryStage.setTitle("   TelePATHY");
        primaryStage.getIcons().add(new Image("/icons/alarm (2).png"));
        primaryStage.setScene(new Scene(root, 700, 600));
        primaryStage.show();
    }

    public static Stage getprimarystage() {
        return ps;
    }

    public static Stage getprimarystage1() {
        ps.initStyle(StageStyle.UNDECORATED);
        return ps;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
