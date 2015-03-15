/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg50shadesrequestreader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg50shadesrequestreader.Main.BoundingBox;

/**
 *
 * @author martin
 */
public class Database {
    
    private Connection connection;
    private PreparedStatement statement;
    private final String EVENT_SQL = "SELECT * FROM events WHERE longitude>=? AND longitude <= ? AND "
            + " latitude >= ? AND latitude <= ?";
    
    public Database(String name) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + name);
            statement = connection.prepareStatement(EVENT_SQL);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<TrainingEntity> getEntities(BoundingBox box) throws SQLException {
        List<TrainingEntity> list = new LinkedList<TrainingEntity>();
        statement.setDouble(1, box.bottomLeftX);
        statement.setDouble(2, box.topRightX);
        statement.setDouble(3, box.bottomLeftY);
        statement.setDouble(4, box.topRightY);
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            String label = res.getString("label");
            double x = res.getDouble("longitude");
            double y = res.getDouble("latitude");
            TrainingEntity entity = new TrainingEntity(label, x, y);
            list.add(entity);
        }
        return list;
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
