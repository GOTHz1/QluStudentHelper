package com.strong.qlu_studenthelper.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.strong.qlu_studenthelper.R;
import com.strong.qlu_studenthelper.bean.Student;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private TextView inputId;
    private TextView inputPassword;
    private Button LoginButton;
    private Button tuichuButton;
    String myInfo;
    Student student=new Student();
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    LoginInfo(myInfo);
                    SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("name",student.getName());
                    editor.putString("userdwmc",student.getUserdwmc());
                    editor.commit();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputId=findViewById(R.id.id);
        inputPassword=findViewById(R.id.password);
        LoginButton=findViewById(R.id.button_get);
        tuichuButton=findViewById(R.id.tuichu);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login(String.valueOf(inputId.getText()),String.valueOf(inputPassword.getText()));
                Intent intent=new Intent(getApplication(),Logo.class);
                startActivity(intent);
            }
        });
        tuichuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("name",null);
                editor.putString("userdwmc",null);
                editor.commit();
                Intent intent=new Intent(getApplication(),Logo.class);
                startActivity(intent);
            }
        });

    }

    private void Login(final String id, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request=new Request.Builder()
                            .url("http://jwxt.qlu.edu.cn/app.do?method=authUser&xh="+ id +"&pwd="+ password)
                            .build();
                    Response response=new OkHttpClient().newCall(request).execute();
                    myInfo=response.body().string();
                    Log.d("TAG", "run: "+myInfo);
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private Student  LoginInfo(String myInfo) {
        try {
            JSONObject jsonObject=new JSONObject(myInfo);
            String flag=jsonObject.getString("flag");
            if (flag.equals("1")){
                String name=jsonObject.getString("userrealname");
                String userdwmc=jsonObject.getString("userdwmc");

                student.setName(name);
                student.setUserdwmc(userdwmc);
            }
            else {
                Toast.makeText(getApplication(),"验证不通过",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Log.e("TAG", "LoginInfo: ");
            e.printStackTrace();
        }
        return student;
    }
}

