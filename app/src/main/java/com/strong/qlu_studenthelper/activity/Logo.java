package com.strong.qlu_studenthelper.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import com.strong.qlu_studenthelper.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Logo extends Activity {
    String viewTextjson;
    String mText;
    String flag;
    Handler handler = new Handler() {
        @SuppressLint("ResourceAsColor")
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    SharedPreferences preferences = getSharedPreferences("viewtext", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("text", mText);
                    editor.putString("flag", flag);
                    editor.commit();
                    Intent intent = new Intent(Logo.this, HomeActivity.class);
                    startActivity(intent);
                    Logo.this.finish();
                    finish();
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_logo);
        getMessage();

    }

    void getMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS).build();
                Request request = new Request.Builder().url("https://19960218.xyz/android/mytext.json")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        SharedPreferences preferences = getSharedPreferences("viewtext", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("text", "");
                        editor.putString("flag", "0");
                        editor.commit();
                        Intent intent = new Intent(Logo.this, HomeActivity.class);
                        startActivity(intent);
                        Logo.this.finish();
                        finish();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        viewTextjson = response.body().string();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(viewTextjson);

                            mText = jsonObject.getString("text");
                            flag = jsonObject.getString("flag");
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }).start();

    }
}
