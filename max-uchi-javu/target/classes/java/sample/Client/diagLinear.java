package sample.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import sample.Server.DataObject;


public class diagLinear extends Connection{
    private DataObject d;

    public diagLinear() {
        d = new DataObject();
        d.setCommand("diagLine");
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
    private LineChart<String, Number> diag;

    @FXML
    private Button back;

    @FXML
    void initialize() {
//        XYChart.Series<String,Number> series = new XYChart.Series<String,Number>();
//        series.getData().add(new XYChart.Data<String,Number>("20d", 50d));
//        series.getData().add(new XYChart.Data<String,Number>("20d", 80d));
//        series.getData().add(new XYChart.Data<String,Number>("20d", 90d));
//        series.getData().add(new XYChart.Data<String,Number>("20d", 30d));
//        series.getData().add(new XYChart.Data<String,Number>("20d", 122d));
//        diag.getData().add(series);
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            newScene("riskDetail");
        });
    }
}