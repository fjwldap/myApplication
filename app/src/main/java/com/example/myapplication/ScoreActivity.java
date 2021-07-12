package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.dto.mInfo.MovieInfo;

public class ScoreActivity extends AppCompatActivity {
    private String code;//저장할때 꼭필요해 영화코드
    private TextView textViewName;
    private Button buttonexitScore;
    private MovieInfo movieInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);

        init();

        Intent intent = getIntent();//저번페이지에서 받아옴
        String movieName = intent.getStringExtra("name");
        String movieDate = intent.getStringExtra("date");
        code = intent.getStringExtra("code");

            textViewName.setText(movieName);

        buttonexitScore.setOnClickListener(new View.OnClickListener() {//닫기
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        textViewName = (TextView) findViewById(R.id.movieName);
        buttonexitScore = (Button) findViewById(R.id.buttonExitScore);

    }
}
