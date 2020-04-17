package com.strong.qlu_studenthelper.location.schoolgate;

public class Point {
    public double latitude;
    public double longitude;
    public String name;
    public int index;

    public Point(){

    }

    public Point(double latitude, double longitude,String name,int index) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name=name;
        this.index=index;
    }
}
