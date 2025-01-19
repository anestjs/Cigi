package main.java.com.cigiproject.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final String url = "jdbc:mysql://localhost:3306/cigi"; 
    private static final String user = "root";  
    private static final String password = ""; 

    public static Connection connexion() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
