package Controller;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class startController implements Initializable {
    @FXML
    Button go;

    public void go(ActionEvent actionEvent) throws Exception {
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("../fxmls/login.fxml"));
        Stage stage = Main.getprimarystage();
        stage.setScene(new Scene(root));
        stage.setTitle("   TelePATHY/login");
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeTransition = new FadeTransition(new Duration(3000), go);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(300);
        fadeTransition.play();
    }
}
