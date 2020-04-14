package com.strong.qlu_studenthelper.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.strong.qlu_studenthelper.R;
import com.strong.qlu_studenthelper.activity.WebActivity;
import com.strong.qlu_studenthelper.bean.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> mNewsList;
    Context mContext;

    public NewsAdapter(Context context, List<News> newsList) {
        mNewsList = newsList;
        mContext = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView NewsPic;
        TextView title;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.news_title_text);
        }
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {

        final News news = mNewsList.get(position);
        holder.title.setText(news.getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(news.getWebUrl()!=null){
                Intent intent=new Intent(mContext,WebActivity.class);
                intent.putExtra("link",news.getWebUrl());
                mContext.startActivity(intent);
                }
                else {
                    Toast.makeText(mContext,"下滑刷新哦！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }


}
