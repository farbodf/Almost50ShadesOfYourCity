/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almost50shades.shadesconnector.eventbrite.api;

import com.almost50shades.shadesconnector.BasicClient;
import com.almost50shades.shadesconnector.Client;
import com.almost50shades.shadesconnector.database.Database;
import com.almost50shades.shadesconnector.entity.Event;
import com.almost50shades.shadesconnector.entity.EventList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.httpclient.URIException;

/**
 *
 * @author martin
 */
public class EventBriteImpl implements EventBriteAPI {

    private Gson gson;
    private Client client;
    private UrlBuilder builder;
    private Database database;

    public EventBriteImpl(String token, Database database) {
        gson = new GsonBuilder().create();
        builder = new UrlBuilder(token);
        client = new BasicClient();
        this.database = database;
    }

    @Override
    public EventList searchForEvent(Param... params) {

        String url = builder.createUrl(UrlBuilder.EVENT_SEARCH,
                params);
        String json = client.executeGetRequest(url);
        EventList events = gson.fromJson(json, EventList.class);
        return events;

    }

    @Override
    public List<Event> searchForAllEvents(Param... params) {
        Param[] nParams = new Param[params.length + 1];
        for (int i = 0; i < params.length; ++i) {
            nParams[i] = params[i];
        }
        List<Event> list = new LinkedList<Event>();
        int page = 1;
        while (true) {
            nParams[params.length] = new Param("page", Integer.toString(page));
            EventList events = searchForEvent(nParams);
            for (Event event : events.getEvents()) {
                try {
                    database.saveEvent(event);
                } catch (SQLException ex) {
                    System.out.println("ERROR");
                    Logger.getLogger(EventBriteImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            list.addAll(events.getEvents());
            ++page;
            System.out.println(events.getPagination().getPageNumber() + " " + events.getPagination().getPageCount());
            if (events.getPagination().getPageNumber() == events.getPagination().getPageCount()) {
                break;
            }
        }
        return list;
    }

}
