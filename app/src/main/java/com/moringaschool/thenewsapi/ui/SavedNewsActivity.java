package com.moringaschool.thenewsapi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.thenewsapi.Constants;
import com.moringaschool.thenewsapi.R;
import com.moringaschool.thenewsapi.adapters.FirebaseNewsViewHolder;
import com.moringaschool.thenewsapi.models.Datum;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedNewsActivity extends AppCompatActivity {

    //initializing the Databasereference and firebaseRecyclerAdapter member variables
    private DatabaseReference mNewsReference;
    private FirebaseRecyclerAdapter<Datum, FirebaseNewsViewHolder> mFirebaseAdapter;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.progressbar) ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //settuing the activity news layout into setcontent view
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        mNewsReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_NEWS);
        setUpFirebaseAdapter();
        hideProgressBar();
        showNews();
    }

    public  void setUpFirebaseAdapter(){
        FirebaseRecyclerOptions<Datum> options =
                new FirebaseRecyclerOptions.Builder<Datum>()
                        .setQuery(mNewsReference, Datum.class)
                        .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Datum, FirebaseNewsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseNewsViewHolder firebaseRestaurantViewHolder, int position, @NonNull Datum news) {
                firebaseRestaurantViewHolder.bindNews(news);

            }

            @NonNull
            @Override
            public FirebaseNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsrecyclerview,parent,false);
                return new FirebaseNewsViewHolder(view);
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);

    }
    @Override
    protected void onStart(){
        super.onStart();
        mFirebaseAdapter.stopListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        mFirebaseAdapter.stopListening();
    }
    private void showNews(){
        mRecyclerView.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }
}