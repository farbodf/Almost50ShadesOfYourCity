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
public class Venue {

    private String name;
    private String latitude;
    private String longitude;

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return Double.parseDouble(longitude);
    }
    
    public double getLatitude() {
        return Double.parseDouble(latitude);
    }

}
