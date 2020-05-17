package com.strong.qlu_studenthelper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.strong.qlu_studenthelper.R;
import com.strong.qlu_studenthelper.activity.WebviewActivity;

public class ViewFragment extends Fragment {
    ImageButton imageButton1;
    ImageButton imageButton2;
    ImageButton imageButton3;
    ImageButton imageButton4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_view, container, false);
//         imageButton1=view.findViewById(R.id.shipaiButton);
//         imageButton1.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 Intent intent=new Intent();
//                 intent.putExtra("URL","https://720yun.com/t/33c20mpkm1s?pano_id=476040&from=timeline&isappinstalled=0#scene_id=586572");
//                 startActivity(new Intent(getActivity(), WebviewActivity.class));
//             }
//         });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageButton1= getActivity().findViewById(R.id.shipaiButton);
        imageButton2=getActivity().findViewById(R.id.jiaofeiButton);
        imageButton3=getActivity().findViewById(R.id.tiyuButton);
        imageButton4=getActivity().findViewById(R.id.kebiaoButton);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("URL","https://720yun.com/t/33c20mpkm1s?pano_id=476040&from=timeline&isappinstalled=0#scene_id=586572");
                startActivity(intent);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("URL","https://qlgydx.mp.sinojy.cn");
                startActivity(intent);
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("URL","http://210.44.144.150/spims/login.do?method=index");
                startActivity(intent);
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("URL","http://jwxt.qlu.edu.cn/");
                startActivity(intent);
            }
        });
    }
}

