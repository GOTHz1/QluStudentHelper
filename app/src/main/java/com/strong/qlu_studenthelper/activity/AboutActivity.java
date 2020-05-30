package com.strong.qlu_studenthelper.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.strong.qlu_studenthelper.R;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription("齐鲁工业大学\n计算机科学与技术学院\n2020届毕业生の毕业设计")
                .setImage(R.drawable.logolongpng250)
                .addGroup("关于项目")
                .addWebsite("https://github.com/GOTHz1/QluStudentHelper","托管于GitHub")
                .addGroup("作者信息：")
                .addItem(chatQQ())
                .addEmail("gothz1@outlook.com","邮箱")
                .addWebsite("19960218.xyz","Blog")
                .addGitHub("GOTHz1","GitHub")
                .addItem(new Element().setTitle("Version 1.0").setGravity(Gravity.CENTER))
                .addItem(getCopyRightsElement())
                .create();
    setContentView(aboutPage);


    }
    Element chatQQ() {
        Element copyRightsElement = new Element();
        copyRightsElement.setTitle("QQ");
        copyRightsElement.setIconDrawable(R.drawable.qq);
        copyRightsElement.setGravity(Gravity.LEFT);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qqUrl = "mqqwpa://im/chat?chat_type=wpa&version=1&uin=1337709506";//uin是发送过去的qq号码
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return copyRightsElement;
    }
    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights ="Copyrights © 2019-"+ Calendar.getInstance().get(Calendar.YEAR);
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.copyright);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }



}
