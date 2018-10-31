package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Controller3 {

    private SqlConnection sqlConnection;
    @FXML
    ListView<String> listView;

    @FXML
    TextField purchaseId ;
    @FXML
    TextField proudctId ;
    @FXML
    TextField qualityofsend ;
    @FXML
    TextField nameofsupplier ;
    ObservableList<String> id;
    @FXML
    DatePicker date;



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

    @FXML
    public void addMedicine(ActionEvent event) throws SQLException, ParseException {
        Connection conn = sqlConnection.DbConnector();
        String querytest = "SELECT * FROM Medicine";
        ResultSet rs3 = conn.createStatement().executeQuery(querytest);
        int qualitylotte=0;
        int qualitykeep = 0;
        int f =0;



        LocalDate ld = date.getValue();
        String dateUse = ld.toString();
        System.out.println(dateUse);



        while (rs3.next()){
            if (rs3.getString(2).equals(listView.getSelectionModel().getSelectedItem())){
                qualitylotte = rs3.getInt(3);
                qualitykeep = rs3.getInt(4);

            }
        }
        System.out.println("abc");

        if (qualityofsend.getText().equals("")|| listView.getSelectionModel().getSelectedItem()==null|| dateUse.equals("")|| purchaseId.equals("")){
            AlertBox.display("Error","Error some field is null");

        }
        else {

            try {
                int q = Integer.parseInt(qualityofsend.getText());
                if (q <= 0) {
                    f = 3;
                } else if (q > 10000) {
                    f = 4;
                }
            } catch (Exception e) {
                f = 1;
            }
            if (f == 1) {
                AlertBox.display("Error", "Error quality is not Integer");
            } else if (f == 3) {
                AlertBox.display("Error", "Error quality is less than 0 or equal");
            } else if (f == 4) {
                AlertBox.display("Error", "Error quality is more than 10000");
            } else {

                System.out.println("bb");

                   String queryCheck = "SELECT * FROM Lotte WHERE id = '" + listView.getSelectionModel().getSelectedItem() +"'";
                    ResultSet rs = conn.createStatement().executeQuery(queryCheck);
                    int cPur = 0;
                    int cName = 0;
                    int check = 0;
                    int numberlotte2 = 0;
                    int qualitybefore = 0;



                    while (rs.next()) {
                        System.out.println("22");
                        if (rs.getString(1).equals(purchaseId.getText()) && rs.getString(3).equals(listView.getSelectionModel().getSelectedItem())) {
                            check = 1;
                            numberlotte2 = rs.getInt(2);
                            qualitybefore = rs.getInt(4);
                            break;
                        }
                        else

                            if (!rs.getString(1).equals(purchaseId.getText())&&rs.getString(3).equals(listView.getSelectionModel().getSelectedItem())){
                            check =2 ;
                            numberlotte2 = rs.getInt(2);
                        }


                        System.out.println(rs.getString(1)+rs.getInt(2)+rs.getString(3)+rs.getInt(4));
                    }

                    if (check==0){

                        String query2 = "INSERT INTO Lotte (purchaseid,numberlotte,id,qualityofsend,Date) VALUES (?,?,?,?,?)";

                PreparedStatement ps = conn.prepareStatement(query2);
                ps.setString(1, purchaseId.getText());
                ps.setInt(2, 1);
                ps.setString(3, listView.getSelectionModel().getSelectedItem());
                ps.setInt(4, Integer.parseInt(qualityofsend.getText()));
                ps.setString(5,dateUse);
                ps.executeUpdate();
                ps.close();

                String query3 = "UPDATE Medicine SET qualitylotte = ?,qualityofkeep = ?  WHERE name = ?";
                PreparedStatement ps2 = conn.prepareStatement(query3);
                ps2.setInt(1,  1);
                ps2.setInt(2,Integer.parseInt(qualityofsend.getText()));
                ps2.setString(3, listView.getSelectionModel().getSelectedItem());
                ps2.executeUpdate();
                ps2.close();

                    }
                    else if (check==1){
//                        String query2 = "UPDATE Lotte SET qualityofsend = ? WHERE purchaseid = ? AND id = ? AND numberlotte = ? ";
//                PreparedStatement ps = conn.prepareStatement(query2);
//                ps.setInt(1, qualitybefore+Integer.parseInt(qualityofsend.getText()));
//                ps.setString(2, purchaseId.getText());
//                ps.setString(3, listView.getSelectionModel().getSelectedItem());
//                ps.setInt(4,numberlotte2);
//                ps.executeUpdate();
//                ps.close();
//
//                        String query3 = "UPDATE Medicine SET qualityofkeep = ? WHERE name = ?";
//                PreparedStatement ps2 = conn.prepareStatement(query3);
//
//                ps2.setInt(1,qualitykeep+Integer.parseInt(qualityofsend.getText()));
//                ps2.setString(2, listView.getSelectionModel().getSelectedItem());
//                ps2.executeUpdate();
//                ps2.close();
                        AlertBox.display("Error","This PO is already use");
                    }
                    else if (check ==2){
                        System.out.println("go");
                        String query2 = "INSERT INTO Lotte (purchaseid,numberlotte,id,qualityofsend,Date) VALUES (?,?,?,?,?)";
                        PreparedStatement ps = conn.prepareStatement(query2);
                        ps.setString(1, purchaseId.getText());
                        ps.setInt(2, numberlotte2+1);
                        ps.setString(3, listView.getSelectionModel().getSelectedItem());
                        ps.setInt(4, Integer.parseInt(qualityofsend.getText()));
                        ps.setString(5,dateUse);
                        ps.executeUpdate();
                        ps.close();

                        String query3 = "UPDATE Medicine SET qualitylotte = ?,qualityofkeep = ? WHERE name = ?";
                        PreparedStatement ps2 = conn.prepareStatement(query3);
                        ps2.setInt(1,qualitylotte+1);
                        ps2.setInt(2,qualitykeep+Integer.parseInt(qualityofsend.getText()));
                        ps2.setString(3, listView.getSelectionModel().getSelectedItem());
                        ps2.executeUpdate();
                        ps2.close();

                    }
                    conn.close();


                    qualityofsend.clear();
                    date.getEditor().clear();
                    purchaseId.clear();



                }
            }
        }











        // System.out.println(comboBox.getValue());
//        int numberLotte = 1;
//        int c = 4;
//        int value = 0;
//        String r1 = "";
//        String r3 = "";
//        int f = 0 ;
//        try {
//            int q = Integer.parseInt(qualityofsend.getText());
//            if (q<=0){
//                f=3;
//            }
//
//        }catch (Exception e){
//            f = 1;
//        }
//
//        if (purchaseId.getText().equals("") || qualityofsend.getText().equals("") ||listView.getSelectionModel().getSelectedItem()==null ){
//                AlertBox.display("Error","Error some field is null");
//            }else if(f==1){
//            AlertBox.display("Error","Error quality field is not Integer");
//
//        }else if (f==3){
//                AlertBox.display("Error","Error number is lower or equal 0");
//
//        }
//            else {
//
//
//
//            String queryCheck = "SELECT * FROM Lotte";
//            ResultSet rs = conn.createStatement().executeQuery(queryCheck);
//
//
//            while (rs.next()) {
//                System.out.println(rs.getString(1));
//                System.out.println(rs.getString(3));
//                System.out.println(listView.getSelectionModel().getSelectedItem());
//                System.out.println("abc");
//                if (!rs.getString(1).equals(purchaseId.getText()) && !rs.getString(3).equals(listView.getSelectionModel().getSelectedItem())) {
//                    c = 0;
//                    numberLotte = 1;
//                    System.out.println("b");
//
//
//
//                } else if (rs.getString(1).equals(purchaseId.getText()) && rs.getString(3).equals(listView.getSelectionModel().getSelectedItem())) {
//                    c = 1;
//                    numberLotte = rs.getInt(2);
//                    value = rs.getInt(4);
//                    r1 = rs.getString(1);
//                    r3 = rs.getString(3);
//
//
//
//                } else if (rs.getString(1).equals(purchaseId.getText()) && !rs.getString(3).equals(listView.getSelectionModel().getSelectedItem())) {
//                    c = 2;
//                    numberLotte = 1;
//
//
//                } else if (!rs.getString(1).equals(purchaseId.getText()) && rs.getString(3).equals(listView.getSelectionModel().getSelectedItem())) {
//                    c = 3;
//                    value += rs.getInt(4);
//                    r1 = rs.getString(1);
//                    r3 = rs.getString(3);
//                    numberLotte = rs.getInt(2);
//                    System.out.println(value);
//
//
//
//                }
//
//            }
//
//
//            if (c == 0) {
//                System.out.println("go in c0");
//
//                String query2 = "INSERT INTO Lotte (purchaseid,numberlotte,id,qualityofsend) VALUES (?,?,?,?)";
//
//                PreparedStatement ps = conn.prepareStatement(query2);
//                ps.setString(1, purchaseId.getText());
//                ps.setInt(2, numberLotte);
//                ps.setString(3, listView.getSelectionModel().getSelectedItem());
//                ps.setInt(4, Integer.parseInt(qualityofsend.getText()));
//
//                ps.executeUpdate();
//                System.out.println("aa");
//                ps.close();
//                System.out.println(purchaseId.getText());
//                System.out.println(numberLotte);
//                System.out.println(listView.getSelectionModel().getSelectedItem());
//                System.out.println(qualityofsend.getText());
//
//                String query3 = "UPDATE Medicine SET qualitylotte = ? ,qualityofkeep = ? WHERE id = ?";
//                PreparedStatement ps2 = conn.prepareStatement(query3);
//                ps2.setInt(1, numberLotte);
//                ps2.setInt(2,Integer.parseInt(qualityofsend.getText()));
//                ps2.setString(3, listView.getSelectionModel().getSelectedItem());
//                ps2.executeUpdate();
//                ps2.close();
//                System.out.println(purchaseId.getText());
//                System.out.println(numberLotte);
//                System.out.println(listView.getSelectionModel().getSelectedItem());
//                System.out.println(qualityofsend.getText());
//
////            System.out.println("a");
//
////            }
//
//            } else if (c == 2) {
//                System.out.println("go in c2");
////            for (String i : id) {
//
//                String query2 = "INSERT INTO Lotte (purchaseid,numberlotte,id,qualityofsend) VALUES (?,?,?,?)";
//                PreparedStatement ps = conn.prepareStatement(query2);
//                ps.setString(1, purchaseId.getText());
//                ps.setInt(2, numberLotte);
//                ps.setString(3, listView.getSelectionModel().getSelectedItem());
//                ps.setInt(4, Integer.parseInt(qualityofsend.getText()));
//                System.out.println(purchaseId.getText());
//                System.out.println(numberLotte);
//                System.out.println(listView.getSelectionModel().getSelectedItem());
//                System.out.println(qualityofsend.getText());
//
//
//
//                ps.executeUpdate();
//                ps.close();
//
//
//                String query3 = "UPDATE Medicine SET qualitylotte = ?,qualityofkeep = ? WHERE id = ?";
//                PreparedStatement ps2 = conn.prepareStatement(query3);
//                ps2.setInt(1, numberLotte);
//                ps2.setInt(2, Integer.parseInt(qualityofsend.getText()));
//                ps2.setString(3, listView.getSelectionModel().getSelectedItem());
//                ps2.executeUpdate();
//                System.out.println(purchaseId.getText());
//                System.out.println(numberLotte);
//                System.out.println(listView.getSelectionModel().getSelectedItem());
//                System.out.println(qualityofsend.getText());
//
//                ps2.close();
//
//
////            }
//            } else if (c == 3) {
//                System.out.println("go in c3");
////            for (String i : id) {
//                value = value + Integer.parseInt(qualityofsend.getText());
//                String query2 = "INSERT INTO Lotte (purchaseid,numberlotte,id,qualityofsend) VALUES (?,?,?,?)";
//                PreparedStatement ps = conn.prepareStatement(query2);
//                ps.setString(1, purchaseId.getText());
//                ps.setInt(2, x + 1);
//                ps.setString(3, listView.getSelectionModel().getSelectedItem());
//                ps.setInt(4, Integer.parseInt(qualityofsend.getText()));
//                System.out.println(purchaseId.getText());
//                System.out.println(numberLotte);
//                System.out.println(listView.getSelectionModel().getSelectedItem());
//                System.out.println(qualityofsend.getText());
//
//                ps.executeUpdate();
//                ps.close();
//
//                String query3 = "UPDATE Medicine SET qualitylotte = ? ,qualityofkeep = ? WHERE id = ?";
//                PreparedStatement ps2 = conn.prepareStatement(query3);
//                ps2.setInt(1, x+ 1);
//                ps2.setInt(2,value);
//
//                ps2.setString(3, listView.getSelectionModel().getSelectedItem());
//                System.out.println(purchaseId.getText());
//                System.out.println(numberLotte);
//                System.out.println(listView.getSelectionModel().getSelectedItem());
//                System.out.println(qualityofsend.getText());
//
//                ps2.executeUpdate();
//                ps2.close();
//
////            }
//            } else if (c == 1) {
//                System.out.println("go in c1");
////            for (String i : id) {
//
//                value = value + Integer.parseInt(qualityofsend.getText());
//                String query2 = "UPDATE Lotte SET qualityofsend = ? WHERE purchaseid = ? AND id = ? AND numberlotte = ? ";
//                PreparedStatement ps = conn.prepareStatement(query2);
//                ps.setInt(1, value);
//                ps.setString(2, r1);
//                ps.setString(3, r3);
//                ps.setInt(4,numberLotte);
//                System.out.println(purchaseId.getText());
//                System.out.println(numberLotte);
//                System.out.println(listView.getSelectionModel().getSelectedItem());
//                System.out.println(qualityofsend.getText());
//
//
//                ps.executeUpdate();
//                ps.close();
//
//
//                String query3 = "UPDATE Medicine SET qualitylotte = ?,qualityofkeep = ? WHERE id = ?";
//                PreparedStatement ps2 = conn.prepareStatement(query3);
//                ps2.setInt(1, numberLotte);
//                ps2.setInt(2,value);
//                ps2.setString(3, listView.getSelectionModel().getSelectedItem());
//                System.out.println(purchaseId.getText());
//                System.out.println(numberLotte);
//                System.out.println(listView.getSelectionModel().getSelectedItem());
//                System.out.println(qualityofsend.getText());
//
//                ps2.executeUpdate();
//                ps2.close();
//
//                //statement.executeUpdate(query2);
//                System.out.println("ac");
//                value = 0;
//                r1 = "";
//                r3 = "";
//
//
////        }
//            } else if (c == 4) {
//                System.out.println("go in c4");
////            for (String i : id) {
//
//                String query2 = "INSERT INTO Lotte (purchaseid,numberlotte,id,qualityofsend) VALUES (?,?,?,?)";
////                String query3 = "UPDATE Medicine SET qualitylotte = ? WHERE id = ?";
////                PreparedStatement ps2 = conn.prepareStatement(query3);
//                PreparedStatement ps = conn.prepareStatement(query2);
//                ps.setString(1, purchaseId.getText());
//                ps.setInt(2, numberLotte);
//                ps.setString(3, listView.getSelectionModel().getSelectedItem());
//                ps.setInt(4, Integer.parseInt(qualityofsend.getText()));
//                System.out.println(purchaseId.getText());
//                System.out.println(numberLotte);
//                System.out.println(listView.getSelectionModel().getSelectedItem());
//                System.out.println(qualityofsend.getText());
//
//
//                ps.executeUpdate();
//                System.out.println("aa");
//
//                ps.close();
//
//                String query3 = "UPDATE Medicine SET qualitylotte = ?,qualityofkeep = ?  WHERE id = ?";
//                PreparedStatement ps2 = conn.prepareStatement(query3);
//                ps2.setInt(1, numberLotte);
//                ps2.setInt(2,Integer.parseInt(qualityofsend.getText()));
//                ps2.setString(3, listView.getSelectionModel().getSelectedItem());
//                System.out.println(purchaseId.getText());
//                System.out.println(numberLotte);
//                System.out.println(listView.getSelectionModel().getSelectedItem());
//                System.out.println(qualityofsend.getText());
//
//                ps2.executeUpdate();
//                ps2.close();
//                System.out.println("pass");
//
//
//
//
//
////
////                ps2.setInt(1,numberLotte);
////                ps2.setString(2,listView.getSelectionModel().getSelectedItem());
////                ps2.executeUpdate();
////            System.out.println("a");
//
//
////            }
//
//            }

//            conn.close();
//            AlertBox.display("Data Already Add", "Add Medice to Stock Succesfully");
//
//
//            purchaseId.clear();
//            qualityofsend.clear();
//
//            System.out.println("add complete");
//        }

    }
//    private void addNormal(){
//        for(String i :id){
//            String query2 = "INSERT INTO PurchaseOrder (purchaseid,numberlotte,id,qualityofsend,nameofsupplier) VALUES (?,?,?,?,?)";
//            PreparedStatement ps = conn.prepareStatement(query2);
//
//            ps.setString(1, purchaseId.getText());
//            ps.setInt(2, numberLotte);
//            ps.setString(3,i);
//            ps.setInt(4, Integer.parseInt(qualityofsend.getText()));
//            ps.setString(5, nameofsupplier.getText());
//            int rs2 = ps.executeUpdate();
//
//    }
//    private void addDuplicate(){
//
//    }

