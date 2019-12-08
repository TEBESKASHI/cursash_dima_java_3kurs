package sample.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import sample.Server.DataObject;


public class diagCircle extends Connection{
    private DataObject d;

    public diagCircle() {
        d = new DataObject();
        d.setCommand("diagCircle");
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
            d = (DataObject) in.readObject();
            closeConnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private PieChart diag;

    @FXML
    private Button back;

    @FXML
    void initialize() {
        System.out.println(d.getCredit());
        System.out.println(d.getPercent());
            diag.getData().add(new PieChart.Data(" ЗК ", Double.valueOf(d.getCredit())));
            diag.getData().add(new PieChart.Data(" СК ", Double.valueOf(d.getPercent())));
            diag.setTitle(" Отношение СК к ЗК ");
            diag.setLegendVisible(true);
            diag.setLegendSide(Side.BOTTOM);

        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            newScene("riskDetail");
        });
    }
}