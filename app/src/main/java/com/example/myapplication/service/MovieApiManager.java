package com.example.myapplication.service;

import com.example.myapplication.dto.mInfo.MovieInfo;
import com.example.myapplication.dto.mInfo.MovieInfoResult;
import com.example.myapplication.dto.mInfo.MovieInfoResultDetail;
import com.example.myapplication.dto.mList.MovieListResultDetail;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieApiManager {
    private final ApiService mApiService;

    public MovieApiManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.kobis.or.kr")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mApiService = retrofit.create(ApiService.class);
    }

    //영화 상세 정보
    public Observable<MovieInfoResult> getMIR(String code) {
        return mApiService
                .getMovieDetail(code)
                .map(new Function<MovieInfoResultDetail, MovieInfoResult>() {
                    @Override
                    public MovieInfoResult apply(MovieInfoResultDetail movieInfoResultDetail) throws Exception {
                        return movieInfoResultDetail.getMovieInfoResult();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //검색된 영화 총 수
    public Observable<String> getCount(String name) {
        return mApiService.getCount(name, "100")
                .map(new Function<MovieListResultDetail, String>() {
                    @Override
                    public String apply(MovieListResultDetail movieListResultDetail) throws Exception {
                        return movieListResultDetail.getMovieListResult().getTotCnt() + "";
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //검색된 영화 총 수 + 연도
    public Observable<String> getCount(String name, String startDt, String endDt) {
        return mApiService.getCount(name, "100",startDt,endDt)
                .map(new Function<MovieListResultDetail, String>() {
                    @Override
                    public String apply(MovieListResultDetail movieListResultDetail) throws Exception {
                        return movieListResultDetail.getMovieListResult().getTotCnt() + "";
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //이름으로 검색한다
    public Observable<ArrayList<MovieInfo>> getList(String name, String page) {
        return mApiService
                .getMovieList(name, page, "100")
                .map(new Function<MovieListResultDetail, ArrayList<MovieInfo>>() {
                    @Override
                    public ArrayList<MovieInfo> apply(MovieListResultDetail movieListResultDetail) throws Exception {
                        return movieListResultDetail.getMovieListResult().getMovieList();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //이름으로 검색한다 + 연도
    public Observable<ArrayList<MovieInfo>> getListwithYear(String name, String page, String startDt, String endDt) {
        return mApiService
                .getMovieListwithYear(name, page, "100",startDt,endDt)
                .map(new Function<MovieListResultDetail, ArrayList<MovieInfo>>() {
                    @Override
                    public ArrayList<MovieInfo> apply(MovieListResultDetail movieListResultDetail) throws Exception {
                        return movieListResultDetail.getMovieListResult().getMovieList();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
