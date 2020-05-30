package com.strong.qlu_studenthelper.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.navigation.NavigationView;
import com.strong.qlu_studenthelper.R;
import com.strong.qlu_studenthelper.course.CourseActivity;
import com.strong.qlu_studenthelper.fragment.NewsFragment;
import com.strong.qlu_studenthelper.fragment.ViewFragment;
import com.strong.qlu_studenthelper.score.ScoreActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends BaseActivity {

    private FragmentManager fragmentManager;
    private DrawerLayout mDrawerLayout;
    private FrameLayout contextFrameLayout;
    NavigationView navView;
    CircleImageView imageView;
    TextView sname;
    TextView sinfo;
    private FloatingActionsMenu floatingActionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        contextFrameLayout = findViewById(R.id.context_Fragment);
        floatingActionsMenu = findViewById(R.id.action_button);
        fragmentManager = getSupportFragmentManager();
        setSupportActionBar(toolbar);
        imageView = findViewById(R.id.icon_image_user);
        mDrawerLayout = findViewById(R.id.draw_layout);
        navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_left_bar_menu);
        }
        replaceFragment(new ViewFragment());
        navView.inflateHeaderView(R.layout.nav_header);
        View headerView = navView.getHeaderView(0);

        imageView = headerView.findViewById(R.id.icon_image_user);
        sname = headerView.findViewById(R.id.usename);
        sinfo = headerView.findViewById(R.id.mail);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);
            }
        });

        final FloatingActionButton booksearch = findViewById(R.id.action_book_search);
        booksearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionsMenu.collapse();
                        Intent intent4=new Intent(getApplication(), WebActivity.class);
                        intent4.putExtra("link","http://121.250.34.16:8056/sms/opac/search/showiphoneSearch.action");
                        startActivity(intent4);
            }
        });

        final FloatingActionButton quanjing = findViewById(R.id.quanjing);
        quanjing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionsMenu.collapse();
                                        Intent intent=new Intent(getApplication(), WebviewActivity.class);
                        intent.putExtra("URL","https://720yun.com/t/33c20mpkm1s?pano_id=476040&from=timeline&isappinstalled=0#scene_id=586572");
                        startActivity(intent);

            }
        });

        final FloatingActionButton sport = findViewById(R.id.sport);
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionsMenu.collapse();
                                        Intent intent2=new Intent(getApplication(), WebActivity.class);
                        intent2.putExtra("link","http://210.44.144.150/spims/login.do?method=index");
                        startActivity(intent2);
            }
        });
        final FloatingActionButton jiaowu = findViewById(R.id.jiaowu);
        jiaowu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionsMenu.collapse();
                       Intent intent3=new Intent(getApplication(), WebActivity.class);
                     intent3.putExtra("link","http://jwxt.qlu.edu.cn/");
                     startActivity(intent3);
            }
        });
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.nav_kechengbiao://课表
                        replaceFragment(2);

                        break;
                    case R.id.nav_score://课表
                        replaceFragment(6);

                        break;
                    case R.id.nav_tianqi:
                        replaceFragment(4);//天气

                        break;
                    case R.id.nva_location:
                        replaceFragment(3);

                        break;
                    case R.id.nav_setting://设置
                        toolbar.setTitle("首页");
                        replaceFragment(5);

                        break;
                    case R.id.nav_info://工大要闻
                        replaceFragment(1);
                        toolbar.setTitle("工大要闻");
                        break;


                }
                mDrawerLayout.closeDrawers();
                return true;

            }

        });


    }

    private void replaceFragment(int id) {
        switch (id) {
            case 1:
                replaceFragment(new NewsFragment());
                break;
            case 2: //课程表
                contextFrameLayout.setVisibility(View.VISIBLE);
                Intent intent2 = new Intent(getApplication(), CourseActivity.class);
                startActivity(intent2);
                break;
            case 3:
                Intent intent1 = new Intent(getApplication(), LocationMainActivity.class);
                startActivity(intent1);
                break;
            case 4:
                Intent intent = new Intent(HomeActivity.this, WeatherActivity.class);
                startActivity(intent);
                break;

            case 5:
                replaceFragment(new ViewFragment());
                break;
            case 6:
                Intent intent3 = new Intent(HomeActivity.this, ScoreActivity.class);
                startActivity(intent3);
                break;

        }
        mDrawerLayout.closeDrawers();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(intent);
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


    @Override
    protected void onResume() {

        super.onResume();
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        String name = preferences.getString("name", "点击头像登录哦！");
        Log.d("TAG", "onResume: " + name);
        String userdwmc = preferences.getString("userdwmc", "你确定不登录一下吗？亲！");
        sname.setText(userdwmc);
        sinfo.setText(name);
        if (preferences.getString("name", null) == null) {
            imageView.setImageResource(R.drawable.noneuser);
        } else imageView.setImageResource(R.drawable.studenthead);
    }

}
