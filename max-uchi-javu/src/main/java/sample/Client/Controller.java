package sample.Client;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Server.DataObject;



public class Controller extends Connection{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button authSignInButton;

    @FXML
    void initialize() {
        loginSignUpButton.setOnAction(event -> {
            loginSignUpButton.getScene().getWindow().hide();
            newScene("signUp");
        });
        authSignInButton.setOnAction(event -> {
            DataObject d = new DataObject();
            d.setCommand("Enter");
            d.setLogin(login_field.getText());
            d.setPassword(password_field.getText());
            current = login_field.getText();
            if (socket == null || socket.isClosed()) {
                connect();
            }
            try {
                out.writeObject(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                d = (DataObject) in.readObject();
                if (d.isResult()) {
                    closeConnect();
                    current_id = d.getId();
                    login_field.getScene().getWindow().hide();
                    if (d.getLogin().equals("admin") || d.getLogin().equals("root")) newScene("acceptAdmin");
                    else newScene("accept2");
                } else {
                    login_field.getScene().getWindow().hide();
                    System.out.println(current);
                    newScene("error1");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }


}