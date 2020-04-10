package com.strong.qlu_studenthelper.schoolgate;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.text.DecimalFormat;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class BottomAniHandler extends Handler {

    private CardView cardView;
    private TextView textView;

    public BottomAniHandler(CardView cardView, TextView textView) {
        this.cardView = cardView;
        this.textView = textView;
    }

    public void handleMessage(){
        textView.setText("距离 "+new DecimalFormat("#").format(MyGraph.getDistance())+" M");
        ObjectAnimator translationY = new ObjectAnimator().ofFloat(cardView,"translationY",200,0);
        ObjectAnimator _translationY = new ObjectAnimator().ofFloat(cardView,"translationY",0,200);
        translationY.setDuration(1000);
        _translationY.setDuration(1000);
        _translationY.setStartDelay(3000);
        cardView.setVisibility(View.VISIBLE);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(translationY,_translationY);
        animatorSet.start();
    }

    @Override
    public void publish(LogRecord record) {

    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }

    public void sendMessage() {
        handleMessage();
    }
}
