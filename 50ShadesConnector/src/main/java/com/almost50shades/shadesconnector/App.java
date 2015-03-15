package com.almost50shades.shadesconnector;

import com.almost50shades.shadesconnector.database.Database;
import com.almost50shades.shadesconnector.entity.Event;
import com.almost50shades.shadesconnector.eventbrite.api.EventBriteAPI;
import com.almost50shades.shadesconnector.eventbrite.api.EventBriteImpl;
import com.almost50shades.shadesconnector.eventbrite.api.Param;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author martin
 */
public class App {

    public static void main(String[] args) throws SQLException {
        Database database = new Database("eventdata");
        EventBriteAPI api = new EventBriteImpl("CD6VJVWIEC225TEFTVS7", database);
        List<Event> events = api.searchForAllEvents(new Param[]{new Param("venue.country", "NL")});
        api.searchForAllEvents(new Param[]{new Param("venue.country", "DK")});
        api.searchForAllEvents(new Param[]{new Param("venue.country", "AT")});
        //for (Event event : events) {
        //    database.saveEvent(event);
        //}
    }

}
