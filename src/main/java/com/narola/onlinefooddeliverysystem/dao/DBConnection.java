/*
package com.narola.onlinefooddeliverysystem.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection dbConnection = null;
    private Connection con = null;

    private static String CONNECTstr = "jdbc:mysql://localhost:3306/onlinefooddelivery";
    private static String username = "root";
    private static String password = "";

    private DBConnection() {

    }

    public static DBConnection getInstance() {
        if(dbConnection == null){
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    public Connection getConnection() {
        if (con == null) {
            try {
               con = DriverManager.getConnection(CONNECTstr, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return con;
    }
}
*/
