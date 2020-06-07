package com.strong.qlu_studenthelper.score;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.strong.qlu_studenthelper.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ScoreActivity extends AppCompatActivity {
    String id;
    int lowScore=0;
    Boolean flag=false;
    String password;
    String mtaken;
    String taken;
    TextView scoreName;
    TextView scoreTime;
    Button buttonGetScore;
    Button buttonGetAll;
    NumberPicker year;
    String year2;
    NumberPicker xueqi;
    String Vyear;
    String Vxueqi;
    String getScore;
    ScoreAdapter adapter;
    TextView scoreNum;
    TextView scoreLow;
    RecyclerView recyclerView;
    LinearLayoutManager scoreLinear;
    List<Score> mScoreList=new ArrayList<>();
    LinearLayoutManager layoutManager;
    Score score=new Score();
    Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Log.d("TAG", "handleMessage: "+getScore);
                    dealMyScore(getScore);
                    break;
                default:
                    break;
            }
        }

    };
    @SuppressLint("ResourceAsColor")
    private void dealMyScore(String getScore) {
        Gson gson = new Gson();
        if (getScore.length() < 10) {
            Toast.makeText(ScoreActivity.this, "此时间没有成绩信息", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ScoreActivity.this, "查询成功！", Toast.LENGTH_SHORT).show();
           List<Score> scoreList = gson.fromJson(getScore, new TypeToken<List<Score>>() {
            }.getType());
            Collections.sort(scoreList, new Comparator<Score>() {
                @Override
                public int compare(Score o1, Score o2) {
                    return new Double(o1.getZcj()).compareTo(new Double(o2.getZcj()));
                }
            });
           mScoreList.clear();
            for (int i = 0; i < scoreList.size(); i++) {
                mScoreList.add(scoreList.get(i));
                if (Double.parseDouble(scoreList.get(i).getZcj())<60){
                    lowScore++;
                }
            }
            scoreName.setText("姓名:"+scoreList.get(0).getXm());
            scoreTime.setText(flag==false?"全部成绩":"学期:"+scoreList.get(0).getXqmc());
            scoreNum.setText("课程数量:"+String.valueOf(mScoreList.size()));
            scoreLow.setText("不及格数："+String.valueOf(lowScore));
            lowScore=0;
            recyclerView.setAdapter(adapter);
            recyclerView.setItemViewCacheSize(600);
            adapter.notifyDataSetChanged();

        }
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        SharedPreferences preferencesUser = getSharedPreferences("user", MODE_PRIVATE);
        id = preferencesUser.getString("id", "");
        password = preferencesUser.getString("password", "");
        gettaken();
        scoreNum=findViewById(R.id.score_num);
        scoreLow=findViewById(R.id.score_low);
        recyclerView=findViewById(R.id.score_recycle);
        scoreName=findViewById(R.id.score_name);
        scoreTime=findViewById(R.id.score_xqmc);
        buttonGetScore=findViewById(R.id.button_getScore);
        buttonGetAll=findViewById(R.id.button_getAllScore);
        year = findViewById(R.id.score_year);
        xueqi = findViewById(R.id.score_xueqi);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new ScoreAdapter(mScoreList, scoreTime,scoreName,scoreNum,scoreLow);
        recyclerView.setAdapter(adapter);
        SharedPreferences preferencesDate = getSharedPreferences("date", MODE_PRIVATE);
        Vyear = preferencesDate.getString("year", "2020");
        Vxueqi = preferencesDate.getString("xueqi", "1");
        year.setMaxValue(2025);
        year.setMinValue(2016);
        year.setValue(Integer.parseInt(Vyear));//无法将值设置在最大最小值之前
        xueqi.setMinValue(1);
        xueqi.setMaxValue(2);
        xueqi.setValue(Integer.parseInt(Vxueqi));
            buttonGetAll.setEnabled(false);
            buttonGetAll.setText("或重新进入");
            buttonGetScore.setEnabled(false);
            buttonGetScore.setText("连接网络中");

        buttonGetScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "1 onClick: ButtonGetScore");
                flag=true;
                getScore();
            }
        });
        buttonGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=false;
                getAllScore();
            }
        });
    }
    private void getAllScore() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://jwxt.qlu.edu.cn/app.do?method=getCjcx&xh=" + id + "&xnxqid=")
                            .header("token", mtaken)
                            .build();
                    Log.d("TAG", "5run: "+request.toString());
                    Response response = client.newCall(request).execute();
                    Log.d("TAG", "2run: ");
                    getScore = response.body().string();
                    Log.d("TAG", "4run: "+getScore.length());
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void getScore() {
        year2 = String.valueOf(year.getValue() + 1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://jwxt.qlu.edu.cn/app.do?method=getCjcx&xh=" + id + "&xnxqid=" + year.getValue() + "-" + year2 + "-" + xueqi.getValue())
                            .header("token", mtaken)
                            .build();
                    Log.d("TAG", "5run: "+request.toString());
                    Response response = client.newCall(request).execute();
                    Log.d("TAG", "2run: ");
                    getScore = response.body().string();
                    Log.d("TAG", "4run: "+getScore.length());
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    void gettaken(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request requesttoken = new Request.Builder()
                            .url("http://jwxt.qlu.edu.cn/app.do?method=authUser&xh=" + id + "&pwd=" + password)
                            .build();
                    Response response = null;
                    response = new OkHttpClient().newCall(requesttoken).execute();
                    taken = response.body().string();
                    JSONObject jsonObject = new JSONObject(taken);
                    mtaken = jsonObject.getString("token");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (id.equals("")  || password .equals("") ) {

                                buttonGetAll.setEnabled(false);
                                buttonGetAll.setText("未登录");
                                buttonGetScore.setEnabled(false);
                                buttonGetScore.setText("不可用");
                            }else if(mtaken.length()>5){
                                buttonGetAll.setEnabled(true);
                                buttonGetAll.setText("所有成绩");
                                buttonGetScore.setEnabled(true);
                                buttonGetScore.setText("查询");
                            }
                        }
                    });
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
