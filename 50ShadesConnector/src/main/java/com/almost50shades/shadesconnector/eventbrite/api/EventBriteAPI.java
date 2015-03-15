/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almost50shades.shadesconnector.eventbrite.api;

import com.almost50shades.shadesconnector.entity.Event;
import com.almost50shades.shadesconnector.entity.EventList;
import java.util.List;

/**
 *
 * @author martin
 */
public interface EventBriteAPI {

    public EventList searchForEvent(Param ... params);
    public List<Event> searchForAllEvents(Param ... params);
    
}
