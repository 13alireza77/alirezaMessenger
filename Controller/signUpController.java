package Controller;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Delayed;

public class signUpController implements Initializable {
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    private static common.user thisUser = null;

    static String string = "su1";

    public synchronized void signUp(ActionEvent actionEvent) throws Exception {
        connect connect = new connect("localhost", 8080);
        boolean b = false;
        if (userName.getText().isEmpty() || password.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Field");
            alert.setContentText("Enter Youre Username Or Password");
            alert.showAndWait();
        } else {
            b = connect.sendInfo(userName, password);
        }
        if (b) {
            string = "su";
            thisUser = connect.getUser();
            thisUser.setUserName(userName.getText());
            thisUser.setPassword(password.getText());
            Parent root = null;
            root = FXMLLoader.load(getClass().getResource("../fxmls/base.fxml"));
            Stage stage = Main.getprimarystage();
            stage.setScene(new Scene(root));
            stage.setTitle("   TelePATHY");
            loginController.getSignUpprimarystage().close();
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Wrong Input");
            alert.setContentText("Repetitious UserName Or PassWord");
            alert.showAndWait();
        }
    }

    public static common.user getThisUser() {
        return thisUser;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
