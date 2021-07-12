
package com.example.myapplication.dto.mNaver;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class NaverMovie implements Parcelable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("director")
    @Expose
    private String director;
    @SerializedName("userRating")
    @Expose
    private String userRating;

    public String getTitle() {
        String str = title.replace("<b>","");
        str = str.replace("</b>","");//검색어가 해당되는거 b표시 제거
        return str;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

    public String getDirector() {
        return director;
    }

    public String getUserRating() {
        return userRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(image);
        dest.writeString(director);
        dest.writeString(userRating);
    }
    public NaverMovie(){}

    protected NaverMovie(Parcel in) {
        this();

        title = in.readString();
        link = in.readString();
        image = in.readString();
        director = in.readString();
        userRating = in.readString();
    }
    public static final Creator<NaverMovie> CREATOR = new Creator<NaverMovie>() {
        @Override
        public NaverMovie createFromParcel(Parcel in) {
            return new NaverMovie(in);
        }

        @Override
        public NaverMovie[] newArray(int size) {
            return new NaverMovie[size];
        }
    };
}
