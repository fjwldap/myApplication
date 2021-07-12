package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.dto.DataMovie;

public class ListFragment extends Fragment {
    private RecyclerAdapter adapter;
    private Button buttonGoSearch;//검색하러 가기

    private ViewGroup rootview;
    MainActivity mainActivity;

    public static ListFragment newInstance()
    {
        return new ListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = (ViewGroup)inflater.inflate(R.layout.fragment_list, container, false);
        getChildFragmentManager().beginTransaction().add(R.id.detailSearchFrame, new DetailSearchFragment()).commit();

        init();
        getData();

        buttonGoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.replaceFragment(SearchFragment.newInstance());//검색화면으로 간다
            }
        });

        return rootview;
    }

    private void getData() {
        //임시로 15줄 리사이클러뷰를 만들었다
        for(int i=0;i<15;i++)
        {
            DataMovie data = new DataMovie();
            data.setDate("20200202");
            adapter.addItem(data,true);
        }
        adapter.notifyDataSetChanged();
    }

    private void init(){
        RecyclerView recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerViewDB);
        buttonGoSearch = (Button)rootview.findViewById(R.id.buttonGoSearch);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter(mainActivity);
        recyclerView.setAdapter(adapter);

    }
}