package com.strong.qlu_studenthelper.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.strong.qlu_studenthelper.R;
import com.strong.qlu_studenthelper.weather.util.LogUtil;

public class Logo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_logo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Logo.this, HomeActivity.class);
                startActivity(intent);
                Logo.this.finish();
                finish();
                LogUtil.d("TAG","Logo");
            }
        }, 2200);
    }
}
