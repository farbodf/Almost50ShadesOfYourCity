/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almost50shades.shadesconnector.database;

import com.almost50shades.shadesconnector.entity.Event;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author martin
 */
public class Database {
    
    private Connection connection;
    private PreparedStatement statement;
    private final String sql = "INSERT INTO events (name,latitude,longitude,date,label) " +
                   "VALUES (?,?,?,?,?);";
    
    public Database(String name) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + name);
            statement = connection.prepareStatement(sql);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveEvent(Event event) throws SQLException {
        if (event.getCategory() == null) return;
        statement.setString(1, event.getName().getText());
        statement.setDouble(2, event.getVenue().getLatitude());
        statement.setDouble(3, event.getVenue().getLongitude());
        statement.setString(4, event.getStart().getUTC());
        statement.setString(5, event.getCategory().getName());
        statement.executeUpdate();
    }
    
    public void stop() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
