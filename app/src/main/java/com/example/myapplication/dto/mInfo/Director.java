
package com.example.myapplication.dto.mInfo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Director implements Parcelable{

    @SerializedName("peopleNm")
    @Expose
    private String peopleNm;

    public String getPeopleNm() {
        return peopleNm;
    }

    public void setPeopleNm(String peopleNm) {
        this.peopleNm = peopleNm;
    }

    public Director(){}

    protected Director(Parcel in)
    {
        peopleNm = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
dest.writeString(peopleNm);
    }


    public static final Parcelable.Creator<Director> CREATOR = new Creator<Director>(){

        @Override
        public Director createFromParcel(Parcel source) {
            return new Director(source);
        }

        @Override
        public Director[] newArray(int size) {
            return new Director[size];
        }
    };;
}
