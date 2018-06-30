package Controller;

import common.user;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import sample.Main;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class makeButton {
    private Button textButton;
    private ListView listView;
    private TextField textField;
    private common.user suz;
    private common.user ruz;
    private List<common.textMessage> textMessageList;
    private VBox buttonbox;
    private AnchorPane anchorPane;
    private Button finalButton;

    public makeButton(common.user ruz1, common.user suz1, VBox buttonListView1, AnchorPane anchorPane1, Button textButton1, ListView listView1, TextField textField1) throws Exception {
        this.suz = suz1;
        this.ruz = ruz1;
        this.buttonbox = buttonListView1;
        this.anchorPane = anchorPane1;
        this.textField = textField1;
        this.listView = listView1;
        this.textButton = textButton1;
        textMessageList = null;
        textMessageList = suz.getTextMessages(suz.getUserName() + " " + ruz.getUserName());
        listView.getItems().clear();
        try {
            for (common.textMessage t : textMessageList) {
                if (t.getSender().getUserName().equals(suz.getUserName()) && t.getSender().getPassword().equals(suz.getPassword()))
                    listView.getItems().add("+   " + t.getMessage() + "       " + t.getDate());
                else
                    listView.getItems().add("-   " + t.getMessage() + "\n" + t.getDate() + "\n\n\n");
            }

        } catch (NullPointerException n) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        Label label = new Label(ruz.getUserName() + "     ");
        label.setFont(Font.font("Cambria", 32));
        label.setPrefHeight(50);
        label.setPrefWidth(50);
        javafx.scene.image.Image image = new javafx.scene.image.Image(getClass().getResource("../icons/user.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        finalButton = new Button(label.getText(), imageView);
        finalButton.setStyle("-fx-background-color: white;");
        finalButton.setContentDisplay(ContentDisplay.RIGHT);
        finalButton.setOnAction(ActionEvent -> {
            for (Node b1 : buttonListView1.getChildren()) {
                b1.setStyle("-fx-background-color: white;");
            }
            finalButton.setStyle("-fx-background-color: #FFBF00;");
            suz.setMakeButton(this);
            try {
                AnchorPane anchorPane2 = anchorPane;
                anchorPane2.getChildren().removeAll(listView, textButton, textField);
                try {
                    System.out.println(textMessageList.equals(null));
                } catch (NullPointerException n) {
                }
                anchorPane2.getChildren().add(listView);
                anchorPane2.getChildren().add(textButton);
                anchorPane2.getChildren().add(textField);
                Stage stage = Main.getprimarystage();
                stage.setScene(new Scene(anchorPane2));
                stage.show();
            } catch (IllegalArgumentException i) {
            }
        });
        textButton.setOnAction(ActionEvent -> {
            String m1 = textField.getText();
            System.out.println(m1);
            common.textMessage tm = new common.textMessage(m1, suz, ruz);
            listView.getItems().add("+   " + tm.getMessage() + "       " + tm.getDate());
            textField.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    textField.clear();
                }
            });
            try {
                tm.sendTextMessage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Button getFinalButton() {
        return finalButton;
    }

    public ListView getListView() {
        return listView;
    }

    public user getRuz() {
        return ruz;
    }
}

