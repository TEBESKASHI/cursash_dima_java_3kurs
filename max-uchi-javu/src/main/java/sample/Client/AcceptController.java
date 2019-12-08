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


public class AcceptController extends Connection{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Next;


    @FXML
    void initialize() {
        Next.setOnAction(event -> {
            Next.getScene().getWindow().hide();
            if (current.equals("root") || current.equals("admin")) newScene("mainAdmin");
            else newScene("sample");
        });

    }

}
