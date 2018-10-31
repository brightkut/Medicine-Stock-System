package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import java.sql.*;

public class Controller2 {


    @FXML
    TextField productId ;
    @FXML
    TextField productName;

    String id;
    String name;
    private SqlConnection sqlConnection;
    private Connection connection;


    @FXML
    ComboBox<String>comboBox;



    public void initialize(){
        sqlConnection = new SqlConnection();

        comboBox.getItems().addAll("เม็ด","ขวด","กล่อง");





    }


    @FXML
    public void backToHome(ActionEvent event) throws SQLException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        try {
            stage.setScene(new Scene(loader.load(), 571, 542));
            stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
//        connection.close();
    }

    @FXML
    public void add(ActionEvent event) throws SQLException {
        Connection conn = sqlConnection.DbConnector();
        id = productId.getText();
        name = productName.getText();
        int p =0;
        String q1 = "SELECT * FROM Medicine";
        ResultSet rs = conn.createStatement().executeQuery(q1);
        int d =0;
        while (rs.next()){
            if (rs.getString(1).equals(id)&&!rs.getString(2).equals(name)){
                AlertBox.display("Error","Error this product id already use");
                d=1;
                break;

            }else if (rs.getString(1).equals(id)&&rs.getString(2).equals(name)){
                AlertBox.display("Error","Error this product id  and name already use ");
                d=1;
                break;

            }else if (!rs.getString(1).equals(id)&&rs.getString(2).equals(name)){
                AlertBox.display("Error","Error this product name already use");
                d=1;
                break;
            }
        }



        if (d==1&&p==1){


        }

        else if (productId.getText().equals("") || productName.getText().equals("")|| comboBox.getSelectionModel().getSelectedItem()==null) {
            AlertBox.display("Error", "Error some Field is null");

        }else {



            String query2 = "INSERT INTO Medicine (id,name,unitofkeep) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query2);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3,comboBox.getValue());
            ps.executeUpdate();



            productId.clear();
            productName.clear();
            comboBox.setValue(null);


            System.out.println("add complete");
            conn.close();
//            AlertBox.display("Data Already Add", "Add product and Data Insert Succesfully");


        }



    }
}
