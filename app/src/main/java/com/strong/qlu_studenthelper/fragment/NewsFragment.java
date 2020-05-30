package com.strong.qlu_studenthelper.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.strong.qlu_studenthelper.R;
import com.strong.qlu_studenthelper.adapter.NewsAdapter;
import com.strong.qlu_studenthelper.bean.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NewsFragment extends Fragment {
    private static String URL = "http://www.qlu.edu.cn/gdyw/list.htm";
    private static String imageUML;
    NewsAdapter adapter;
    Button buttonMain;
    FrameLayout frameLayout;
    static WebView webView;
    static String schoolUrl = "http://www.qlu.edu.cn";
    static List<News> newsList = new ArrayList<>();


    protected RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;


    public static void sent(String webUrl) {
        imageUML=webUrl;
    }
    Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:

                    getFragmentManager().beginTransaction().replace(R.id.context_Fragment, new NewsFragment()).commit();
                    Toast.makeText(getActivity(),"刷新成功",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = view.findViewById(R.id.list_view);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
//        if (newsList.size() == 14) {
//            isRefresh(swipeRefreshLayout);
//        }
        if(mifNewsList().size()<2) {
            Toast.makeText(getActivity(),"正在刷新",Toast.LENGTH_SHORT).show();
            getFreshNews();
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(),"正在刷新",Toast.LENGTH_SHORT).show();
                mifNewsList().clear();
                getFragmentManager().beginTransaction().replace(R.id.context_Fragment, new NewsFragment()).commit();
                Log.d("TAG", "数据大小" + String.valueOf(newsList.size()));
                Log.d("TAG", "数据大小" + String.valueOf(mifNewsList().size()));

            }
        });
        adapter = new NewsAdapter(getActivity(), mifNewsList());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }



    List<News> mifNewsList() {
        List<News> getnesList = new LinkedList<>();
        if (newsList.size() != 0) {
            getnesList = newsList.subList(0, 14);
        } else {
            News news2 = new News();
            news2.setTitle("正在刷新中");
            getnesList.add(news2);
            return getnesList;
        }
        return getnesList;
    }



    private void getFreshNews() {
        Log.d("TAG", "getFreshNews: ");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(URL).get();
                    Elements element = doc.select("div[class=col_news_list listcon]").select("li");
                    Log.d("TAG", "run: "+element.toString());
                    for (int i = 0; i < element.size(); i++) {
                        String title = element.get(i).getElementsByClass("news_title").text();
                        String Uml = element.get(i).getElementsByTag("a").attr("href");
                        String date=element.get(i).getElementsByClass("news_meta").text();
                        Log.d("TAG", "新闻名字：" + title);
                        Log.d("TAG", "数据:" + element.get(i).getElementsByTag("a").attr("href"));
                        Log.d("TAG", "数据:" + element.get(i).getElementsByClass("news_meta").text());
                        News news = new News();
                        news.setTitle(title);
                        news.setDade(date);
                        if (Uml.startsWith("http")) {
                            news.setWebUrl(Uml);
                        } else {
                            news.setWebUrl(schoolUrl + Uml);
                        }
                        if (newsList.size() < 14) {
                            newsList.add(news);
                        }
                    }

                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
