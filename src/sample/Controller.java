package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;

public class Controller {
    private SqlConnection sqlConnection;
    @FXML
    private TableView <Medicine> tableMedicine;
    @FXML
    private TableColumn<Medicine,String> columnId;
    @FXML
    private TableColumn<Medicine,String> columnName;
    @FXML
    private TableColumn<Medicine,String> columnLotte;
    @FXML
    private TableColumn<Medicine,String> columnqualityofkeep;
    @FXML
    private TableColumn<Medicine,String> columnunitofkeep;

    @FXML
    private TextField search;


    ObservableList<Medicine>  data ;
    FilteredList filter ;





    public void initialize(){
        sqlConnection = new SqlConnection();
        try {
            Connection conn = sqlConnection.DbConnector();
            data = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Medicine");

            while (rs.next()){
                data.add(new Medicine(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }
            conn.close();


        }catch (SQLException ex){
            System.err.println("Error"+ ex);
        }
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnLotte.setCellValueFactory(new PropertyValueFactory<>("qualitylotte"));
        columnqualityofkeep.setCellValueFactory(new PropertyValueFactory<>("qualityofkeep"));
        columnunitofkeep.setCellValueFactory(new PropertyValueFactory<>("unitofkeep"));
        tableMedicine.setItems(data);
        filter = new FilteredList(data,e->true);












    }

    @FXML
    private void search(KeyEvent event){
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate((Predicate<? super Medicine>) (Medicine med) -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;

                } else if (med.getName().contains(newValue)) {
                    return true;

                }
                return false;

            });
        });

        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tableMedicine.comparatorProperty());
        tableMedicine.setItems(sort);

    }


    @FXML
    public void createMedicine(ActionEvent event){
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample2.fxml"));
        try {
            stage.setScene(new Scene(loader.load(), 571, 542));
            stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    @FXML
    public void addMedicine(ActionEvent event){
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample3.fxml"));
        try {
            stage.setScene(new Scene(loader.load(), 571, 542));
            stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
    @FXML
    public void LotteDetail(ActionEvent event){
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample5.fxml"));
        try {
            stage.setScene(new Scene(loader.load(), 571, 542));
            stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }


    @FXML
    public void SaleDetail(ActionEvent event){
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample6.fxml"));
        try {
            stage.setScene(new Scene(loader.load(), 571, 542));
            stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
    @FXML
    public void deleteMedicine(ActionEvent event){
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample4.fxml"));
        try {
            stage.setScene(new Scene(loader.load(), 571, 542));
            stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
