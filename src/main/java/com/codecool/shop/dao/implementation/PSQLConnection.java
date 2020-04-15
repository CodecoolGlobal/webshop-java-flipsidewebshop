package com.codecool.shop.dao.implementation;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PSQLConnection {

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/flipsideshop";
    private static final String DB_USER = "username"; // TODO: update
    private static final String DB_PASSWORD = "pswd"; // TODO: update

    private static PSQLConnection dbIsntance;
    private static Connection connection;
    private static Statement stmt;


    private PSQLConnection() {
    }

    public static PSQLConnection getInstance() {
        if (dbIsntance == null) {
            dbIsntance = new PSQLConnection();
        }
        return dbIsntance;
    }

    public Connection getConnection() {

        if (connection == null) {
            try {
                String host = DATABASE;
                String username = DB_USER;
                String password = DB_PASSWORD;
                connection = DriverManager.getConnection(host, username, password);
            } catch (SQLException ex) {
                Logger.getLogger(PSQLConnection.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        }

        return connection;
    }

    private void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
        ){
            statement.execute(query);

        } catch (SQLException ex) {
            Logger.getLogger(PSQLConnection.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}
