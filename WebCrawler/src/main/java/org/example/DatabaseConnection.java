package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // This connection will be shared across the application (Singleton pattern)
    private static Connection connection = null;

    public static Connection getConnection(){
        if(connection != null){
            return connection;
        }
        // Default database credentials
        String user = "root";
        String pwd = "database";
        String db = "SearchEngineApp";
        return getConnection(user, pwd, db);
    }

    // Private method to establish the connection
    private static Connection getConnection(String user, String pwd, String db){
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/" + db + "?user=" + user + "&password=" + pwd;

            connection = DriverManager.getConnection(connectionUrl);
        }
        catch(SQLException | ClassNotFoundException e) {
            // Print the full error trace if connection fails
            e.printStackTrace();
        }
        return connection;
    }
}