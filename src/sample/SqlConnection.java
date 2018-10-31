package sample;

import java.lang.reflect.Executable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;


public class SqlConnection {


    public static Connection conn;

    public static Connection DbConnector(){
        try {
            conn = null;
            Class.forName("org.sqlite.JDBC");
            conn=DriverManager.getConnection("jdbc:sqlite:sa.db");

            return conn;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;


    }

    public static void closeConnect(){
        try {
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }



}









