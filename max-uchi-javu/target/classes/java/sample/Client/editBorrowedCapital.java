package sample.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Server.DataObject;

public class editBorrowedCapital extends Connection{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField credit;

    @FXML
    private TextField percent;

    @FXML
    private TextField withoutPercent;

    @FXML
    private Button saveEdit;

    @FXML
    private Button back;


    @FXML
    void initialize() {
        credit.setText(selectionModel.getSelectedItem().getCredit());
        percent.setText(selectionModel.getSelectedItem().getPercent());
        withoutPercent.setText(selectionModel.getSelectedItem().getWithoutPercent());
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            if (current.equals("root") || current.equals("admin")) newScene("mainAdmin");
            else newScene("main");
        });
        saveEdit.setOnAction(event -> {
            DataObject d = new DataObject();
            d.setCommand("editBorrowedCapital");
            d.setPercent(percent.getText());
            d.setCredit(credit.getText());
            d.setWithoutPercent(withoutPercent.getText());
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
