package com.strong.qlu_studenthelper.score;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.strong.qlu_studenthelper.R;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    private  View context;
    private List<Score> mScoreList;
    TextView mscoreName;
    TextView mScoreTime;
    TextView mScoreNum;
    TextView mScoreLow;
    public ScoreAdapter(List<Score> scoreList, TextView scoreTime){
        mScoreList=scoreList;
        mScoreTime=scoreTime;
    }

    public ScoreAdapter(List<Score> mScoreList, TextView scoreTime, TextView scoreName, TextView scoreNum, TextView scoreLow) {
        this.mScoreList=mScoreList;
        mscoreName=scoreName;
        mScoreTime=scoreTime;
        mScoreNum=scoreNum;
        mScoreLow=scoreLow;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ViewHolder viewHolder=holder;
        final Score score=mScoreList.get(position);

        holder.kcmcText.setText(score.getKcmc());
        holder.zcjText.setText(score.getZcj());
        holder.xfText.setText(String.valueOf(score.getXf()));
        holder.kcxzmcText.setText(score.getKcxzmc());
        holder.zcjText.setText(String.valueOf(score.getZcj()));
        holder.ksxzmcText.setText(score.getKsxzmc().substring(0,1).equals("正")?"":"*"+score.getKsxzmc().substring(0,2));
        if(Double.parseDouble(score.getZcj())<60.0){
            holder.itemView.findViewById(R.id.score_item_card_view).setBackgroundColor(R.color.red_a700);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScoreTime.setText(score.getXqmc());
                mscoreName.setText(score.getKcmc());
                mScoreNum.setText(score.getKcxzmc()+" 学分:"+score.getXf());
                mScoreLow.setText(score.getKsxzmc()+"成绩:"+score.getZcj());
            }
        });
    }
    @Override
    public int getItemCount() {
        return mScoreList.size();
    }
    static class  ViewHolder extends  RecyclerView.ViewHolder{
        TextView kcmcText;
        TextView kcxzmcText;
        TextView xfText;
        TextView zcjText;
        TextView ksxzmcText;
        public  ViewHolder(View view){
            super(view);
            kcmcText=view.findViewById(R.id.kcmc);
            kcxzmcText=view.findViewById(R.id.kcxzmc);
            xfText=view.findViewById(R.id.xf);
            zcjText=view.findViewById(R.id.zcj);
            ksxzmcText=view.findViewById(R.id.ksxzmc);
        }
    }
}
