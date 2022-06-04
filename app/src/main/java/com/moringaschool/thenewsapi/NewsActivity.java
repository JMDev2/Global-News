package com.moringaschool.thenewsapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moringaschool.thenewsapi.models.Datum;
import com.moringaschool.thenewsapi.models.TheNews;
import com.moringaschool.thenewsapi.network.NewsApi;
import com.moringaschool.thenewsapi.network.NewsClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {
    private NewsAdapter newsAdapter;
    private List<Datum> mList;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerview;
    @BindView(R.id.progressbar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ButterKnife.bind(this);


        NewsApi newsApiClient = NewsClient.getClient();
        Call<TheNews> call = NewsClient.getClient().getNews(Constants.NEWS_API_KEY, getIntent().getStringExtra("sports"), "en");
        call.enqueue(new Callback<TheNews>() {
            @Override
            public void onResponse(Call<TheNews> call, Response<TheNews> response) {
                hideProgressBar();
                if(response.isSuccessful()){
                    mList = response.body().getData();

                    NewsAdapter newsAdapter = new NewsAdapter(NewsActivity.this, mList);
                    mRecyclerview.setAdapter(newsAdapter);
                    mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<TheNews> call, Throwable t) {

            }
        });

    }
    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}