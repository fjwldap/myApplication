
package com.example.myapplication.dto.mList;

import com.example.myapplication.dto.mInfo.MovieInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class MovieListResult {

    @SerializedName("totCnt")
    @Expose
    private Integer totCnt;
    @SerializedName("movieList")
    @Expose
    private ArrayList<MovieInfo> movieList = null;

    public Integer getTotCnt() {
        return totCnt;
    }

    public ArrayList<MovieInfo> getMovieList() {
        return movieList;
    }

}
