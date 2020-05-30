package com.strong.qlu_studenthelper.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.strong.qlu_studenthelper.R;
import com.strong.qlu_studenthelper.weather.gson.Weather;
import com.strong.qlu_studenthelper.weather.service.AutoUpdateService;
import com.strong.qlu_studenthelper.weather.util.HttpUtil;
import com.strong.qlu_studenthelper.weather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public SwipeRefreshLayout swipeRefresh;
    private Button drawerButton;
    String TAG = "TAG";
    private ScrollView weatherLayout;
    private TextView titleCity;
     private TextView updateTimeText;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView windDir;
    private TextView windSc;
    private TextView comfortText;
    private TextView windSpd;
    private TextView sportText;
    private  TextView currenthumText;
//    private ImageView bingPicImg;
    private String mWeatherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);

        weatherLayout = findViewById(R.id.weather_layout);
        titleCity = findViewById(R.id.title_city);

        updateTimeText = findViewById(R.id.time_update);
        degreeText = findViewById(R.id.degree_text);
        weatherInfoText = findViewById(R.id.weather_info_text);
        forecastLayout = findViewById(R.id.forecast_layout);
        windDir = findViewById(R.id.wind_dir_text);
        windSc = findViewById(R.id.win_sc_text);
        windSpd = findViewById(R.id.wind_spd_text);
        currenthumText=findViewById(R.id.currenthum);
        comfortText = findViewById(R.id.comfort_text);
        sportText = findViewById(R.id.sport_text);
        drawerButton=findViewById(R.id.navBtn);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        drawerLayout = findViewById(R.id.drawer_layout);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        if (weatherString != null) {
            // 有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.getHeWeather6().get(0).getBasicX().getCid();
            showWeatherInfo(weather);
        } else {
            // 无缓存时去服务器查询天气

            mWeatherId = getIntent().getStringExtra("weather_id");
            if(mWeatherId==null){
                mWeatherId="CN101120102";//默认值设为长清
            }
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId);
        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
            }
        });
        drawerButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.END);
            }
        });


    }

    /**
     * 根据天气id请求城市天气信息。
     */
    public void requestWeather(final String weatherId) {
        final String weatherUrl2 = "https://free-api.heweather.com/s6/weather?location=" + weatherId + "&key=5cfa71f0523045cbbc2a915848c89ad4";
        Log.d(TAG, weatherUrl2);
        HttpUtil.sendOkHttpRequest(weatherUrl2, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.getHeWeather6().get(0).getStatusX())) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            mWeatherId = weather.getHeWeather6().get(0).getBasicX().getCid();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
    }

    /**
     * 处理并展示Weather实体类中的数据。
     */
    private void showWeatherInfo(Weather weather) {
        String cityName = weather.getHeWeather6().get(0).getBasicX().getLocation();
        String updateTime = weather.getHeWeather6().get(0).getUpdate().getLoc();
        String degree = weather.getHeWeather6().get(0).getNowX().getTmp() + "℃";
        String weatherInfo = weather.getHeWeather6().get(0).getNowX().getCond_txt();
        String currenthum=weather.getHeWeather6().get(0).getNowX().getHum();
        titleCity.setText(cityName);
        updateTimeText.setText("最近更新      "+updateTime);
        degreeText.setText(degree);
        currenthumText.setText("相对湿度 "+currenthum);
        windSc.setText(weather.getHeWeather6().get(0).getNowX().getWind_sc());
        windDir.setText(weather.getHeWeather6().get(0).getNowX().getWind_dir());
        windSpd.setText(weather.getHeWeather6().get(0).getNowX().getWind_spd());
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (int i = 0; i < weather.getHeWeather6().get(0).getDaily_forecast().size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = view.findViewById(R.id.data_text);
            TextView infoText = view.findViewById(R.id.info_text);
            TextView maxText = view.findViewById(R.id.max_text);
            TextView minText = view.findViewById(R.id.min_text);
            dateText.setText(weather.getHeWeather6().get(0).getDaily_forecast().get(i).getDate());
            infoText.setText(weather.getHeWeather6().get(0).getDaily_forecast().get(i).getCond_txt_n());
            maxText.setText(weather.getHeWeather6().get(0).getDaily_forecast().get(i).getTmp_max());
            minText.setText(weather.getHeWeather6().get(0).getDaily_forecast().get(i).getTmp_min());
            forecastLayout.addView(view);
        }
        comfortText.setText("舒适度：" + weather.getHeWeather6().get(0).getLifestyle().get(0).getTxt());
        sportText.setText("运动指数：" + weather.getHeWeather6().get(0).getLifestyle().get(3).getTxt());
        weatherLayout.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }

}
