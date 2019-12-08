package sample.Client;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Server.DataObject;

public class addBorrowedCapital extends Connection{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField percent;

    @FXML
    private TextField withoutPercent;

    @FXML
    private TextField creditSum;


    @FXML
    private Button save;


    @FXML
    void initialize() {
        save.setOnAction(event -> {
            DataObject d = new DataObject();
            d.setCommand("addBorrowedCapital");
            d.setPercent(percent.getText());
            d.setWithoutPercent(withoutPercent.getText());
            d.setCredit(creditSum.getText());
            d.setCompanyId(companyId);
            if (socket == null || socket.isClosed()) {
                connect();
            }
            try {
                out.writeObject(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
            closeConnect();
            save.getScene().getWindow().hide();
            newScene("newCompanyAdded");
        });
    }

}
