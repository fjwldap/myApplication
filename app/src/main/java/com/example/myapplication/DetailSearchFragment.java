package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    private SearchView searchView;
    private String word = "";

    private CheckBox checkYear;
    private Spinner spinnerYearStart;
    private Spinner spinnerYearEnd;
    private String selectedYearStart = "2020";
    private String selectedYearEnd = "2020";

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
        rootview = (ViewGroup) inflater.inflate(R.layout.fragment_detailsearch, container, false);

        init();

        return rootview;
    }

    private void init() {
//        linearSearch = (LinearLayout) rootview.findViewById(R.id.LinearSearch);
        searchView = (SearchView) rootview.findViewById(R.id.searchView);

        checkYear = (CheckBox) rootview.findViewById(R.id.checkBoxYear);
        spinnerYearStart = (Spinner) rootview.findViewById(R.id.spinnerYearStart);
        spinnerYearStart.setEnabled(false);
        spinnerYearEnd = (Spinner) rootview.findViewById(R.id.spinnerYearEnd);
        spinnerYearEnd.setEnabled(false);

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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {//검색 버튼 누를때 - 연도, 단어 전달
                word = query;

                Bundle result = new Bundle();
                result.putString("word",word);//검색단어
                result.putBoolean("yearCheck",checkYear.isChecked());
                result.putString("yearStart",selectedYearStart);//연도시작
                result.putString("yearEnd",selectedYearEnd);//연도끝

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