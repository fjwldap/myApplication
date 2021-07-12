package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.dto.DataMovie;
import com.example.myapplication.dto.mInfo.MovieInfo;
import com.example.myapplication.dto.mInfo.MovieInfoResult;
import com.example.myapplication.dto.mNaver.NaverMovie;
import com.example.myapplication.service.MovieApiManager;
import com.example.myapplication.service.NaverMovieApiManager;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    private ArrayList<DataMovie> list = new ArrayList<>();
    private Context context;
    MainActivity mainActivity;

    private MovieApiManager mManager;
    private NaverMovieApiManager mManager2;
    private MovieInfo movieInfo;
    private NaverMovie naverMovie;

    private Boolean listOrSearch;

    public RecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_itemview, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(list.get(position), listOrSearch);//하나씩 보여준다
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    void addItem(DataMovie data, boolean b) {//아이템 추가
        list.add(data);
        listOrSearch = b;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1;//개봉일
        private TextView textView2;//제목
        private TextView textView3;//평점
        private Button button;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textOpenDate);
            textView2 = itemView.findViewById(R.id.textTitle);
            textView3 = itemView.findViewById(R.id.textScore);
            button = itemView.findViewById(R.id.buttonToDetail);
        }

        void onBind(DataMovie data, boolean b) {
            String date = data.getDate();

            textView1.setText(date);
            textView2.setText(data.getName() + "\uD83D\uDD0E");
            if (b == true)
                textView3.setText(data.getScore() + "");
            else
                textView3.setVisibility(View.INVISIBLE);//검색할때는 평점 표시 안한다..

            button.setOnClickListener(new Button.OnClickListener() {//기록하러 가기
                @Override
                public void onClick(View view) {
                    button.setBackgroundColor(Color.RED);
                    Intent intent = new Intent(view.getContext(), ScoreActivity.class);
                    intent.putExtra("date", data.getDate());//영화개봉일
                    intent.putExtra("code", data.getCode());//영화코드*
                    intent.putExtra("name", data.getName());//영화이름
//                    intent.putExtra("dir",data.get)
                    ((Activity) context).startActivity(intent);
                }
            });

            textView2.setOnClickListener(new View.OnClickListener() {//영화 정보 보기
                @Override
                public void onClick(View view) {
                    textView2.setTextColor(Color.RED);

                    mManager = new MovieApiManager();
                    Observable<MovieInfoResult> observable1 = mManager.getMIR(data.getCode());

                    CompositeDisposable compositeDisposable1 = new CompositeDisposable();

                    compositeDisposable1.add(observable1.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<MovieInfoResult>() {

                                @Override
                                public void onNext(MovieInfoResult value) {
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {
                                }
                            })
                    );
                    //영화진흥원 api--------------------------------------------------------------------------------

                    mManager2 = new NaverMovieApiManager();
                    Observable<ArrayList<NaverMovie>> observable2 = mManager2.getMov(data.getName());

                    CompositeDisposable compositeDisposable2 = new CompositeDisposable();

                    compositeDisposable2.add(observable2.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<ArrayList<NaverMovie>>() {
                                @Override
                                public void onNext(ArrayList<NaverMovie> value) {
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {
                                }
                            })
                    );
                    //네이버영화 api--------------------------------------------------------------------------------

                    Observable
                            .zip(observable1, observable2, new BiFunction<MovieInfoResult, ArrayList<NaverMovie>, Object>() {
                                @Override
                                public Object apply(MovieInfoResult movieInfoResult, ArrayList<NaverMovie> naverMovies) throws Exception {
                                    movieInfo = movieInfoResult.getMovieInfo();
                                    naverMovie = new NaverMovie();
                                    for (int i = 0; i < naverMovies.size(); i++) {
                                        if (naverMovies.get(i).getTitle().equals(movieInfo.getMovieNm()) && naverMovies.get(i).getDirector().contains(movieInfo.getDirectors()))
                                            naverMovie = naverMovies.get(i);//이름과 감독이 같은 그것
                                    }

                                    Intent intent = new Intent(view.getContext(), AboutActivity.class);//상세정보보기
                                    intent.putExtra("info", movieInfo);//정보 전달
                                    intent.putExtra("nMovie", naverMovie);//정보 전달
                                    ((Activity) context).startActivity(intent);

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

                                }
                            });
                }
            });
        }
    }
}
