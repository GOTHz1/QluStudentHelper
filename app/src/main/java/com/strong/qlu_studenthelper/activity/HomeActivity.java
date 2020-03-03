package com.strong.qlu_studenthelper.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.strong.qlu_studenthelper.R;
import com.strong.qlu_studenthelper.WeatherMainActivity;
import com.strong.qlu_studenthelper.fragment.WeatherFragment;
import com.strong.qlu_studenthelper.fragment.WeatherMainFragment;

public class HomeActivity extends BaseActivity   {

    private WeatherFragment weatherFragment;
    private FragmentManager fragmentManager;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        fragmentManager = getSupportFragmentManager();
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.draw_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_left_bar_menu);
        }
        navView.setCheckedItem(R.id.nav_info);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_tianqi:
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
                if(weatherFragment==null) {
                    Intent intent=new Intent(HomeActivity.this, WeatherMainActivity.class);
                    Log.d("TAG", "为空replaceFragment: ");
                    startActivity(intent);

//                    WeatherMainFragment weatherMainFragment = new WeatherMainFragment();
//                    fm.beginTransaction().replace(R.id.main_home,weatherMainFragment).commit();
                }
                else {
                    Log.d("TAG", "不为空replaceFragment: ");
                transaction.show(weatherFragment);
            }
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
    public WeatherFragment getWeatherFragment() {
        return weatherFragment;
    }
}
