package com.example.myapplication.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

//영화 박스오피스
public class MovieRank implements Parcelable {
    ArrayList<DataMovie> list;

    public MovieRank(){}

    protected MovieRank(Parcel in) {
        list = new ArrayList<>();
        in.readTypedList(list, DataMovie.CREATOR);
    }

    public static final Creator<MovieRank> CREATOR = new Creator<MovieRank>() {
        @Override
        public MovieRank createFromParcel(Parcel in) {
            return new MovieRank(in);
        }

        @Override
        public MovieRank[] newArray(int size) {
            return new MovieRank[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(list);
    }

    public ArrayList<DataMovie> getList() {
        return list;
    }

    public void setList(ArrayList<DataMovie> list) {
        this.list = list;
    }

    public int getSize(){
        return list.size();
    }

}
