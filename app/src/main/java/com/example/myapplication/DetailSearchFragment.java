package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailSearchFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private Button buttonSDetail;//디테일창 보기
    private boolean showDetail = false;//디테일창 보이는지
    private LinearLayout linearSearch;//검색버튼
    private LinearLayout linearDetailSearch;//디테일창
    private SearchView searchView;

    private CheckBox checkYear;
    private Spinner spinnerYearStart;
    private Spinner spinnerYearEnd;
    private String selectedYearStart = "2020";
    private String selectedYearEnd = "2020";

    private Button buttonF5;
    private boolean[] types = new boolean[19];//체크된 장르
    private CheckBox[] cbs;//상세검색 장르 체크박스
    private String word = "사랑";

    private ViewGroup rootview;
    MainActivity mainActivity;

    public static DetailSearchFragment newInstance() {
        return new DetailSearchFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = (ViewGroup) inflater.inflate(R.layout.detailsearch, container, false);

        init();

        buttonSDetail.setOnClickListener(new View.OnClickListener() {//상세검색 보이기
            @Override
            public void onClick(View view) {
                LinearLayout.LayoutParams plControl = (LinearLayout.LayoutParams) linearSearch.getLayoutParams();
                if (showDetail == false) {//안보이면 보이게
                    plControl.topMargin = 0;
                    linearSearch.setLayoutParams(plControl);

                    linearDetailSearch.setVisibility(View.VISIBLE);
                    showDetail = true;
                } else {//보이면 안보이게
                    float density = getContext().getResources().getDisplayMetrics().density;
                    plControl.topMargin = Math.round((float) 90 * density);
                    linearSearch.setLayoutParams(plControl);

                    linearDetailSearch.setVisibility(View.GONE);
                    showDetail = false;
                }
            }
        });

        buttonF5.setOnClickListener(new View.OnClickListener() {//새로고침
            @Override
            public void onClick(View v) {
                //여기서 저기로 정보 전달 -검색이름, 상세조건장르순연도등
                Bundle result = new Bundle();
                result.putString("word",word);//검색단어
                result.putBoolean("yearCheck",checkYear.isChecked());
                result.putString("yearStart",selectedYearStart);//연도시작
                result.putString("yearEnd",selectedYearEnd);//연도끝
//                result.putString("sort",selectedSort);//정렬
                result.putBooleanArray("types",types);//장르

                Log.v("aa", "aa");
                getParentFragmentManager().setFragmentResult("searchDetailInfo",result);
            }
        });

        return rootview;
    }

    private void init() {
        buttonSDetail = (Button) rootview.findViewById(R.id.buttonSDetail);
        linearSearch = (LinearLayout) rootview.findViewById(R.id.LinearSearch);
        linearDetailSearch = (LinearLayout) rootview.findViewById(R.id.LinearLayoutDetail);
        searchView = (SearchView) rootview.findViewById(R.id.searchView);

        checkYear = (CheckBox) rootview.findViewById(R.id.checkBoxYear);
        spinnerYearStart = (Spinner) rootview.findViewById(R.id.spinnerYearStart);
        spinnerYearStart.setEnabled(false);
        spinnerYearEnd = (Spinner) rootview.findViewById(R.id.spinnerYearEnd);
        spinnerYearEnd.setEnabled(false);
        buttonF5 = (Button) rootview.findViewById(R.id.buttonF5);
        cbs = new CheckBox[19];

        spinnerYearStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYearStart = (String) spinnerYearStart.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerYearEnd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYearEnd = (String) spinnerYearEnd.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        for (int i = 0; i < 19; i++) {
            int idArray = getResources().getIdentifier("checkBox" + (i + 1), "id", "com.example.myapplication");
            cbs[i] = (CheckBox) rootview.findViewById(idArray);
            if (types[i])//체크되어있는 장르
                cbs[i].setChecked(true);
            cbs[i].setOnCheckedChangeListener(this);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {//검색 버튼 누를때
                word = query;

                Bundle result = new Bundle();
                result.putString("word",word);//검색단어

                getParentFragmentManager().setFragmentResult("searchWord",result);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                word = newText;

                return false;
            }
        });

        checkYear.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        for (int i = 0; i < 19; i++) {//장르 개수
            if (cbs[i].isChecked()) {
                types[i] = true;
            } else {
                types[i] = false;
            }
        }

        if(checkYear.isChecked())
        {
            spinnerYearStart.setEnabled(true);
            spinnerYearEnd.setEnabled(true);
        }
        else{
            spinnerYearStart.setEnabled(false);
            spinnerYearEnd.setEnabled(false);
        }
    }
}