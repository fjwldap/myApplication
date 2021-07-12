package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.dto.DataMovie;
import com.example.myapplication.dto.mInfo.MovieInfo;
import com.example.myapplication.service.MovieApiManager;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchFragment extends Fragment {
    private RecyclerAdapter adapter;

    private ViewGroup rootview;
    MainActivity mainActivity;
    private RecyclerView recyclerView;

    //    private String word = "사랑";
    private ArrayList<MovieInfo> list;
    private int page;
    private boolean[] types = new boolean[19];//체크된 장르
    private Spinner spinnerSort;
    private String selectedSort = "개봉일순";

    private ProgressDialog progressDialog;

    private MovieApiManager mManager;


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);
        getChildFragmentManager().beginTransaction().add(R.id.detailSearchFrame, new DetailSearchFragment()).commit();

        init();

        getChildFragmentManager().setFragmentResultListener("searchDetailInfo", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                String word = bundle.getString("word");
                Boolean isYearChecked = bundle.getBoolean("yearCheck");
                String yearStart = bundle.getString("yearStart");
                String yearEnd = bundle.getString("yearEnd");
                types = bundle.getBooleanArray("types");

                if (isYearChecked)
                    func(word);//연도조건 검색
                else
                    func(word);
            }
        });


        getChildFragmentManager().setFragmentResultListener("searchWord", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                String word = bundle.getString("word");
                Boolean isYearChecked = bundle.getBoolean("yearCheck");
                String yearStart = bundle.getString("yearStart");
                String yearEnd = bundle.getString("yearEnd");

                if (isYearChecked)
                    func(word);//연도조건 검색
                else
                    func(word);
            }
        });
        return rootview;
    }

    private void func(String word) {
        //진행다일로그 시작
        progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("잠시 기다려 주세요.");//헐 팝업처럼 뜨네
        progressDialog.show();

        list = new ArrayList<>();
        adapter = new RecyclerAdapter(mainActivity);
        recyclerView.setAdapter(adapter);

        mManager = new MovieApiManager();
        Observable<String> observable = mManager.getCount(word, "2020", "2020")
                .doOnComplete(() -> {

                });

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable
                .add(observable
                                .subscribeWith(new DisposableObserver<String>() {
                                    @Override
                                    public void onNext(String value) {
                                        int count = Integer.parseInt(value);
                                        page = ((count / 100) + 1);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                    }

                                    @Override
                                    public void onComplete() {
                                        ArrayList<Observable<ArrayList<MovieInfo>>> observableList = new ArrayList<>();

                                        for (int c = 1; c <= page; c++) {
                                            observableList.add(mManager.getListwithYear(word, c + "", "2020", "2020"));

                                            CompositeDisposable compositeDisposable1 = new CompositeDisposable();
                                            compositeDisposable1
                                                    .add(observableList.get(c - 1)
                                                                    .subscribeWith(new DisposableObserver<ArrayList<MovieInfo>>() {
                                                                        @Override
                                                                        public void onNext(ArrayList<MovieInfo> value) {
                                                                            for (int i = 0; i < value.size(); i++) {
                                                                                MovieInfo movieInfo = value.get(i);
                                                                                boolean isOk = true;//가능한 영화인지
                                                                                if (movieInfo.getMovieNm().contains(word) && movieInfo.getOpenDt().equals("") == false) {

                                                                                    if (movieInfo.getGenreAlt().contains("에로")) {
                                                                                        isOk = false;
                                                                                        break;
                                                                                    }

                                                                                    if (isOk) {
                                                                                        list.add(value.get(i));
                                                                                    }
                                                                                }
//                                                                Log.v("aa", "asdfasdf " + value.get(i).getMovieNm());
                                                                            }
                                                                        }


                                                                        @Override
                                                                        public void onError(Throwable e) {

                                                                        }

                                                                        @Override
                                                                        public void onComplete() {
                                                                            Log.v("aa", "총" + list.size());

                                                                        }
                                                                    })
                                                    );
                                        }

                                        Observable
                                                .zip(observableList, new Function<Object[], Object>() {
                                                    @Override
                                                    public Object apply(Object[] objects) throws Exception {

                                                        Log.v("aa", "출력하자" + list.size());
                                                        getData();
                                                        progressDialog.dismiss();//잠시기다려주세요 뜬 메시지 지운다

                                                        return null;
                                                    }
                                                })
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new DefaultObserver<Object>() {
                                                    @Override
                                                    public void onNext(Object value) {
                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {

                                                    }

                                                    @Override
                                                    public void onComplete() {
//                                                                progressDialog.dismiss();

                                                    }
                                                });
                                    }
                                })
                );
    }

    private void init() {
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);

//        spinnerSort = (Spinner) rootview.findViewById(R.id.spinnerSort);
    }

    public void getData() {//리사이클러뷰에 추가
        Log.v("aa", "a-----------" + list.size());
        for (MovieInfo m : list) {
            DataMovie dm = new DataMovie();
            dm.setName(m.getMovieNm());
            dm.setCode(m.getMovieCd());
            dm.setDate(m.getOpenDt());
            adapter.addItem(dm, false);//recycler에서 필요한 필수항목
        }
        adapter.notifyDataSetChanged();
    }
}