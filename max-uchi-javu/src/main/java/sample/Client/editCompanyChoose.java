package sample.Client;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Server.DataObject;
public class editCompanyChoose extends Connection{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button editCompany;

    @FXML
    private Button back;

    @FXML
    private Button editBorrowedCapital;

    @FXML
    private Button editCapital;


    @FXML
    void initialize() {
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            if (current.equals("root") || current.equals("admin")) newScene("mainAdmin");
            else newScene("main");
        });
        editCompany.setOnAction(event -> {
            editCompany.getScene().getWindow().hide();
            newScene("editCompany2");
        });
        editBorrowedCapital.setOnAction(event -> {
            editBorrowedCapital.getScene().getWindow().hide();
            newScene("editBorrowedCapital");
        });
        editCapital.setOnAction(event -> {
            editCapital.getScene().getWindow().hide();
            newScene("editCapital");
        });
    }
}
