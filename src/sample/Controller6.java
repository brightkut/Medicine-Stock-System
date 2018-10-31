package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller6 {

    private SqlConnection sqlConnection;

    @FXML
    private TableView<SaleList> tableSale;
    @FXML
    private TableColumn<SaleList,String> columnsaleid;
    @FXML
    private TableColumn<SaleList,String> columnName;
    @FXML
    private TableColumn<SaleList,String> columnNumberlotte;
    @FXML
    private TableColumn<SaleList,String> columnqualitysale;
    @FXML
    private TableColumn<SaleList,String> columndate;

    private ObservableList<SaleList> data;


    public void initialize(){
        sqlConnection = new SqlConnection();
        try {
            Connection conn = sqlConnection.DbConnector();
            data = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM SaleOrder");

            while (rs.next()){
                data.add(new SaleList(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }
            conn.close();


        }catch (SQLException ex){
            System.err.println("Error"+ ex);
        }
        columnsaleid.setCellValueFactory(new PropertyValueFactory<>("saleid"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnNumberlotte.setCellValueFactory(new PropertyValueFactory<>("numberlotte"));
        columnqualitysale.setCellValueFactory(new PropertyValueFactory<>("qualitysale"));
        columndate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableSale.setItems(data);
    }

    @FXML
    public void backToHome(ActionEvent event){
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        try {
            stage.setScene(new Scene(loader.load(), 571, 542));
            stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
