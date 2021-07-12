
package com.example.myapplication.dto.mInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class MovieInfoResultDetail {

    @SerializedName("movieInfoResult")
    @Expose
    private MovieInfoResult movieInfoResult;

    public MovieInfoResult getMovieInfoResult() {
        return movieInfoResult;
    }

    public void setMovieInfoResult(MovieInfoResult movieInfoResult) {
        this.movieInfoResult = movieInfoResult;
    }

}
