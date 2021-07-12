package com.example.myapplication.service;

import com.example.myapplication.dto.mNaver.NaverMovieList;
import com.example.myapplication.dto.mNaver.NaverMovie;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NaverMovieApiManager {
    private final ApiService mApiService;

    public NaverMovieApiManager(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://openapi.naver.com/v1/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mApiService = retrofit.create(ApiService.class);
    }

    public Observable<ArrayList<NaverMovie>> getMov(String name){
        return mApiService
                .getMov(name)
                .map(new Function<NaverMovieList, ArrayList<NaverMovie>>() {
                    @Override
                    public ArrayList<NaverMovie> apply(NaverMovieList naverMovieList) throws Exception {
                        return naverMovieList.getItems();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
