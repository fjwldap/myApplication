package com.example.myapplication.dto;

import android.os.Parcel;
import android.os.Parcelable;

//영화 정보
public class DataMovie implements Parcelable {
    private String code;//영화코드 primarykey
    private String name;//이름
    private String date;//개봉일
    private double score;//평점
    private String rank;//박스오피스 순위
    private String rankOldAndNew;//신규 진입 여부
    private String audiAcc;//누적 관객수
    private boolean isScored;//내 평가

    public DataMovie() {
    }

    protected DataMovie(Parcel in) {
        code = in.readString();
        name = in.readString();
        date = in.readString();
        score = in.readDouble();
        rank = in.readString();
        rankOldAndNew = in.readString();
        audiAcc = in.readString();
    }

    public static final Creator<DataMovie> CREATOR = new Creator<DataMovie>() {
        @Override
        public DataMovie createFromParcel(Parcel in) {
            return new DataMovie(in);
        }

        @Override
        public DataMovie[] newArray(int size) {
            return new DataMovie[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        if(date==null || date.contains("-"))
            return date;
        String str = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
        return str;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRankOldAndNew() {
        return rankOldAndNew;
    }

    public void setRankOldAndNew(String rankOldAndNew) {
        this.rankOldAndNew = rankOldAndNew;
    }

    public String getAudiAcc() {
        return audiAcc;
    }

    public void setAudiAcc(String audiAcc) {
        this.audiAcc = audiAcc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(date);
        dest.writeDouble(score);
        dest.writeString(rank);
        dest.writeString(rankOldAndNew);
        dest.writeString(audiAcc);
    }

}
