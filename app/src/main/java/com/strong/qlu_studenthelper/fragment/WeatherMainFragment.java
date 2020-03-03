package com.strong.qlu_studenthelper.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.strong.qlu_studenthelper.R;


/**
 * A simple {@link Fragment} subclass.
 * 本打算用Fragment呈现天气，已经弃用
 */
public class WeatherMainFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);

        View view = inflater.inflate(R.layout.actvity_weathermain,container,false);

        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(getContext());
        if(prefs.getString("weather",null)!=null)
        {
            getFragmentManager().beginTransaction().replace(getId(),new WeatherFragment()).commit();

           // Intent intent=new Intent(getActivity(), WeatherActivity.class);
           // startActivity(intent);
        }
        return view;
    }


}
