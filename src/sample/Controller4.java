package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Executable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class Controller4 {
    private SqlConnection sqlConnection;
    @FXML
    ListView<String> listView;
    @FXML
    TextField saleid;
    @FXML
    TextField  qualityofsale;

    @FXML
    DatePicker date2;


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
    public void initialize() throws SQLException {
        sqlConnection = new SqlConnection();



        Connection conn = sqlConnection.DbConnector();
        ResultSet rs = conn.createStatement().executeQuery("SELECT name FROM Medicine");
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        while (rs.next()){
            listView.getItems().add(rs.getString(1));



        }
        conn.close();


    }

    @FXML
    public void deleteProduct(ActionEvent event) throws SQLException {
        Connection conn = sqlConnection.DbConnector();
        String que = "SELECT qualityofkeep,name FROM Medicine";
        ResultSet r = conn.createStatement().executeQuery(que);
        int num = 0;


        LocalDate ld2 = date2.getValue();
        String dateUse2 = ld2.toString();

        
        while (r.next()) {
            if (r.getString(2).equals(listView.getSelectionModel().getSelectedItem())) {
                num += r.getInt(1);
            }
        }
        r.close();
        System.out.println(num);

        if (dateUse2.equals("") || qualityofsale.getText().equals("") || listView.getSelectionModel().getSelectedItem() == null || saleid.getText().equals("")) {
            AlertBox.display("Error", "Error some field is null");
        }

        else {
            int f = 0;
            try {
                int q = Integer.parseInt(qualityofsale.getText());
                if (q<=0){
                    f=3;
                }
                else if (q>num){
                    f=4;
                }

            } catch (Exception e) {
                f = 1;
            }

            if (f == 1) {
                AlertBox.display("Error", "Error quality field is not Integer");


            } else if (f==3){
                AlertBox.display("Error","Error nuber is lower or equal 0");

            }
            else  if (f==4) {
                AlertBox.display("Error", "Error quality sale is more than keep");
            }

            else {

                String queryCheck = "SELECT * FROM Lotte";
                ResultSet rs = conn.createStatement().executeQuery(queryCheck);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm withdraw medicine");
                alert.setContentText("Are you sure to withdraw ?");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {

                    int a = 0;
                    int numberlotte = 0;
                    int ql = 0;
                    int nl = 0;
                    int t = Integer.parseInt(qualityofsale.getText());

                    while (rs.next()) {
                        if (rs.getString(3).equals(listView.getSelectionModel().getSelectedItem()) && t == rs.getInt(4)) {
                            a = 0;
                            numberlotte = rs.getInt(2);
                            break;
                        } else if (rs.getString(3).equals(listView.getSelectionModel().getSelectedItem()) && t < rs.getInt(4)) {
                            a = 1;
                            numberlotte = rs.getInt(2);
                            ql = rs.getInt(4);
                            System.out.println("3");
                            break;
                        } else if (rs.getString(3).equals(listView.getSelectionModel().getSelectedItem()) && t > rs.getInt(4)) {

                            numberlotte = rs.getInt(2);
                            nl = rs.getInt(4);
                            String query2 = "DELETE FROM Lotte WHERE id = ? AND numberlotte = ? ";

                            PreparedStatement ps = conn.prepareStatement(query2);
                            ps.setString(1, listView.getSelectionModel().getSelectedItem());
                            ps.setInt(2, numberlotte);
                            ps.executeUpdate();
                            ps.close();

                            String qu = "INSERT INTO SaleOrder (saleid,name,numberlotte,qualitysale,date) VALUES (?,?,?,?,?)";
                            PreparedStatement ps3 = conn.prepareStatement(qu);
                            ps3.setString(1, saleid.getText());
                            ps3.setString(2, listView.getSelectionModel().getSelectedItem());
                            ps3.setInt(3, numberlotte);
                            ps3.setInt(4, nl);
                            ps3.setString(5,dateUse2);
                            ps3.executeUpdate();
                            ps3.close();


                            String query = "SELECT name,qualitylotte,qualityofkeep From Medicine";
                            int n = 0;
                            int m = 0;
                            r = conn.createStatement().executeQuery(query);

                            while (r.next()) {
                                if (r.getString(1).equals(listView.getSelectionModel().getSelectedItem())) {
                                    n = r.getInt(2);
                                    m = r.getInt(3);

                                }

                            }


                            String query3 = "UPDATE Medicine SET qualitylotte = ?,qualityofkeep = ? WHERE name = ?";
                            PreparedStatement ps2 = conn.prepareStatement(query3);
                            ps2.setInt(1, n - 1);
                            ps2.setInt(2, m - nl);

                            ps2.setString(3, listView.getSelectionModel().getSelectedItem());
                            ps2.executeUpdate();
                            ps2.close();
                            System.out.println("p");
                            r.close();
                            t -= nl;

                        }


                    }

                    if (a == 0) {
                        String query2 = "DELETE FROM Lotte WHERE id = ? AND numberlotte = ? ";

                        PreparedStatement ps = conn.prepareStatement(query2);
                        ps.setString(1, listView.getSelectionModel().getSelectedItem());
                        ps.setInt(2, numberlotte);
                        ps.executeUpdate();
                        ps.close();

                        String qu = "INSERT INTO SaleOrder (saleid,name,numberlotte,qualitysale,date) VALUES (?,?,?,?,?)";
                        PreparedStatement ps3 = conn.prepareStatement(qu);
                        ps3.setString(1, saleid.getText());
                        ps3.setString(2, listView.getSelectionModel().getSelectedItem());
                        ps3.setInt(3, numberlotte);
                        ps3.setInt(4, Integer.parseInt(qualityofsale.getText()));
                        ps3.setString(5,dateUse2);
                        ps3.executeUpdate();
                        ps3.close();


                        String query = "SELECT name,qualitylotte,qualityofkeep From Medicine";
                        int n = 0;
                        int m = 0;
                        rs = conn.createStatement().executeQuery(query);

                        while (rs.next()) {
                            if (rs.getString(1).equals(listView.getSelectionModel().getSelectedItem())) {
                                n = rs.getInt(2);
                                m = rs.getInt(3);

                            }

                        }
                        System.out.println(n);
                        System.out.println(m);


                        String query3 = "UPDATE Medicine SET qualitylotte = ?,qualityofkeep = ? WHERE name = ?";
                        PreparedStatement ps2 = conn.prepareStatement(query3);
                        ps2.setInt(1, n - 1);
                        ps2.setInt(2, m - t);
                        System.out.println(m - 1);
                        ps2.setString(3, listView.getSelectionModel().getSelectedItem());
                        ps2.executeUpdate();
                        ps2.close();
                        System.out.println("p");
                        rs.close();


                    } else if (a == 1) {

                        String query = "UPDATE Lotte SET qualityofsend = ? WHERE id = ? AND numberlotte = ?";
                        PreparedStatement ps2 = conn.prepareStatement(query);
                        ps2.setInt(1, ql - t);
                        ps2.setString(2, listView.getSelectionModel().getSelectedItem());
                        ps2.setInt(3, numberlotte);
                        ps2.executeUpdate();
                        ps2.close();

                        String qu = "INSERT INTO SaleOrder (saleid,name,numberlotte,qualitysale,date) VALUES (?,?,?,?,?)";
                        PreparedStatement ps3 = conn.prepareStatement(qu);
                        ps3.setString(1, saleid.getText());
                        ps3.setString(2, listView.getSelectionModel().getSelectedItem());
                        ps3.setInt(3, numberlotte);
                        ps3.setInt(4, t);
                        ps3.setString(5,dateUse2);
                        ps3.executeUpdate();
                        ps3.close();


                        String query3 = "SELECT name,qualitylotte,qualityofkeep From Medicine";

                        int m = 0;
                        rs = conn.createStatement().executeQuery(query3);

                        while (rs.next()) {
                            if (rs.getString(1).equals(listView.getSelectionModel().getSelectedItem())) {

                                m = rs.getInt(3);

                            }

                        }
                        rs.close();


                        String query2 = "UPDATE Medicine SET qualityofkeep = ? WHERE name = ?";
                        PreparedStatement p = conn.prepareStatement(query2);
                        p.setInt(1, m - t);

                        p.setString(2, listView.getSelectionModel().getSelectedItem());
                        p.executeUpdate();
                        p.close();
                    }

//                }else if (a==2){
//                    String query2 = "DELETE FROM PurchaseOrder WHERE id = ? AND numberlotte = ? ";
//
//                    PreparedStatement ps = conn.prepareStatement(query2);
//                    ps.setString(1, listView.getSelectionModel().getSelectedItem());
//                    ps.setInt(2, numberlotte);
//                    ps.executeUpdate();
//                    ps.close();
//
//                    String query = "SELECT id,qualitylotte,qualityofkeep From Medicine";
//                    int n = 0;
//                    int m = 0;
//                    rs = conn.createStatement().executeQuery(query);
//
//                    while (rs.next()) {
//                        if (rs.getString(1).equals(listView.getSelectionModel().getSelectedItem())) {
//                            n = rs.getInt(2);
//                            m = rs.getInt(3);
//
//                        }
//
//                    }
//                    System.out.println(n);
//                    System.out.println(m);
//
//
//                    String query3 = "UPDATE Medicine SET qualitylotte = ?,qualityofkeep = ? WHERE id = ?";
//                    PreparedStatement ps2 = conn.prepareStatement(query3);
//                    ps2.setInt(1, n - 1);
//                    ps2.setInt(2, m - nl);
//
//                    ps2.setString(3, listView.getSelectionModel().getSelectedItem());
//                    ps2.executeUpdate();
//                    ps2.close();
//                    System.out.println("p");
//                    rs.close();
//
//
//                }

                    conn.close();
//                AlertBox.display("Data Already Delete", "Delete Product From Stock Succesfully");

                    qualityofsale.clear();
                  date2.getEditor().clear();
                  saleid.clear();

                }
            }



        }
    }
}
