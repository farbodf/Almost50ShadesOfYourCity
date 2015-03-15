/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almost50shades.shadesconnector.entity;

import java.util.List;

/**
 *
 * @author martin
 */
public class EventList {

    private Pagination pagination;
    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public Pagination getPagination() {
        return pagination;
    }

}
