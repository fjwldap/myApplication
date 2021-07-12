
package com.example.myapplication.dto.mNaver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class NaverMovieList {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("items")
    @Expose
    private ArrayList<NaverMovie> items = null;

    public Integer getTotal() {
        return total;
    }

    public ArrayList<NaverMovie> getItems() {
        return items;
    }

}
