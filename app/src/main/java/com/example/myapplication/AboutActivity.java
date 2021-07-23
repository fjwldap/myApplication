package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.dto.mInfo.MovieInfo;
import com.example.myapplication.dto.mNaver.NaverMovie;

public class AboutActivity extends AppCompatActivity {
    private String code;
    private MovieInfo movieInfo;
    private NaverMovie naverMovie;
    private String NaverLink;

    private TextView textViewName;
    private TextView textViewDate;
    private TextView textViewTime;
    private TextView textViewDir;
    private TextView textViewNat;
    private TextView[] textViewGenre;
    private TextView[] textViewActor;
    private TextView textViewAudit;

    private LinearLayout[] LayoutActors;

    private Button buttonGoScore;
    private Button buttonexitAbout;

    private ImageView imageViewPoster;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;


    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException, IndexOutOfBoundsException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        init();

        Intent intent = getIntent();//저번페이지에서 받아옴
        movieInfo = intent.getParcelableExtra("info");//영화정보
        naverMovie = intent.getParcelableExtra("nMovie");//네이버 영화 정보
        NaverLink = naverMovie.getLink();//네이버영화 링크

        setData();
        String str = "https://search.pstatic.net/common/?src=https%3A%2F%2Fssl.pstatic.net%2Fsstatic%2Fpeople%2Fportrait%2F202002%2F20200203155236234-7932904.jpg&type=u111_139&quality=95";

        Glide.with(this).load(naverMovie.getImage())
//                .placeholder(R.drawable.ic_launcher_background)//미리보기
                .error(R.drawable.ic_android_black_24dp)//에러
                .into(imageViewPoster);

        Glide.with(this).load(str)
//                .placeholder(R.drawable.ic_launcher_background)//미리보기
//                .error(R.drawable.error_image)//에러
                .into(imageView1);
        Glide.with(this).load(str)
//                .placeholder(R.drawable.ic_launcher_background)//미리보기
//                .error(R.drawable.error_image)//에러
                .into(imageView2);
        Glide.with(this).load(str)
//                .placeholder(R.drawable.ic_launcher_background)//미리보기
//                .error(R.drawable.error_image)//에러
                .into(imageView3);


        buttonGoScore.setOnClickListener(new View.OnClickListener() {//영화 평가하러 가기
            @Override
            public void onClick(View view) {
                finish();//일단 얘를 닫고
                Intent intent = new Intent(view.getContext(), ScoreActivity.class);
//                intent.putExtra("info", movieInfo);//영화정보 넘긴다 사실 정보말고 이름/코드/감독/개봉일 정도만 넘겨도 됨
                intent.putExtra("date", movieInfo.getOpenDt());//영화개봉일
                intent.putExtra("code", movieInfo.getMovieCd());//영화코드*
                intent.putExtra("name", movieInfo.getMovieNm());//영화이름
                 startActivity(intent);
            }
        });

        buttonexitAbout.setOnClickListener(new View.OnClickListener() {//닫기
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        LayoutActors = new LinearLayout[3];//배우 목록
        LayoutActors[0] = (LinearLayout) findViewById(R.id.LayoutActor1);
        LayoutActors[1] = (LinearLayout) findViewById(R.id.LayoutActor2);
        LayoutActors[2] = (LinearLayout) findViewById(R.id.LayoutActor3);

        textViewGenre = new TextView[2];
        textViewActor = new TextView[3];

        textViewGenre[0] = (TextView) findViewById(R.id.movieGenre1);
        textViewGenre[1] = (TextView) findViewById(R.id.movieGenre2);
        textViewActor[0] = (TextView) findViewById(R.id.movieActor1);
        textViewActor[1] = (TextView) findViewById(R.id.movieActor2);
        textViewActor[2] = (TextView) findViewById(R.id.movieActor3);

        textViewName = (TextView) findViewById(R.id.movieNameA);
        textViewDate = (TextView) findViewById(R.id.movieDate);
        textViewTime = (TextView) findViewById(R.id.movieTime);
        textViewDir = (TextView) findViewById(R.id.movieDir);
        textViewNat = (TextView) findViewById(R.id.movieNat);
        textViewAudit = (TextView) findViewById(R.id.movieAudit);

        buttonGoScore = (Button) findViewById(R.id.buttonGoScore);
        buttonexitAbout = (Button) findViewById(R.id.buttonExitAbout);

        imageViewPoster = (ImageView) findViewById(R.id.imagePoster);

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
    }

    private void setData() {
        textViewDate.setText(movieInfo.getOpenDt());
        textViewName.setText(movieInfo.getMovieNm());
        textViewDir.setText(movieInfo.getDirectors());
        textViewTime.setText(movieInfo.getShowTm());
        textViewNat.setText(movieInfo.getNations());
        textViewAudit.setText(movieInfo.getAudits().substring(0,2));//이건 나중에 switch case

        if (movieInfo.getGenres().size() < 2) {
            //장르가 한개면
            for (int i = movieInfo.getGenres().size(); i < 2; i++)
                textViewGenre[i].setVisibility(View.GONE);//없애기
            for (int i = 0; i < movieInfo.getGenres().size(); i++)
                textViewGenre[i].setText(movieInfo.getGenres().get(i).getGenreNm());
        } else {
            for (int i = 0; i < 2; i++) {
                textViewGenre[i].setText(movieInfo.getGenres().get(i).getGenreNm());
            }
        }

        if (movieInfo.getActors().size() < 3) {//배우 수가 3명 이하면
            for (int i = movieInfo.getActors().size(); i < 3; i++)
                LayoutActors[i].setVisibility(View.GONE);

            for (int i = 0; i < movieInfo.getActors().size(); i++)
                textViewActor[i].setText(movieInfo.getActors().get(i).getPeopleNm());
        } else {
            for (int i = 0; i < 3; i++) {
                textViewActor[i].setText(movieInfo.getActors().get(i).getPeopleNm());
            }
        }

    }

}
