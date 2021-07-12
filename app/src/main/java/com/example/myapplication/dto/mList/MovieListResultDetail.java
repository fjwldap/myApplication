
package com.example.myapplication.dto.mList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class MovieListResultDetail {

    @SerializedName("movieListResult")
    @Expose
    private MovieListResult movieListResult;

    public MovieListResult getMovieListResult() {
        return movieListResult;
    }

    public void setMovieListResult(MovieListResult movieListResult) {
        this.movieListResult = movieListResult;
    }

}
