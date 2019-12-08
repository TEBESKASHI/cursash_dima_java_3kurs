package sample.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import sample.Server.DataObject;

import java.io.IOException;

public class riskGetInfo extends Connection{

    private DataObject d;

    @FXML
    private Button back;

    @FXML
    private Text companyName;

    @FXML
    private Text level;

    @FXML
    private Text risk;

    @FXML
    void initialize() {
        DataObject d = new DataObject();
        d.setCommand("riskGetInfo");
        d.setCompanyId(companyId);
        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeObject(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            d  = (DataObject) in.readObject();
            companyName.setText(d.getCompanyName() );
            risk.setText(d.getLevel() );
            level.setText(d.getMultiply() );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        closeConnect();
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            newScene("sample");
        });
    }
}
