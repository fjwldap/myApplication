
package com.example.myapplication.dto.mInfo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class MovieInfo implements Parcelable {

    @SerializedName("movieCd")
    @Expose
    private String movieCd;
    @SerializedName("movieNm")
    @Expose
    private String movieNm;
    @SerializedName("movieNmEn")
    @Expose
    private String movieNmEn;
    @SerializedName("showTm")
    @Expose
    private String showTm;
    @SerializedName("openDt")
    @Expose
    private String openDt;
    @SerializedName("genreAlt")
    @Expose
    private String genreAlt;
    @SerializedName("nations")
    @Expose
    private ArrayList<Nation> nations = null;
    @SerializedName("genres")
    @Expose
    private ArrayList<Genre> genres = null;
    @SerializedName("directors")
    @Expose
    private ArrayList<Director> directors = null;
    @SerializedName("actors")
    @Expose
    private ArrayList<Actor> actors = null;
    @SerializedName("audits")
    @Expose
    private ArrayList<Audit> audits = null;

    public String getMovieCd() {
        return movieCd;
    }

    public String getMovieNm() {
        return movieNm;
    }

    public String getMovieNmEn() {
        return movieNmEn;
    }

    public String getShowTm() {
        return showTm;
    }

    public String getOpenDt() {
        if(openDt==null || openDt.contains("-")||openDt.length()<8)
            return openDt;
        String str = openDt.substring(0,4)+"-"+openDt.substring(4,6)+"-"+openDt.substring(6,8);
            return str;
    }

    public String getGenreAlt() {
        return genreAlt;
    }

    public void setGenreAlt(String genreAlt) {
        this.genreAlt = genreAlt;
    }

    public String getNations() {
        if (nations.size() != 0)
            return nations.get(0).getNationNm();
        return "";
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public String getDirectors() {
        if (directors.size() != 0)
            return directors.get(0).getPeopleNm();
        return "";
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public String getAudits() {
        if (audits.size() != 0)
            return audits.get(0).getWatchGradeNm();
        return "";
    }

    public MovieInfo() {
        nations = new ArrayList<Nation>();
        genres = new ArrayList<Genre>();
        directors = new ArrayList<Director>();
        actors = new ArrayList<Actor>();
        audits = new ArrayList<Audit>();
    }

    protected MovieInfo(Parcel in) {
        this();

        movieCd = in.readString();
        movieNm = in.readString();
        showTm = in.readString();
        openDt = in.readString();
        genreAlt = in.readString();
        in.readTypedList(nations, Nation.CREATOR);
        in.readTypedList(genres, Genre.CREATOR);
        in.readTypedList(directors, Director.CREATOR);
        in.readTypedList(actors, Actor.CREATOR);
        in.readTypedList(audits, Audit.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieCd);
        dest.writeString(movieNm);
        dest.writeString(showTm);
        dest.writeString(openDt);
        dest.writeString(genreAlt);
        dest.writeTypedList(nations);
        dest.writeTypedList(genres);
        dest.writeTypedList(directors);
        dest.writeTypedList(actors);
        dest.writeTypedList(audits);
    }

    public static final Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };
}
