package za.ac.up.cs.cos221;

import javax.swing.*;
import java.sql.*;
import java.io.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static JFrame createGUI() {
        JFrame mainFrame = new JFrame();
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
        mainFrame.pack();
        mainFrame.setTitle("COS221_Prac4");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return mainFrame;
    }

    public static Connection createConnection() {
        String connString = new StringBuilder()
        .append("jdbc:")
        .append(System.getenv("SAKILA_DB_PROTO") + "://")
        .append(System.getenv("SAKILA_DB_HOST") + ":")
        .append(System.getenv("SAKILA_DB_PORT") + "/")
        .append(System.getenv("SAKILA_DB_NAME") + "?")
        .append("user=" + System.getenv("SAKILA_DB_USERNAME") + "&")
        .append("password=" + System.getenv("SAKILA_DB_PASSWORD")).toString();

        try {
            Connection conn = DriverManager.getConnection(connString);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main( String[] args )
    {
        //JFrame gui = createGUI();

        Connection conn = createConnection();
        if (conn == null) {
            System.out.println("Failed to connect to database!");
            System.exit(1);
        }
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM store");
            while(rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
