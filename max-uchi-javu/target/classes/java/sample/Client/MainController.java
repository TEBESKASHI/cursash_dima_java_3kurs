package sample.Client;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.Server.DataObject;


public class MainController extends Connection{
    private DataObject d;

    public MainController() {
    }

    @FXML
    private Button exit;

    @FXML
    private Button CompanyDetail;

    @FXML
    private Button risk;

    @FXML
    void initialize() {
//        if (d.getBool()==1 && d.isResult()){
//            diag.getData().add(new PieChart.Data("Сальдо", d.getPer_income()));
//            diag.getData().add(new PieChart.Data("Расходы", d.getPer_con()));
//            diag.setTitle("Отнощение расходов к доходам");
//
//        }else{
//            diag.getData().add(new PieChart.Data("Дефицит бюджета", d.getPer_income()));
//            diag.getData().add(new PieChart.Data("Доходы", d.getPer_con()));
//            diag.setTitle("Отнощение доходов к расходам");
//        }
//        diag.setLegendVisible(true);
//        diag.setLegendSide(Side.BOTTOM);


        exit.setOnAction(event -> {
            exit.getScene().getWindow().hide();
            newScene("sample");
        });
        CompanyDetail.setOnAction(event -> {
            CompanyDetail.getScene().getWindow().hide();
            newScene("companyDetail");
        });
        risk.setOnAction(event -> {
            risk.getScene().getWindow().hide();
            newScene("displayCompanys");
        });
    }
}