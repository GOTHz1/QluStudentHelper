package com.strong.qlu_studenthelper.location.choose;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.strong.qlu_studenthelper.R;

/**
 *弃用
 */
public class ChoosePlace {
    private static String[] placeNames = {
            ""
    };
    /*
    使用AutoCompleteTextView进行动态匹配
    输入内容
     */
    public static AutoCompleteTextView setAutoCompleteTextView(AutoCompleteTextView autoCompleteTextView , Activity activity){
        //创建ArrayAdapter封装数组
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_multiple_choice,placeNames);

        autoCompleteTextView = activity.findViewById(R.id.auto);
        //设置adapter
        autoCompleteTextView.setAdapter(adapter);
        return autoCompleteTextView;
    }
}
