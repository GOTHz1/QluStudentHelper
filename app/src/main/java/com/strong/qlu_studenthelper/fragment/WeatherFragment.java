package com.strong.qlu_studenthelper.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.strong.qlu_studenthelper.R;
import com.strong.qlu_studenthelper.weather.gson.Weather;
import com.strong.qlu_studenthelper.weather.service.AutoUpdateService;
import com.strong.qlu_studenthelper.weather.util.HttpUtil;
import com.strong.qlu_studenthelper.weather.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 * 本打算用Fragment呈现天气，已经弃用
 */
public class WeatherFragment extends Fragment {
    private View view;
    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    private ImageView bingPicImg;
    public SwipeRefreshLayout swipeRefresh;
    public DrawerLayout drawerLayout;
    private Button navButton;

    private String mWeatherId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);

        view = inflater.inflate(R.layout.activity_weather, container, false);

        return view;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        weatherLayout = (ScrollView) view.findViewById(R.id.weather_layout);
        titleCity = (TextView) view.findViewById(R.id.title_city);
        titleUpdateTime = (TextView) view.findViewById(R.id.title_update_time);
        degreeText = (TextView) view.findViewById(R.id.degree_text);
        weatherInfoText = (TextView) view.findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) view.findViewById(R.id.forecast_layout);
        comfortText = (TextView) view.findViewById(R.id.comfort_text);
        carWashText = (TextView) view.findViewById(R.id.car_wash_text);
        sportText = (TextView) view.findViewById(R.id.sport_text);
        bingPicImg = (ImageView) view.findViewById(R.id.bing_pic_img);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
//        navButton = (Button) view.findViewById(R.id.nav_button);
        swipeRefresh.setColorSchemeColors(R.color.colorPrimary);

        mWeatherId = "CN101120102";
        requestWeather(mWeatherId);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
            }
        });
        loadBingPic();

//        navButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
    }

    /**
     * 加载必应每日一图
     */
    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingpic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                editor.putString("bing_pic", bingpic);
                editor.apply();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getActivity()).load(bingpic).into(bingPicImg);
                    }
                });
            }
        });
    }

    /**
     * 根据天气id请求城市天气信息
     */
    public void requestWeather(String weatherId) {String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=58d20a93e7f64cfea9dfa0db52a04a24";
        Log.d(TAG, "weatherUrl: " + weatherUrl);
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"failure to request weather data",Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.getHeWeather6().get(0).getStatusX())) {
                            SharedPreferences.Editor editor = PreferenceManager
                                    .getDefaultSharedPreferences(getActivity())
                                    .edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                            Toast.makeText(getActivity(),
                                    "success to request weather data",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(),
                                    "failure to request weather data",Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });

        loadBingPic();
    }


    private void showWeatherInfo(Weather weather) {

        String cityName = weather.getHeWeather6().get(0).getBasicX().getLocation();
        String updateTime = weather.getHeWeather6().get(0).getUpdate().getLoc();
        String degree=weather.getHeWeather6().get(0).getNowX().getTmp()+"℃";
        String weatherInfo=weather.getHeWeather6().get(0).getNowX().getCond_txt();
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();

        for (int i=0;i<3;i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.data_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(weather.getHeWeather6().get(0).getDaily_forecast().get(i).getDate());
            infoText.setText(weather.getHeWeather6().get(0).getDaily_forecast().get(i).getCond_txt_n());
            maxText.setText(weather.getHeWeather6().get(0).getDaily_forecast().get(i).getTmp_max());
            minText.setText(weather.getHeWeather6().get(0).getDaily_forecast().get(i).getTmp_min());
            forecastLayout.addView(view);
        }


        comfortText.setText("舒适度："+weather.getHeWeather6().get(0).getLifestyle().get(0).getTxt());
        carWashText.setText("洗车指数："+weather.getHeWeather6().get(0).getLifestyle().get(6).getTxt());
        sportText.setText("运动指数："+weather.getHeWeather6().get(0).getLifestyle().get(3).getTxt());

        weatherLayout.setVisibility(View.VISIBLE);
        if (weather != null && "ok".equals(weather.getHeWeather6().get(0).getStatusX())) {
            Intent intent = new Intent(getActivity(), AutoUpdateService.class);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(),
                    "fail to request weather data",Toast.LENGTH_SHORT).show();
        }
    }
}
