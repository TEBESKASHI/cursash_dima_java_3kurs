package sample.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Server.DataObject;
import sample.Server.DatabaseHandler;


public class RegistrationController extends Connection{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button completeButton;

    @FXML
    private TextField signUpName;

    @FXML
    private TextField signUpLastName;

    @FXML
    private CheckBox signUpCheckBoxMale;

    @FXML
    private CheckBox signUpCheckBoxFemale;

    @FXML
    private TextField signUpCountry;

    @FXML
    void initialize() {
        DatabaseHandler db = new DatabaseHandler();

        completeButton.setOnAction(event -> {
            DataObject d = new DataObject();
            int n = signUpName.getText().length();
            int error = 0;
            if (n <= 0) {
                error = 1;
            }
            n = signUpLastName.getText().length();
            if (n <= 0) {
                error = 1;
            }
            n = login_field.getText().length();
            if (n <= 0) {
                error = 1;
            }
            n = password_field.getText().length();
            if (n <= 0) {
                error = 1;
            }
            n = signUpCountry.getText().length();
            if (n <= 0) {
                error = 1;
            }
            if (error == 1) {
                completeButton.getScene().getWindow().hide();
                newScene("error2");
            } else {
                d.setCommand("Reg");
                d.setFirstName(signUpName.getText());
                d.setLastName(signUpLastName.getText());
                d.setLogin(login_field.getText());
                d.setPassword(password_field.getText());
                d.setLocation(signUpCountry.getText());
                if(signUpCheckBoxMale.isSelected()) {
                    d.setGender("Male");
                }
                else {
                    d.setGender("Female");
                }
                current = d.getLogin();
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
                        completeButton.getScene().getWindow().hide();
                        newScene("accept");
                    } else {
                        completeButton.getScene().getWindow().hide();
                        newScene("error2");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    }