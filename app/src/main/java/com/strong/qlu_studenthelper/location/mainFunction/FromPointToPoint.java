package com.strong.qlu_studenthelper.location.mainFunction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.strong.qlu_studenthelper.R;
import com.strong.qlu_studenthelper.location.schoolgate.MyGraph;

public class FromPointToPoint extends Activity {
    
    final String[] items=new String[]{
            "南门（正门）",
            "机电楼",
            "实验楼",
            "图书馆",
            "美术馆",
            "食工楼",
            "一餐",
            "体育场东",
            "15-19学生公寓，体育场北",
            "三餐",
            "二餐",
            "1号公教楼",
            "2号公交楼",
            "3号公交楼",
            "20-25学生公寓",
    };
    
    private int arrayIndexFrom=0,arrayIndexTo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fp2p);

        final TextView textView=findViewById(R.id.text1);
        final TextView textView2=findViewById(R.id.text2);

        Button button=findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(FromPointToPoint.this);
                builder.setTitle("选择出发点");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrayIndexFrom=which+1;
                        textView.setText(items[which]);
                    }
                });
                builder.create().show();
            }
        });

        Button button2=findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(FromPointToPoint.this);
                builder.setTitle("选择目的地");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrayIndexTo=which+1;
                        textView2.setText(items[which]);
                    }
                });
                builder.create().show();
            }
        });

        Button button3=findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayIndexFrom==arrayIndexTo){
                    Toast.makeText(FromPointToPoint.this,"出发点与目的地不能相同",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent();
                intent.putExtra("indexFrom",MyGraph.points[arrayIndexFrom].index);
                intent.putExtra("indexTo", MyGraph.points[arrayIndexTo].index);
                setResult(0x1,intent);
                finish();
            }
        });

        Button button4=findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
