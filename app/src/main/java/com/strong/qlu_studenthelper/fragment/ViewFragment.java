package com.strong.qlu_studenthelper.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.strong.qlu_studenthelper.R;

public class ViewFragment extends Fragment {
TextView viewText;
String text;
String flag;
    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        viewText=view.findViewById(R.id.view_text);
        SharedPreferences preferences=getActivity().getSharedPreferences("viewtext", Context.MODE_PRIVATE);
        text=preferences.getString("text", "");
        flag=preferences.getString("flag","");
        Log.d("TAG", "onCreateView: Flag"+flag);
        if (flag.equals("1")){
            viewText.setTextSize(50);
            viewText.setText(text);

        }else {
            viewText.setText(R.string.info);
        }
        return view;
    }


}

