package sample.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Server.DataObject;

public class editCapital extends Connection{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField availableSum;

    @FXML
    private TextField gratInvest;

    @FXML
    private Button saveEdit;

    @FXML
    private Button back;


    @FXML
    void initialize() {
        availableSum.setText(selectionModel.getSelectedItem().getAvailableSum());
        gratInvest.setText(selectionModel.getSelectedItem().getGratInvest());
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            if (current.equals("root") || current.equals("admin")) newScene("mainAdmin");
            else newScene("main");
        });
        saveEdit.setOnAction(event -> {
            DataObject d = new DataObject();
            d.setCommand("editCapital");
            d.setAvailableSum(availableSum.getText());
            d.setGratInvest(gratInvest.getText());
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
