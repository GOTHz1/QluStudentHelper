package com.strong.qlu_studenthelper.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.strong.qlu_studenthelper.R;
import com.strong.qlu_studenthelper.animation.TopAniHandlerHide;
import com.strong.qlu_studenthelper.animation.TopAniHandlerShow;
import com.strong.qlu_studenthelper.attractions.Attractions_ifo;
import com.strong.qlu_studenthelper.attractions.Scenic;
import com.strong.qlu_studenthelper.choose.ChoosePlace;
import com.strong.qlu_studenthelper.imageProcessing.ImageProcessing;
import com.strong.qlu_studenthelper.mainFunction.FromPointToPoint;
import com.strong.qlu_studenthelper.position.LocateAnimation;
import com.strong.qlu_studenthelper.position.LocationSetter;
import com.strong.qlu_studenthelper.position.MyLocationListener;
import com.strong.qlu_studenthelper.position.MyOrientationListener;
import com.strong.qlu_studenthelper.position.ReloacateAnimationForAll;
import com.strong.qlu_studenthelper.schoolgate.BottomAniHandler;
import com.strong.qlu_studenthelper.schoolgate.Draw;
import com.strong.qlu_studenthelper.schoolgate.DrawGraph;
import com.strong.qlu_studenthelper.schoolgate.MyGraph;
import com.strong.qlu_studenthelper.schoolgate.Point;

import java.util.Arrays;


public class LocationMainActivity extends AppCompatActivity {
    /**
     * 手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
     */
    private float x1 = 0;
    private float x2 = 0;
    private float y1 = 0;
    private float y2 = 0;

    public static LocationMainActivity locationMainActivity;
    //定位服务
    public LocationSetter locationSetter;
    //当前方向信息
    public MyOrientationListener myOrientationListener;

    private MapView mMapView = null;

    public BaiduMap mBaiduMap;

    private static AutoCompleteTextView autoCompleteTextView;

    LocationClient locationClient;

    private SensorManager manager;

    private MySensorEventListener listener;

    private Sensor magneticSensor, accelerometerSensor;

    public float[] values, r, gravity, geomagnetic;
    /**
     * 地点信息
     */
    public View view;

    private FloatingActionsMenu floatingActionsMenu;


    private float azimuth;

    public static boolean flag = false;

    private static boolean threadFlag = false;

    private boolean flag2=false;

    private static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if((Integer)msg.obj==0){
                String destination =LocationMainActivity.autoCompleteTextView.getText().toString();//获得用户目的地
                localPositionNavigation(LocationMainActivity.locationMainActivity.mBaiduMap,
                        destination,LocationMainActivity.locationMainActivity.locationSetter);
            }
        }
    };

    private static Thread thread = new Thread(){//该线程用于导航是
        @Override                //实时更新用户位置信息
        public void run() {
            while (true){
                if(threadFlag == true){
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }Log.v("myTag","tsdfsdfdsfsdv");
                    Message message = handler.obtainMessage();
                    message.obj = 0;
                    handler.sendMessage(message);
                }else {

                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.BD09LL);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity_main);

        //请求GPS权限
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        listener = new MySensorEventListener();
        //获取Sensor
        magneticSensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometerSensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //初始化数组
        values = new float[3];//用来保存最终的结果
        gravity = new float[3];//用来保存加速度传感器的值
        r = new float[9];//
        geomagnetic = new float[3];//用来保存地磁传感器的值

        myOrientationListener = new MyOrientationListener(getApplicationContext());
        mMapView  = findViewById(R.id.bmapView);
        Toast.makeText(LocationMainActivity.this,mBaiduMap+"",Toast.LENGTH_SHORT).show();
        mBaiduMap = mMapView.getMap();

        locationSetter =new LocationSetter(mBaiduMap,azimuth);
        //初始化定位更新
        initLocationOption();
        new LocateAnimation(locationSetter,mBaiduMap).start();
        floatingActionsMenu = findViewById(R.id.multiple_actions);
        autoCompleteTextView = findViewById(R.id.auto);
        //设置自动匹配搜索文本框
        autoCompleteTextView = ChoosePlace.setAutoCompleteTextView(autoCompleteTextView,this );
        locationMainActivity = LocationMainActivity.this;

        /*
        设置三个悬浮按钮的监听事件
         */
        final CardView cardView=findViewById(R.id.cardTop);
        final FloatingActionButton actionC = findViewById(R.id.action_c);
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionsMenu.collapse();
                startActivityForResult(new Intent(LocationMainActivity.this, FromPointToPoint.class),0x1);

                if(flag2) {
                    cardView.setClickable(false);
                    flag2 = false;
                    mBaiduMap.clear();
                    new TopAniHandlerHide(cardView).sendMessage();
                }
            }
        });

        final FloatingActionButton actionB = findViewById(R.id.action_b);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionsMenu.collapse();
                //overlook -59.0 rotation 105.0 zoom 18.134295 lat 36.56454845994154 lng 116.80536759271781
                new ReloacateAnimationForAll(mBaiduMap,-59.0f,105.0f,18.134295f,36.5589614800,116.8179893400).start();
                mBaiduMap.clear();
