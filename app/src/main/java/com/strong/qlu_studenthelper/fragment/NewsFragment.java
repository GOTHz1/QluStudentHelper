package com.strong.qlu_studenthelper.fragment;


import android.os.Bundle;
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
        getFreshNews();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFragmentManager().beginTransaction().replace(R.id.context_Fragment, new NewsFragment()).commit();
                isRefresh(swipeRefreshLayout);
                Log.d("TAG", "数据大小" + String.valueOf(newsList.size()));
                Log.d("TAG", "数据大小" + String.valueOf(mifNewsList().size()));

            }
        });
        NewsAdapter adapter = new NewsAdapter(getActivity(), mifNewsList());
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
            news2.setTitle("注：第一次操作需要你手动刷新。\n下滑刷新！！！");
            getnesList.add(news2);
            return getnesList;
        }
        return getnesList;
    }

    void isRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        if (!swipeRefreshLayout.isRefreshing()) {
            Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
        }
    }


    private void getFreshNews() {
        Log.d("TAG", "getFreshNews: ");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(URL).get();
                   // Elements element = doc.select("div[id=wp_news_w6]").select("li");  工大要闻页面更新
                    Elements element = doc.select("div[class=col_news_list listcon]").select("li");
                    Log.d("TAG", "run: "+element.toString());
                    for (int i = 0; i < element.size(); i++) {
                        String title = element.get(i).text();
                        String Uml = element.get(i).getElementsByTag("a").attr("href");
                        Log.d("TAG", "新闻名字：" + title);
                        Log.d("TAG", "数据:" + element.get(i).getElementsByTag("a").attr("href"));
                        News news = new News();
                        news.setTitle(title);
                        if (Uml.startsWith("http")) {
                            news.setWebUrl(Uml);
                        } else {
                            news.setWebUrl(schoolUrl + Uml);
                        }
                        if (newsList.size() < 14) {
                            newsList.add(news);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
