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
public class Pagination {
    
    @SerializedName("object_count")
    private int objectCount;
    
    @SerializedName("page_number")
    private int pageNumber;
    
    @SerializedName("page_size")
    private int pageSize;
    
    @SerializedName("page_count")
    private int pageCount;

    public int getObjectCount() {
        return objectCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }
    
}