//                Draw.drawLines(mBaiduMap,MyGraph.edges);
                Draw.drawPoints(mBaiduMap,Arrays.copyOfRange(MyGraph.points,1,20));

                flag2=true;
                CardView cardView=findViewById(R.id.cardTop);
                cardView.setClickable(true);
                new TopAniHandlerShow(cardView).sendMessage();
            }
        });

        final FloatingActionButton actionA = findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionsMenu.collapse();
                new ReloacateAnimationForAll(mBaiduMap,0,0,18.5f,mBaiduMap.getLocationData().latitude,mBaiduMap.getLocationData().longitude).start();

                if(flag2) {
                    cardView.setClickable(false);
                    flag2 = false;
                    mBaiduMap.clear();
                    new TopAniHandlerHide(cardView).sendMessage();
                }
            }
        });
        /*
        地图点击事件
         */
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                floatingActionsMenu.collapse();
                if(!flag)return;
                flag=false;
                locationMainActivity.findViewById(R.id.card).setVisibility(View.INVISIBLE);
                new ReloacateAnimationForAll(mBaiduMap,-59.0f,105.0f,18.134295f,36.56454845994154,116.80536759271781).start();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                if(!flag)return false;
                flag=false;
                locationMainActivity.findViewById(R.id.card).setVisibility(View.INVISIBLE);
                new ReloacateAnimationForAll(mBaiduMap,-59.0f,105.0f,18.134295f,36.56454845994154,116.80536759271781).start();
                return false;
            }
        });
        /*
        地图标记点击事件
         */
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onMarkerClick(Marker marker){

                CardView cardView=locationMainActivity.findViewById(R.id.card);
                ScrollView scrollView = cardView.findViewById(R.id.attraction_scroll);
                scrollView.getLayoutParams().height = locationMainActivity.getWindowManager().getDefaultDisplay().getHeight()/3;
                ImageView imageView   = cardView.findViewById(R.id.attraction_image);
                TextView textView02   = cardView.findViewById(R.id.whole_ifo);
                TextView textView     = cardView.findViewById(R.id.attraction_name);
                imageView.getLayoutParams().width=imageView.getLayoutParams().height=locationMainActivity.getWindowManager().getDefaultDisplay().getWidth()/3;

                for (Scenic i: Attractions_ifo.Attractions_ifo){
                    if (i.getAttractionsPoint().longitude==marker.getPosition().longitude &&
                            i.getAttractionsPoint().latitude==marker.getPosition().latitude ){

                        imageView.setImageBitmap(ImageProcessing.ChangeXY(i,locationMainActivity));
                        textView.setText("景点名：" + i.getName() +  "\n\n" + "所在位置：" + i.getAttractionsPoint().index);
                        textView02.setText(i.getIntroduce());
                        break;
                    }
                }
                if (textView.getText().toString().equals("")){
                    Bitmap bitmap = ImageProcessing.ChangeXY(new Scenic(R.drawable.zhulou,"","","",
                            new Point(0,0,"",0)),locationMainActivity);
                    imageView.setImageBitmap(bitmap);
                    textView.setText("黑龙江大学知名地标\n区域未录入");
                    textView02.setText("暂无详细信息，欢迎小伙伴投稿\n投稿邮箱地址：814484626@qq.com");
                }
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                flag=true;
                new ReloacateAnimationForAll(mBaiduMap,0,105.0f,18.5f,marker.getPosition().latitude-0.0003882285675,marker.getPosition().longitude+0.0025).start();
                findViewById(R.id.card).setVisibility(View.VISIBLE);
                return true;
            }
        });

        /*
        顶部搜索
         */
        final ImageButton buttonSearch = findViewById(R.id.search);
        //修改图标大小
        Bitmap bitmap01 = BitmapFactory.decodeResource(locationMainActivity.getResources(),R.drawable.search);
        bitmap01 = ImageProcessing.ChangBitmapSize(bitmap01,10,10);
        buttonSearch.setImageBitmap(bitmap01);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag2) {
                    cardView.setClickable(false);
                    flag2 = false;
                    mBaiduMap.clear();
                    new TopAniHandlerHide(cardView).sendMessage();
                }

                CardView cardView=findViewById(R.id.navigation_button);
                ((TextView)findViewById(R.id.navigation_textTop)).setText("点击开启导航");
                new TopAniHandlerShow(cardView).sendMessage();
                cardView.setClickable(true);

                String destination = autoCompleteTextView.getText().toString();//获得用户目的地
                localPositionNavigation(mBaiduMap,destination,locationSetter);//调用实时定位实现方法
                if(threadFlag){
                    threadFlag = false;
                }
            }
        });
        /*
        底部导航按钮点击事件
         */
        CardView navigationButton = LocationMainActivity.locationMainActivity.findViewById(R.id.navigation_button);
        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!threadFlag){
                    CardView cardView=findViewById(R.id.navigation_button);
                    ((TextView)findViewById(R.id.navigation_textTop)).setText("点击停止导航");
                    cardView.setClickable(true);
                    threadFlag = true;
                    thread.start();
                    Toast.makeText(LocationMainActivity.locationMainActivity,"自动导航已经开启",Toast.LENGTH_SHORT).show();
                }else {
                    CardView cardView=findViewById(R.id.navigation_button);
                    cardView.setClickable(false);
                    new TopAniHandlerHide(cardView).sendMessage();
                    threadFlag = false;
                    Toast.makeText(LocationMainActivity.locationMainActivity,"跟随导航已经停止",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //给搜索框下方关闭介绍模式按钮，设置监听器
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setClickable(false);
                flag2=false;
                mBaiduMap.clear();
                new TopAniHandlerHide(cardView).sendMessage();
                findViewById(R.id.card).setVisibility(View.INVISIBLE);
            }
        });

        Toast.makeText(LocationMainActivity.this,"亲~ 记得打开数据和GPS呦~",Toast.LENGTH_SHORT).show();
    }

    /**
     * 获得用户个人定位
     */
    private void initLocationOption() {
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        locationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类实例并配置定位参数
        MyLocationListener myLocationListener = new MyLocationListener(locationSetter,mBaiduMap,locationClient);
        //注册监听函数
        locationClient.registerLocationListener(myLocationListener);
        LocationClientOption locationOption = new LocationClientOption();
        //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("bd09ll");
        locationOption.setOpenGps(true);
        locationOption.setLocationNotify(true);
        locationClient.setLocOption(locationOption);
        //开始定位
        locationClient.start();
    }

    /**
     * 结束上一活动回到本活动
     * @param requestCode
     * @param resultCode
     * @param twoPoints
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent twoPoints) {
        super.onActivityResult(requestCode, resultCode, twoPoints);
        if (requestCode != resultCode) return;
        DrawGraph drawGraph = new DrawGraph(mBaiduMap, twoPoints);
        drawGraph.start();

        while (drawGraph.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        CardView cardView = findViewById(R.id.distance_cardView);
        TextView textView = findViewById(R.id.text);
        BottomAniHandler handler = new BottomAniHandler(cardView, textView);
        handler.sendMessage();
    }

    private class MySensorEventListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                geomagnetic = event.values;
            }
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                gravity = event.values;
                getValue();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(listener, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
        manager.registerListener(listener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        manager.unregisterListener(listener);
        super.onPause();
    }

    public void getValue() {
        // r从这里返回
        SensorManager.getRotationMatrix(r, null, gravity, geomagnetic);
        //values从这里返回
        SensorManager.getOrientation(r, values);
        //提取数据
        azimuth = (float) Math.toDegrees(values[0]);
        if (azimuth<0) {
            azimuth=azimuth+360;
        }
        double pitch = Math.toDegrees(values[1]);
        double roll = Math.toDegrees(values[2]);
        locationSetter.setMx(values[0]);
        Log.v("qwe",String.valueOf(azimuth));
        locationSetter.setMx(azimuth);

        try {
            setMapLocationData();
        }catch (Exception e){
            new Thread(){
                @Override
                public void run() {
                    try {
                        double latitude = mBaiduMap.getLocationData().latitude;//用于检测是否成功获得位置信息 防止奔溃
                    }catch (Exception e){
                        initLocationOption();
                        try {
                            setMapLocationData();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }

    /**
     * 开启定位图层
     * @throws Exception
     */
    private void setMapLocationData() throws Exception{
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(50)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(azimuth)
                .latitude(mBaiduMap.getLocationData().latitude)
                .longitude(mBaiduMap.getLocationData().longitude).build();
        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);
        throw new Exception();
    }

    /*
    当前位置导航实现方法
     */
    private static void localPositionNavigation(BaiduMap mBaiduMap, String destination ,
                                                LocationSetter locationSetter){
        InputMethodManager imm = (InputMethodManager) LocationMainActivity.locationMainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(LocationMainActivity.locationMainActivity.getWindow().getDecorView().getWindowToken(), 0);

        int endPlace = 0;
        for (int i = 0; i < MyGraph.points.length ; i++){
            if (destination.equals(MyGraph.points[i].name) ){
                endPlace = MyGraph.points[i].index;
            }
        }
        DrawGraph drawGraph = new DrawGraph(mBaiduMap,locationSetter.getLocation(),endPlace);
        drawGraph.start();
        while(drawGraph.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        CardView cardView=locationMainActivity.findViewById(R.id.distance_cardView);
        TextView textView=locationMainActivity.findViewById(R.id.text);
        BottomAniHandler handler=new BottomAniHandler(cardView,textView);
        handler.sendMessage();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // Toast.makeText(MainActivity.this, "slipe", Toast.LENGTH_SHORT).show();
        // 继承了Activity的onTouchEvent方法，直接监听点击事件
        switch (ev.getAction() ) {
            case MotionEvent.ACTION_DOWN:
                //当手指按下的时候
                x1 = ev.getX();
                y1 = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                //当手指离开的时候
                x2 = ev.getX();
                y2 = ev.getY();
                if(y1 - y2 > 50) {
                    floatingActionsMenu.animate().translationY(-300).setDuration(500);
                } else if(y2 - y1 > 50) {
                    floatingActionsMenu.animate().translationY(0).setDuration(500);
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
