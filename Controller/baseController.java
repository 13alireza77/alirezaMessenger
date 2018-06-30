package Controller;

import common.user;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sample.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

public class baseController implements Initializable {
    @FXML
    private AnchorPane welcome;
    @FXML
    private VBox userList;
    @FXML
    private AnchorPane base;
    @FXML
    private Button all;
    @FXML
    private Button group;
    @FXML
    private Button person;
    @FXML
    private TextField searchText;

    private static common.user user;

    public static common.user getUser() {
        return user;
    }

    private static Stage setStage;

    private makeButton[] buttons;

    public void setting(ActionEvent actionEvent) throws Exception {
        FXMLLoader fxmlLoader = new
                FXMLLoader(getClass().getResource("../fxmls/setting.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        setStage = stage;
        stage.show();
    }

    public void search(ActionEvent actionEvent) throws Exception {
        searchText.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                searchText.clear();
            }
        });

        String s = searchText.getText();
        userList.getChildren().clear();
        for (makeButton b : buttons) {
            if (b.getRuz().getUserName().contains(s)) {
                userList.getChildren().add(b.getFinalButton());
                FadeTransition fadeTransition = new FadeTransition(new Duration(1000), b.getFinalButton());
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(300);
                fadeTransition.play();
            }
        }

    }

    public void all(ActionEvent actionEvent) {
        userList.getChildren().clear();
        for (makeButton b : buttons) {
            userList.getChildren().add(b.getFinalButton());
            FadeTransition fadeTransition = new FadeTransition(new Duration(1000), b.getFinalButton());
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(300);
            fadeTransition.play();
        }
    }

    public static Stage getSetStage() {
        return setStage;
    }

    public VBox getuserBox() {
        return userList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeTransition = new FadeTransition(new Duration(100000), welcome);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(300);
        fadeTransition.play();
        FadeTransition fadeTransition1 = new FadeTransition(new Duration(4000), all);
        fadeTransition1.setFromValue(0);
        fadeTransition1.setToValue(300);
        fadeTransition1.play();
        FadeTransition fadeTransition2 = new FadeTransition(new Duration(3000), group);
        fadeTransition2.setFromValue(0);
        fadeTransition2.setToValue(300);
        fadeTransition2.play();
        FadeTransition fadeTransition3 = new FadeTransition(new Duration(2000), person);
        fadeTransition3.setFromValue(0);
        fadeTransition3.setToValue(300);
        fadeTransition3.play();
//        try {
//            Parent root = null;
//            root = FXMLLoader.load(getClass().getResource("../fxmls/welcome.fxml"));
//            Stage stage = Main.getprimarystage1();
//            stage.setScene(new Scene(root));
//            stage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        if (loginController.string.equals("login1")) {
            user = signUpController.getThisUser();
        } else {
            user = loginController.getThisUser();
        }
        System.out.println(user.getName());
        List<common.user> list = null;
        try {
            list = user.getUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        userList.getChildren().clear();
        buttons = new makeButton[list.size() - 1];
        int c = 0;
        int t = 500;
        for (common.user u : list) {
            if (!(u.getUserName().equals(user.getUserName()) && u.getPassword().equals(user.getPassword()))) {
                try {
                    javafx.scene.image.Image image = new javafx.scene.image.Image(getClass().getResource("../icons/send.png").toExternalForm());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(35);
                    imageView.setFitHeight(35);
                    Button textButton = new Button("", imageView);
                    ListView listView = new ListView();
                    TextField textField = new TextField();
                    listView.setPrefWidth(449);
                    listView.setPrefHeight(550);
                    listView.setLayoutX(0);
                    listView.setLayoutY(0);
                    listView.setStyle("-fx-background-color: #C0C0C0 ;");
                    textButton.setPrefHeight(49);
                    textButton.setPrefWidth(50);
                    textButton.setLayoutX(399);
                    textButton.setLayoutY(551);
                    textButton.setStyle("-fx-background-color:  #FCFFA4;");
                    textField.setPrefWidth(398);
                    textField.setPrefHeight(49);
                    textField.setLayoutX(0);
                    textField.setLayoutY(551);
                    textField.setStyle("-fx-background-color:  #FCFFA4;");
                    textField.setFont(Font.font("Berlin Sans FB", FontWeight.THIN, 20));
                    buttons[c] = new makeButton(u, user, userList, base, textButton, listView, textField);
                    Button b = buttons[c].getFinalButton();
                    b.setPrefHeight(100);
                    b.setPrefWidth(userList.getPrefWidth());
                    userList.getChildren().add(b);
                    FadeTransition fadeTransition4 = new FadeTransition(new Duration(t), b);
                    fadeTransition4.setFromValue(0);
                    fadeTransition4.setToValue(300);
                    fadeTransition4.play();
                    c++;
                    t += 500;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        user.start();
    }
}
