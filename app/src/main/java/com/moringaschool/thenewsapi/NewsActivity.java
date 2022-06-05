package com.moringaschool.thenewsapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moringaschool.thenewsapi.adapters.NewsAdapter;
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
    @BindView(R.id.errorTextView) TextView mErrorTextView;

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

                    showNews();
                }else{
                    showUnsuccessfulMessage();
                }
            }

            @Override
            public void onFailure(Call<TheNews> call, Throwable t) {
                hideProgressBar();
                showFailureMessage();
            }
        });

    }
    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("No News match!");
        mErrorTextView.setVisibility(View.VISIBLE);
    }
    private void showNews() {
        mRecyclerview.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}