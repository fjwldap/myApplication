
package com.example.myapplication.dto.mInfo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Genre implements Parcelable{

    @SerializedName("genreNm")
    @Expose
    private String genreNm;

    public Genre(){}

    protected Genre(Parcel in) {
        genreNm = in.readString();
    }

    public String getGenreNm() {
        return genreNm;
    }

    public void setGenreNm(String genreNm) {
        this.genreNm = genreNm;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(genreNm);
    }

    public static final Parcelable.Creator<Genre> CREATOR  = new Creator<Genre>(){

        @Override
        public Genre createFromParcel(Parcel source) {
            return new Genre(source);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };
}
