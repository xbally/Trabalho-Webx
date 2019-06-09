package com.ufpr.tads.web2.dao;
//
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Gabriel
 */
public class ConnectionFactory {
    public static Connection getConnection() {
        
        try {
            Class.forName("org.postgresql.Driver");
            //Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/trabalho_web2x", "postgres", "root");
            //return DriverManager.getConnection("jdbc:mysql://localhost:3306/tcc","root","root");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException (e);
        }
    }
}
