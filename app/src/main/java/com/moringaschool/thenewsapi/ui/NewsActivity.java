package com.moringaschool.thenewsapi.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.moringaschool.thenewsapi.Constants;
import com.moringaschool.thenewsapi.R;
import com.moringaschool.thenewsapi.adapters.NewsAdapter;
import com.moringaschool.thenewsapi.models.Datum;
import com.moringaschool.thenewsapi.models.TheNews;
import com.moringaschool.thenewsapi.network.NewsApi;
import com.moringaschool.thenewsapi.network.NewsClient;

import java.util.List;
import java.util.prefs.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {
    //shared preferences  variables
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentNews;


    private static final String TAG = NewsActivity.class.getSimpleName();
    private NewsAdapter mAdapter;
    private NewsAdapter newsAdapter;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerview;
    @BindView(R.id.progressbar) ProgressBar mProgressBar;
    public List<Datum> mList;
    @BindView(R.id.errorTextView) TextView mErrorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentNews = mSharedPreferences.getString(Constants.PREFERENCES_NEWS_KEY, null);

        if (mRecentNews != null) {
            fetchNews(mRecentNews);
        }
    }


    //a method to inflate and bind views .
    //retrieves users search from the searchView
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        //adding the setOnQueryTextListener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String news) {
                addToSharedPreferences(news);
                fetchNews(news);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String news) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void showNews() {
        mRecyclerview.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void addToSharedPreferences(String news) {
        mEditor.putString(Constants.PREFERENCES_NEWS_KEY, news).apply();
    }

    private void fetchNews(String news) {
        NewsApi client = NewsClient.getClient();



        //CHECK THIS LINE
        Call<TheNews> call = client.getNews(Constants.NEWS_API_KEY, getIntent().getStringExtra("sports"), "en");

        call.enqueue(new Callback<TheNews>() {
            @Override
            public void onResponse(Call<TheNews> call, Response<TheNews> response) {
                hideProgressBar();
                if (response.isSuccessful()) {
                        mList = response.body().getData();
                        mAdapter = new NewsAdapter(NewsActivity.this, mList);

                        mRecyclerview.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NewsActivity.this);
                        mRecyclerview.setLayoutManager(layoutManager);
                        mRecyclerview.setHasFixedSize(true);

                        showNews();

                    } else {
//                        showUnsuccessfulMessage();
                    }
                }



                @Override
                public void onFailure(Call<TheNews> call, Throwable t) {
                    Log.e(TAG, "onFAilure: ", t);
                    hideProgressBar();
//

                }
        });
    }
    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }



















    //    private void showFailureMessage() {
//        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
//        mErrorTextView.setVisibility(View.VISIBLE);
//    }

//    private void showUnsuccessfulMessage() {
//        mErrorTextView.setText("No News match!");
//        mErrorTextView.setVisibility(View.VISIBLE);
//    }











//        NewsApi newsApiClient = NewsClient.getClient();
//        Call<TheNews> call = NewsClient.getClient().getNews(Constants.NEWS_API_KEY, getIntent().getStringExtra("sports"), "en");
//        call.enqueue(new Callback<TheNews>() {
//            @Override
//            public void onResponse(Call<TheNews> call, Response<TheNews> response) {
//                hideProgressBar();
//                if(response.isSuccessful()){
//                    mList = response.body().getData();
//
//
//                    NewsAdapter newsAdapter = new NewsAdapter(NewsActivity.this, mList);
//                    mRecyclerview.setAdapter(newsAdapter);
//                    mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//
//                    showNews();
//                }else{
////                    showUnsuccessfulMessage();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TheNews> call, Throwable t) {
//                hideProgressBar();
////                showFailureMessage();
//            }
//        });
//
//    }
    //*************************************************

    //a method to inflate and bind views .
    //retrieves users search from the searchView
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_search, menu);
//        ButterKnife.bind(this);

//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mEditor = mSharedPreferences.edit();
//
//        MenuItem menuItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//
//        //adding the setOnQueryTextListener
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String news) {
//                addToSharedPreferences(news);
//                fetchNews(news);
//                return false;
//




//******************************************************

//                NewsApi client = NewsClient.getClient();
////                Call<TheNews> call = client.getNews("api_token", "category", "en" );
//                Call<TheNews> call = NewsClient.getClient().getNews(Constants.NEWS_API_KEY, getIntent().getStringExtra("sports"), "en");
//                call.enqueue(new Callback<TheNews>() {
//                    @Override
//                    public void onResponse(Call<TheNews> call, Response<TheNews> response) {
//                        hideProgressBar();

//                        if (response.isSuccessful()) {
//                            mList = response.body().getData();
//                            mAdapter = new NewsAdapter(NewsActivity.this, mList);
//
//                            mRecyclerview.setAdapter(mAdapter);
//                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NewsActivity.this);
//                            mRecyclerview.setLayoutManager(layoutManager);
//                            mRecyclerview.setHasFixedSize(true);
//
//                            showNews();
//
//                        } else {
//                            showUnsuccessfulMessage();
//                        }
//                    }

//                    @Override
//                    public void onFailure(Call<TheNews> call, Throwable t) {
//                        Log.e(TAG, "onFailure: ", t);
//                        hideProgressBar();
//                        showFailureMessage();
//
//                    }
//                });
//                return false;
//            }

//            @Override""
//            public boolean onQueryTextChange(String news) {
//                return false;
//            }
//        });

//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        return super.onOptionsItemSelected(item);
//    }
    //****************************************

    //Adding a method to write data to shared preference from the main activity
//    private void addToSharedPreferences(String news){
//        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY).apply();
//
//    }
//    private void showFailureMessage() {
//        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
//        mErrorTextView.setVisibility(View.VISIBLE);
//    }

//    private void showUnsuccessfulMessage() {
//        mErrorTextView.setText("No News match!");
//        mErrorTextView.setVisibility(View.VISIBLE);
//    }
//    private void showNews() {
//        mRecyclerview.setVisibility(View.VISIBLE);
//    }
//    private void hideProgressBar() {
//        mProgressBar.setVisibility(View.GONE);
//    }
}