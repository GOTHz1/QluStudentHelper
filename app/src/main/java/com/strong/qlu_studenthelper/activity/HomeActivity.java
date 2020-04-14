package com.strong.qlu_studenthelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.strong.qlu_studenthelper.R;
import com.strong.qlu_studenthelper.WeatherActivity;
import com.strong.qlu_studenthelper.fragment.NewsFragment;

public class HomeActivity extends BaseActivity   {

    private FragmentManager fragmentManager;
    private DrawerLayout mDrawerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        fragmentManager = getSupportFragmentManager();
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.draw_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_left_bar_menu);
        }
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_tianqi:
                        replaceFragment(4);

                        break;
                    case R.id.nva_location:
                        replaceFragment(3);

                        break;
                    case R.id.nav_setting:
                        replaceFragment(5);

                        break;
                    case R.id.nav_info:
                        replaceFragment(1);
                        toolbar.setTitle("工大要闻");
                        break;
                        case R.id.nav_kechengbiao:
                        replaceFragment(1);

                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;

            }
        });
    }
    private void replaceFragment(int id){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        FragmentManager fm = getSupportFragmentManager();
        switch (id){
            case 1:
               replaceFragment(new NewsFragment());
               break;
            case 4:
                    Intent intent=new Intent(HomeActivity.this, WeatherActivity.class);
                    startActivity(intent);
                break;
            case 3:
                Intent intent1=new Intent(getApplication(),LocationMainActivity.class);
                startActivity(intent1);
                break;
            case 5:
                break;
            case 2: //课程表
                break;
        }
        mDrawerLayout.closeDrawers();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.back:
                Toast.makeText(HomeActivity.this,"夜间模式",Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager
                .beginTransaction()
                .replace(R.id.context_Fragment, fragment);
        transaction.commit();
    }
//    public static void startWeb(String Url) {
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl(Url);
//    }
}
