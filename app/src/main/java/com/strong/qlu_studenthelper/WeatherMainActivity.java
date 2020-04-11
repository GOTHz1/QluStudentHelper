package com.strong.qlu_studenthelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

public class WeatherMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.actvity_weathermain);

        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getString("weather",null)!=null)
        {
            Intent intent=new Intent(this,WeatherActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
