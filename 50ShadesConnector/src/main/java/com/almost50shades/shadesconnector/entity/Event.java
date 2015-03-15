/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almost50shades.shadesconnector.entity;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author martin
 */
public class Event {

    private String id;
    private Name name;
    private TimePoint start;
    private TimePoint end;
    @SerializedName("online_event")
    private boolean onlineEvent;
    private Category category;
    private Venue venue;

    public TimePoint getEnd() {
        return end;
    }

    public String getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public TimePoint getStart() {
        return start;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isOnlineEvent() {
        return onlineEvent;
    }

    public Venue getVenue() {
        return venue;
    }

}
