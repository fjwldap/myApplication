package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Arrays;

public class GenrePopup extends DialogFragment implements CompoundButton.OnCheckedChangeListener {
    private Button buttonApply;
    private Button buttonReset;
    private boolean[] types = new boolean[19];//체크된 장르
    private CheckBox[] cbs;//상세검색 장르 체크박스

    private ViewGroup rootview;
    MainActivity mainActivity;
    private Fragment fragment;
    private SetGenreIntf setGenreIntf;

    public static GenrePopup newInstance() {
        return new GenrePopup();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = (ViewGroup) inflater.inflate(R.layout.genre_popup, container, false);

        fragment = getActivity().getSupportFragmentManager().findFragmentByTag("genre");
        Bundle bundle = fragment.getArguments();
        types = bundle.getBooleanArray("types");//이전에 체크했던 장르 받아온다

        init();

        buttonApply.setOnClickListener(new View.OnClickListener() {//닫기
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putBooleanArray("types",types);//장르

                if (fragment != null) {
                    setGenreIntf.finish(types);//인터페이스 통해서 선택한 장르 전달
                    DialogFragment dialogFragment = (DialogFragment) fragment;
                    dialogFragment.dismiss();
                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 19; i++) {//장르 개수
                    if (cbs[i].isChecked()) {
                        types[i] = false;
                        cbs[i].setChecked(false);
                    }
                }
            }
        });
        return rootview;
    }

    public interface SetGenreIntf{
        void finish(boolean[] types);
    }

    public void setGenre(SetGenreIntf types)
    {
       setGenreIntf = types;
    }

    public void init(){
        buttonApply = (Button) rootview.findViewById(R.id.buttonApply);
        buttonReset = (Button) rootview.findViewById(R.id.buttonReset);
        cbs = new CheckBox[19];

        for (int i = 0; i < 19; i++) {
            int idArray = getResources().getIdentifier("checkBox" + (i + 1), "id", "com.example.myapplication");
            cbs[i] = (CheckBox) rootview.findViewById(idArray);

            if (types[i])//체크되어있는 장르
                cbs[i].setChecked(true);
            cbs[i].setOnCheckedChangeListener(this);
        }
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
    }
}
