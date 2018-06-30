package Controller;

import common.user;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sample.Main;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class settingController implements Initializable {
    private common.user user;

    @FXML
    private AnchorPane settings;
    @FXML
    private TextField userName;
    @FXML
    private TextField passWord;
    @FXML
    private ImageView profileIm;
    @FXML
    private Label nameLable;

    {
        try {
            ImageView imageView = new ImageView(user.getImage());
            profileIm = imageView;
        } catch (NullPointerException n) {
            ImageView imageView1 = new ImageView(new javafx.scene.image.Image(getClass().getResource("../icons/send.png").toExternalForm()));
            profileIm = imageView1;
        }
    }

    public common.user getUser() {
        return user;
    }

    public void setUn(ActionEvent actionEvent) throws Exception {
        String n = userName.getText();
        user.sendText("C-U-N " + n + " " + user.getUserName() + " " + user.getPassword());
        user.setUserName(n);
        nameLable.setText(n);
    }

    public void setPw(ActionEvent actionEvent) throws Exception {
        String n = passWord.getText();
        user.sendText("C-P-W " + n + " " + user.getUserName() + " " + user.getPassword());
        user.setPassword(n);
    }

    public void setIm(ActionEvent actionEvent) throws Exception {
//        FileChooser fileChooser = new FileChooser();
////        fileChooser.setTitle("Open Resource File");
////        fileChooser.showOpenDialog(Main.getprimarystage());
////        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.JPG),(*.JPG)");
////        FileChooser.ExtensionFilter extFilterjpg = new FileChooser.ExtensionFilter("jpg files (*.jpg),(*.jpg)");
////        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.PNG),(*.PNG)");
////        FileChooser.ExtensionFilter extFilterpng = new FileChooser.ExtensionFilter("png files (*.png),(*.png)");
////        File file = fileChooser.showOpenDialog(null);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("SELECT IMAGE");
        File file = fileChooser.showOpenDialog(Main.getprimarystage());
        if (file != null) {
            profileIm.setImage(new Image(file.getAbsoluteFile().toURI().toString(), 40, 40, true, true));
            user.setImage(new Image(file.getAbsoluteFile().toURI().toString(), 40, 40, true, true));
        }
    }

    public void logOut(ActionEvent actionEvent) throws Exception {
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("../fxmls/login.fxml"));
        Stage stage = Main.getprimarystage();
        stage.setScene(new Scene(root));
        stage.setTitle("   TelePATHY/login");
        stage.show();
        baseController.getSetStage().close();
        user.close();
    }

    public void setting(ActionEvent actionEvent) throws Exception {
        baseController.getSetStage().close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeTransition = new FadeTransition(new Duration(3000), userName);
        FadeTransition fadeTransition1 = new FadeTransition(new Duration(3000), passWord);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(300);
        fadeTransition1.setFromValue(0);
        fadeTransition1.setToValue(300);
        fadeTransition.play();
        fadeTransition1.play();
        user = baseController.getUser();
        nameLable.setText(user.getUserName());
    }

}
