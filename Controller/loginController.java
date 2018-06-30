package Controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    private static Stage psSignUp;

    static String string = "login1";

    public void signUp(ActionEvent actionEvent) throws Exception {
        FXMLLoader fxmlLoader = new
                FXMLLoader(getClass().getResource("../fxmls/signUp.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("   TelePATHY/signUp");
        stage.getIcons().add(new Image("/icons/alarm (2).png"));
        stage.setScene(new Scene(root1));
        stage.setResizable(false);
        psSignUp = stage;
        stage.show();
    }

    public static Stage getSignUpprimarystage() {
        return psSignUp;
    }

    private static common.user thisUser = null;

    public void login(ActionEvent actionEvent) throws Exception {
        connect connect = new connect("localhost", 8080);
        boolean b = false;
        if (userName.getText().isEmpty() || password.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Field");
            alert.setContentText("Enter Youre Username Or Password");
            alert.showAndWait();
        } else {
            b = connect.loginInfo(userName, password);
        }
        if (b) {
            string = "login";
            thisUser = connect.getUser();
            thisUser.setUserName(userName.getText());
            thisUser.setPassword(password.getText());
            Parent root = null;
            root = FXMLLoader.load(getClass().getResource("../fxmls/base.fxml"));
            Stage stage = Main.getprimarystage();
            stage.setScene(new Scene(root));
            stage.setTitle("   TelePATHY");
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Wrong Input");
            alert.setContentText("wrong UserName Or PassWord");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static common.user getThisUser() {
        return thisUser;
    }
}
