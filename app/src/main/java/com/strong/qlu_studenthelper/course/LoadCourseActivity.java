package com.strong.qlu_studenthelper.course;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.strong.qlu_studenthelper.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoadCourseActivity extends AppCompatActivity {
    String getMyCourse;
    Button button;
    TextView textView;
    String id;
    String password;
    String taken;
    String zc;
    String xnxqh;
    String mtaken;
    NumberPicker year;
    String year2;
    NumberPicker zhou;
    NumberPicker xueqi;
    List<MyCourse> myCourseList = new ArrayList<>();
    List<Course> courseList = new ArrayList<>();
    private DatabaseHelper databaseHelper = new DatabaseHelper
            (this, "database.db", null, 2);
    Handler handler=new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case 1:
                    dealMyCourse(getMyCourse);
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_course);
        button = findViewById(R.id.button_load);
        textView=findViewById(R.id.warn_text);
        year=findViewById(R.id.year);
        xueqi=findViewById(R.id.xueqi);
        zhou=findViewById(R.id.zhou);
        year.setMaxValue(2025);
        year.setMinValue(2016);
        year.setValue(2020);

        xueqi.setValue(1);
        xueqi.setMinValue(1);
        xueqi.setMaxValue(2);

        zhou.setValue(1);
        zhou.setMinValue(1);
        zhou.setMaxValue(20);

        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        id = preferences.getString("id", "");
        password = preferences.getString("password", "");
        if(id==""||password==""){
            textView.setText("你还没有登录,请返回主界面登录");
            textView.setTextColor(this.getResources().getColor(R.color.red_700));
            button.setText("不可使用");
            button.setEnabled(false);
        }
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
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCourse(mtaken);
            }
        });


    }
    private void loadCourse(final String mtaken) {
        year2= String.valueOf(year.getValue()+1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://jwxt.qlu.edu.cn/app.do?method=getKbcxAzc&xh=" + id + "&xnxqid="+year.getValue()+"-"+year2+"-"+xueqi.getValue()+"&zc="+zhou.getValue())
                            .header("token", mtaken)
                            .build();
                    Response response = client.newCall(request).execute();
                    Log.d("TAG", "run: "+"http://jwxt.qlu.edu.cn/app.do?method=getKbcxAzc&xh=" + id + "&xnxqid="+year.getValue()+"-"+year2+"-"+xueqi.getValue()+"&zc="+zhou.getValue());
                    getMyCourse = response.body().string();
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private void loadmess(final String mtaken) {
        //加载当前周次，弃用
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    Request request1=new Request.Builder()
                            .url("http://jwxt.xxxx.edu.cn/app.do?method=getCurrentTime&currDate=2019-01-14")
                            .header("token", mtaken)
                            .build();

                    Response response1=client.newCall(request1).execute();


                    String message=response1.body().string();
                    JSONObject jsonObject = new JSONObject(message);
                    zc = jsonObject.getString("zc");
                    xnxqh=jsonObject.getString("xnxqh");
                    Log.d("TAG", "run:11 " + zc+xnxqh);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private void dealMyCourse(String getMyCourse) {
        Gson gson = new Gson();
        Log.d("TAG", "dealMyCourse: " + getMyCourse);
        if (getMyCourse.length()<10) {
            Toast.makeText(LoadCourseActivity.this,"此时间没有课程信息",Toast.LENGTH_SHORT).show();

        } else {
            List<MyCourse> myCourseList = gson.fromJson(getMyCourse, new TypeToken<List<MyCourse>>() {
            }.getType());
            for (MyCourse myCourse : myCourseList) {
                Course course = new Course();
                Log.d("TAG", "dealMyCourse: KCSJ" + myCourse.getKcsj());
                course.setCourseName(myCourse.getKcmc());
                course.setTeacher(myCourse.getJsxm());
                course.setClassRoom(myCourse.getJsmc());
                course.setDay(Integer.parseInt(myCourse.getKcsj().substring(0, 1)));
                course.setStart(Integer.parseInt(myCourse.getKcsj().substring(2, 3)));
                course.setEnd(Integer.parseInt(myCourse.getKcsj().substring(4)));
                Log.d("TAG", "dealMyCourse: +数据" + course.getCourseName() + course.getDay() + course.getStart() + course.getEnd());
                if (course.getEnd() < 13) {
                    courseList.add(course);
                }
            }
            saveList(courseList);
            Intent intent = new Intent(LoadCourseActivity.this, CourseActivity.class);
            this.finish();
            startActivity(intent);
        }
    }

    private void saveList(List<Course> courseList) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from courses");
        for (Course course : courseList) {
            sqLiteDatabase.execSQL
                    ("insert into courses(course_name, teacher, class_room, day, class_start, class_end) " + "values(?, ?, ?, ?, ?, ?)",
                            new String[]{course.getCourseName(),
                                    course.getTeacher(),
                                    course.getClassRoom(),
                                    course.getDay() + "",
                                    course.getStart() + "",
                                    course.getEnd() + ""}
                    );
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoadCourseActivity.this, CourseActivity.class);
        this.finish();
        startActivity(intent);
    }
}
