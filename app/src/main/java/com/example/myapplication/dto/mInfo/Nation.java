
package com.example.myapplication.dto.mInfo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Nation implements Parcelable {

    @SerializedName("nationNm")
    @Expose
    private String nationNm;

    public String getNationNm() {
        return nationNm;
    }

    public void setNationNm(String nationNm) {
        this.nationNm = nationNm;
    }

    public Nation(){}

    protected Nation(Parcel in){
        nationNm = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(nationNm);
    }

    public static  final Creator<Nation> CREATOR = new Creator<Nation>(){

        @Override
        public Nation createFromParcel(Parcel source) {
            return new Nation(source);
        }

        @Override
        public Nation[] newArray(int size) {
            return new Nation[size];
        }
    };
}
