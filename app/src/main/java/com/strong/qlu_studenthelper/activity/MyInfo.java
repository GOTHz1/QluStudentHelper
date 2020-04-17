package com.strong.qlu_studenthelper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.strong.qlu_studenthelper.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyInfo extends AppCompatActivity {
    Button buttonGet;
    TextView textView;
    EditText id;
    EditText password;
    String mid;
    String mpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        buttonGet=findViewById(R.id.button_get);
        textView=findViewById(R.id.text_myinfo);
        id=findViewById(R.id.id);
        password=findViewById(R.id.password);

        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mid= String.valueOf(id.getText());
                mpassword=String.valueOf(password.getText());
                requestMyInfo(mid,mpassword);
            }
        });
    }
    String myInfo;
    private void requestMyInfo(final String mid, final String mpassword) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request=new Request.Builder()
                            .url("http://jwxt.qlu.edu.cn/app.do?method=authUser&xh="+ mid +"&pwd="+ mpassword)
                            .build();

                    Response response=new OkHttpClient().newCall(request).execute();
                    myInfo=response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(myInfo);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();

    }
}
