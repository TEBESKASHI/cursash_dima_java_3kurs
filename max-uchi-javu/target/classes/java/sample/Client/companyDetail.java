package sample.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class companyDetail extends Connection{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back;

    @FXML
    private Button editCompany;

    @FXML
    private Button exit;

    @FXML
    private Button deleteCompany;

    @FXML
    private Button addCompany;


    @FXML
    void initialize() {
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            if (current.equals("root") || current.equals("admin")) newScene("mainAdmin");
            else newScene("main");
        });
        addCompany.setOnAction(event -> {
            addCompany.getScene().getWindow().hide();
            newScene("addCompany");
        });
        deleteCompany.setOnAction(event -> {
            deleteCompany.getScene().getWindow().hide();
            newScene("deleteCompany");
        });
        editCompany.setOnAction(event -> {
            editCompany.getScene().getWindow().hide();
            newScene("editCompany");
        });
        exit.setOnAction(event -> {
            exit.getScene().getWindow().hide();
            newScene("sample");
        });
    }

}
