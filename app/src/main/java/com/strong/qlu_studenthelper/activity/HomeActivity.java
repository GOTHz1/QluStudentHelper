package com.strong.qlu_studenthelper.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.strong.qlu_studenthelper.R;

public class HomeActivity extends BaseActivity   {


    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout=findViewById(R.id.draw_layout);
        NavigationView navView=findViewById(R.id.nav_view);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_left_bar_menu);
        }
        navView.setCheckedItem(R.id.nav_info);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        //onNavigationItemSelected(item);

    }
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_tongzhi) {
//            // Handle the camera action
//
//        } else if (id == R.id.nav_kechengbiao) {
//
//        } else if (id == R.id.nav_tianqi) {
//
//        } else if (id == R.id.nav_setting) {
//
//        } else if (id == R.id.nva_location) {
//
//        }
//        //关闭Drawer
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.draw_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.back:
                onBackPressed();
                Toast.makeText(HomeActivity.this,"这是返回键",Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }
}
