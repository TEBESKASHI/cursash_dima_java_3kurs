package sample.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Server.DataObject;


public class displayCompanys extends Connection{
    private ObservableList<DataObject> list = FXCollections.observableArrayList();
    public displayCompanys(){
        DataObject d = new DataObject();
        d.setId(current_id);
        d.setCommand("getCompany");
        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeObject(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            DataObject arr[] = (DataObject[]) in.readObject();
            for (int i=0; i<arr[arr.length-1].getCounter()-1; i++){
                list.add(arr[i]);
            }
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
    private URL location;

    @FXML
    private Button editCompany;

    @FXML
    private Button back;

    @FXML
    private TableColumn<DataObject, String> lastName;

    @FXML
    private TableView<DataObject> table;

    @FXML
    private TableColumn<DataObject, String> companyName;



    @FXML
    void initialize() {
        lastName.setCellValueFactory(new PropertyValueFactory<DataObject, String>("lastName"));
        companyName.setCellValueFactory(new PropertyValueFactory<DataObject, String>("companyName"));
        table.setItems(list);
        editCompany.setOnAction(event -> {
            try {
                selectionModel = table.getSelectionModel();
                companyId = selectionModel.getSelectedItem().getCompanyId();
                editCompany.getScene().getWindow().hide();
                newScene("riskDetail");
            }catch (NullPointerException e){
                editCompany.getScene().getWindow().hide();
                newScene("error3");
            }
        });
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            if (current.equals("root") || current.equals("admin")) newScene("mainAdmin");
            else newScene("main");
        });
    }

}
