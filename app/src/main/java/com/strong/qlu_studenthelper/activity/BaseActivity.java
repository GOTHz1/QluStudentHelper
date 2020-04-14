package com.strong.qlu_studenthelper.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.strong.qlu_studenthelper.R;

public class BaseActivity extends AppCompatActivity {
    //this activity context
    protected Context mContext = null;

    //application context
    protected Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        context = getApplicationContext();
    }

    public void setTitle(String title) {
        TextView textView = findViewById(R.id.head_title);
        if (textView != null) {
            textView.setText(title == null ? getTitle() : title);//getTitle() 即是 manifest中声明的lable
        }
    }

    public void setTitle(int title) {
        TextView textView = findViewById(R.id.head_title);
        if (textView != null) {
            textView.setText(title == 0 ? getTitle() : getString(title));
        }
    }


}
