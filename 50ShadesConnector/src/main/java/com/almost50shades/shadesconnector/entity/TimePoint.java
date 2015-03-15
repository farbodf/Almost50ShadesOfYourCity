/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almost50shades.shadesconnector.entity;

/**
 *
 * @author martin
 */
public class TimePoint {
    
    private String timezone;
    private String local;
    private String utc;

    public String getLocal() {
        return local;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getUTC() {
        return utc;
    }
    
}
