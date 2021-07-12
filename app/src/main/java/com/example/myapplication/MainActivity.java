package com.example.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.dto.DataMovie;
import com.example.myapplication.dto.MovieRank;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private String requestUrl;
    private String MYKEY = "9db1c08ae0d6c721bc3f57b4851d9f97";
    ArrayList<DataMovie> list;
    MovieRank rankList;
    ShowRankFragment rankFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNav);

        //처음화면은 평가한 리스트가 있는화면
        getSupportFragmentManager().beginTransaction().add(R.id.mainFrame, new ListFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.list_fragmentItem);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) { //item을 클릭시 id값을 가져와 FrameLayout에 fragment.xml띄우기
                    case R.id.change_fragmentItem:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new ListFragment()).commit();
                        break;
                    case R.id.rank_fragmentItem:
                        MyAsyncTask myAsyncTask = new MyAsyncTask();
                        myAsyncTask.execute();

                        rankFragment = new ShowRankFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, rankFragment).commit();
                        break;
                    case R.id.list_fragmentItem:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new ListFragment()).commit();
                        break;
                    case R.id.search_fragmentItem:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new SearchFragment()).commit();
                        break;
                    case R.id.setting_fragmentItem:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new ListFragment()).commit();
                        break;
                }
                return true;
            }
        });

    }

    public void replaceFragment(Fragment fragment) {//이페이지로 바꿔주는
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment).commit();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {//박스오피스를 보여주기 위해 왜 여기서했찌..
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다일로그 시작
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");//헐 팝업처럼 뜨네
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // 백그라운드 스레드에서 실행되는 코드입니다. 그러므로 doInBackground() 메서드 내에서 UI를 직접 제어하면 안됩니다.

            requestUrl = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.xml";
            requestUrl += "?key=";
            requestUrl += MYKEY;
            requestUrl += "&targetDt=";
            requestUrl += "20190301";

            try {//파싱
                URL url = new URL(requestUrl);
                InputStream is = url.openStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new InputStreamReader(is, "UTF-8"));

                String tag;
                int eventType = parser.getEventType();

                DataMovie data = null;
                boolean b_rank = false;
                boolean b_rankOldAndNew = false;
                boolean b_movieNm = false;
                boolean b_openDt = false;
                boolean b_audiAcc = false;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            list = new ArrayList<>();
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("dailyBoxOffice") && data != null) {
                                list.add(data);
                            }
                            break;
                        case XmlPullParser.START_TAG:
                            if (parser.getName().equals("dailyBoxOffice")) {
                                data = new DataMovie();
                            }
                            if (parser.getName().equals("rank")) b_rank = true;
                            if (parser.getName().equals("rankOldAndNew")) b_rankOldAndNew = true;
                            if (parser.getName().equals("movieNm")) b_movieNm = true;
                            if (parser.getName().equals("openDt")) b_openDt = true;
                            if (parser.getName().equals("audiAcc")) b_audiAcc = true;
                            break;
                        case XmlPullParser.TEXT:
                            if (b_rank) {
                                data.setRank(parser.getText());
                                b_rank = false;
                            } else if (b_rankOldAndNew) {
                                data.setRankOldAndNew(parser.getText());
                                b_rankOldAndNew = false;
                            } else if (b_movieNm) {
                                data.setName(parser.getText());
                                b_movieNm = false;
                            } else if (b_openDt) {
                                data.setDate(parser.getText());
                                b_openDt = false;
                            } else if (b_audiAcc) {
                                data.setAudiAcc(parser.getText());
                                b_audiAcc = false;
                            }
                            break;
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //여기에서 받아온값? 결과?를 추가하도록

            rankList = new MovieRank();
            rankList.setList(list);
            rankFragment.setList(rankList);//여기서 넘겨야하나...
            rankFragment.getData();//리사이클러뷰에 추가

            progressDialog.dismiss();//잠시기다려주세요 뜬 메시지 지운다
        }
    }
}