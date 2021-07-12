
package com.example.myapplication.dto.mInfo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Audit implements Parcelable {

    @SerializedName("watchGradeNm")
    @Expose
    private String watchGradeNm;

    public Audit() {
    }

    protected Audit(Parcel in) {
        watchGradeNm = in.readString();
    }

    public String getWatchGradeNm() {
        return watchGradeNm;
    }

    public void setWatchGradeNm(String watchGradeNm) {
        this.watchGradeNm = watchGradeNm;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(watchGradeNm);
    }

    public static final Parcelable.Creator<Audit> CREATOR = new Creator<Audit>() {

        @Override
        public Audit createFromParcel(Parcel source) {
            return new Audit(source);
        }

        @Override
        public Audit[] newArray(int size) {
            return new Audit[size];
        }
    };
}
