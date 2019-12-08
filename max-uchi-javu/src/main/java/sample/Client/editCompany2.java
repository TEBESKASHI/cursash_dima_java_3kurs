package sample.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Server.DataObject;

public class editCompany2 extends Connection{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField companyName;

    @FXML
    private TextField companyTag;

    @FXML
    private Button saveEdit;

    @FXML
    private Button back;


    @FXML
    void initialize() {
        companyName.setText(selectionModel.getSelectedItem().getCompanyName());
        companyTag.setText(selectionModel.getSelectedItem().getCompanyTag());
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            if (current.equals("root") || current.equals("admin")) newScene("mainAdmin");
            else newScene("main");
        });
        saveEdit.setOnAction(event -> {
            DataObject d = new DataObject();
            d.setCommand("editCompany");
            d.setCompanyName(companyName.getText());
            d.setCompanyTag(companyTag.getText());
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
            saveEdit.getScene().getWindow().hide();
            newScene("acceptEditCon");
        });
    }
}
