package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.dto.DataMovie;
import com.example.myapplication.dto.MovieRank;

import java.util.ArrayList;

public class ShowRankFragment extends Fragment {
    private RecyclerAdapter adapter;
    MovieRank rankList;

    private ViewGroup rootview;
    MainActivity mainActivity;

    public static ShowRankFragment newInstance() {
        return new ShowRankFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = (ViewGroup) inflater.inflate(R.layout.fragment_boxoffice, container, false);

        init();

        return rootview;
    }

    private void init() {
        RecyclerView recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter(mainActivity);
        recyclerView.setAdapter(adapter);
    }

    public void setList(MovieRank rankList) {//박스오피스 list를 받아온다
        this.rankList = rankList;
    }

    public void getData() {//리사이클러뷰에 추가
        ArrayList<DataMovie> list = rankList.getList();
        for (DataMovie m : list) {
            adapter.addItem(m,true);
        }
        adapter.notifyDataSetChanged();
    }
}
