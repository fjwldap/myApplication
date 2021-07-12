
package com.example.myapplication.dto.mInfo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Actor implements Parcelable {

    @SerializedName("peopleNm")
    @Expose
    private String peopleNm;
    @SerializedName("cast")
    @Expose
    private String cast;

    public Actor(){}

    protected Actor(Parcel in) {
        peopleNm = in.readString();
        cast = in.readString();
    }

    public String getPeopleNm() {
        return peopleNm;
    }

    public void setPeopleNm(String peopleNm) {
        this.peopleNm = peopleNm;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(peopleNm);
        dest.writeString(cast);
    }


    public static final Parcelable.Creator<Actor> CREATOR =  new Creator<Actor>(){

        @Override
        public Actor createFromParcel(Parcel source) {
            return new Actor(source);
        }

        @Override
        public Actor[] newArray(int size) {
            return new Actor[size];
        }
    };
}
