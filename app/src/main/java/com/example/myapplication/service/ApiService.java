package com.example.myapplication.service;

import com.example.myapplication.dto.mInfo.MovieInfoResultDetail;
import com.example.myapplication.dto.mList.MovieListResultDetail;
import com.example.myapplication.dto.mNaver.NaverMovieList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {
    String cid= "f_NG7uaMSh2NgLJUErDY";
    String cpw= "dpGyEZW6Xd";

    //영화진흥원위원회 - 영화 정보 코드로 검색
    @GET("/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=9db1c08ae0d6c721bc3f57b4851d9f97")
    Observable<MovieInfoResultDetail> getMovieDetail(@Query("movieCd") String code);//코드를 넘긴다

    //네이버api - 영화 상세정보
    @Headers({"X-Naver-Client-Id:"+cid,"X-Naver-Client-Secret:"+cpw})
    @GET("search/movie")
    Observable<NaverMovieList> getMov(@Query("query") String name);//이름 넘긴다

    //영화진흥원위원회 - 영화 목록 개수 검색용
    @GET("/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=9db1c08ae0d6c721bc3f57b4851d9f97")
    Observable<MovieListResultDetail> getCount(@Query("movieNm") String name, @Query("itemPerPage") String cnt);//개수찾기

    //영화진흥원위원회 - 영화 목록 개수 검색용 + 연도
    @GET("/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=9db1c08ae0d6c721bc3f57b4851d9f97")
    Observable<MovieListResultDetail> getCount(@Query("movieNm") String name, @Query("itemPerPage") String cnt, @Query("openStartDt") String startDt, @Query("openEndDt") String endDt);//개수찾기 +연도

    //영화진흥원위원회 - 영화 목록 이름으로 페이지마다 검색
    @GET("/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=9db1c08ae0d6c721bc3f57b4851d9f97")
    Observable<MovieListResultDetail> getMovieList(@Query("movieNm") String name,@Query("curPage") String page, @Query("itemPerPage") String cnt);//이름으로 검색

    //영화진흥원위원회 - 영화 목록 이름으로 페이지마다 검색 + 연도
    @GET("/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=9db1c08ae0d6c721bc3f57b4851d9f97")
    Observable<MovieListResultDetail> getMovieListwithYear(@Query("movieNm") String name,@Query("curPage") String page, @Query("itemPerPage") String cnt, @Query("openStartDt") String startDt, @Query("openEndDt") String endDt);//이름으로 검색
}
