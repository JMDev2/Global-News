package com.moringaschool.thenewsapi.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsFragment extends Fragment {

    private static final String TAG = NewsActivity.class.getSimpleName();

    private String mRecentNews;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    public List<Datum> mList;
    private NewsAdapter mAdapter;
    private NewsAdapter newsAdapter;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerview;
//    @BindView(R.id.progressbar) ProgressBar mProgressBar;
//    @BindView(R.id.errorTextView) TextView mErrorTextView;


    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();

        // Instructs fragment to include menu options:
        setHasOptionsMenu(true);

//
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
                mRecentNews = mSharedPreferences.getString(Constants.PREFERENCES_NEWS_KEY, null);

        if (mRecentNews != null) {
            fetchNews(mRecentNews);
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Call super to inherit method from parent:
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_search, menu);


        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        //adding the setOnQueryTextListener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                fetchNews(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    private void fetchNews(String news) {
        NewsApi client = NewsClient.getClient();

        //CHECK THIS LINE
        Call<TheNews> call = client.getNews(Constants.NEWS_API_KEY,news, "en");

        call.enqueue(new Callback<TheNews>() {
            @Override
            public void onResponse(Call<TheNews> call, Response<TheNews> response) {
//                hideProgressBar();
                if (response.isSuccessful()) {
                    mList = response.body().getData();
                    mAdapter = new NewsAdapter(getActivity(), (ArrayList<Datum>) mList);

                    mRecyclerview.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    mRecyclerview.setLayoutManager(layoutManager);
                    mRecyclerview.setHasFixedSize(true);

                    showNews();

                } else {
//                    showUnsuccessfulMessage();
                }
            }


            @Override
            public void onFailure(Call<TheNews> call, Throwable t) {
                Log.e(TAG, "onFAilure: ", t);
//                hideProgressBar();
//

            }
        });
    }



    private void addToSharedPreferences(String news) {
        mEditor.putString(Constants.PREFERENCES_NEWS_KEY, news).apply();
    }

//    private void showFailureMessage() {
//        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
//        mErrorTextView.setVisibility(View.VISIBLE);
//    }
//
//    private void showUnsuccessfulMessage() {
//        mErrorTextView.setText("Something went wrong. Please try again later");
//        mErrorTextView.setVisibility(View.VISIBLE);
//    }

    private void showNews() {
        mRecyclerview.setVisibility(View.VISIBLE);
    }

//    private void hideProgressBar() {
//        mProgressBar.setVisibility(View.GONE);
//    }


}
