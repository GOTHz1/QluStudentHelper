package com.strong.qlu_studenthelper.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.strong.qlu_studenthelper.R;

import java.util.ArrayList;
import java.util.List;

public class WebActivity extends BaseActivity {


    private WebView webView;
    ImageButton imageButton;

    private String newsLink;
    private String readNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        setTitle("新闻详情");

        findViewById(R.id.image_sure_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share(newsLink);
            }
        });
        newsLink = getIntent().getExtras().getString("link");
        initView();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Log.d("TAG1","地址为："+newsLink);
    }

    private void share(String content) {
        try {

            Intent intent = new Intent(Intent.ACTION_SEND);
            //intent.setType("text/plain");
            intent.setType("text/plain");
            // 查询所有可以分享的Activity
            List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (!resInfo.isEmpty()) {
                List<Intent> targetedShareIntents = new ArrayList<Intent>();
                for (ResolveInfo info : resInfo) {
                    Intent targeted = new Intent(Intent.ACTION_SEND);
                    targeted.setType("text/plain");
                    ActivityInfo activityInfo = info.activityInfo;
                    Log.v("logcat", "packageName=" + activityInfo.packageName + "Name=" + activityInfo.name); // 分享出去的内容
                    targeted.putExtra(Intent.EXTRA_TEXT, content); // 分享出去的标题
                    targeted.putExtra(Intent.EXTRA_SUBJECT, "主题");
                    targeted.setPackage(activityInfo.packageName);
                    targeted.setClassName(activityInfo.packageName, info.activityInfo.name);
                    PackageManager pm = getApplication().getPackageManager();
                    if (info.activityInfo.applicationInfo.loadLabel(pm).toString().equals("QQ")) {
                        targetedShareIntents.add(targeted);
                    }
                } // 选择分享时的标题
                if (targetedShareIntents.size() == 0) {
                    Toast.makeText(this, "找不到可以分享的应用!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "选择分享");
                if (chooserIntent == null) {
                    return;
                }
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
                startActivity(chooserIntent);

            }
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "找不到可以分享的应用!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        imageButton=findViewById(R.id.head_back);
        webView =  findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBlockNetworkImage(false);
        Log.d("TAG", newsLink);
        webView.loadUrl(newsLink);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
