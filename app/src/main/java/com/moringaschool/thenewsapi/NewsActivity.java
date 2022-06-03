package com.moringaschool.thenewsapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.moringaschool.thenewsapi.models.TheNews;
import com.moringaschool.thenewsapi.network.NewsApi;
import com.moringaschool.thenewsapi.network.NewsClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {
    @BindView(R.id.textCat)
    EditText mText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ButterKnife.bind(this);


        NewsApi newsApiClient = NewsClient.getClient();
        Call<TheNews> call = NewsClient.getClient().getNews("sports");
        call.enqueue(new Callback<TheNews>() {
            @Override
            public void onResponse(Call<TheNews> call, Response<TheNews> response) {

            }

            @Override
            public void onFailure(Call<TheNews> call, Throwable t) {

            }
        });
        //Fetching user input from the MainActivity
//        Intent intent = getIntent();
//        String name = intent.getStringExtra("sport");
//        mText.setText("Welcome " +name+ "!");
    }
}