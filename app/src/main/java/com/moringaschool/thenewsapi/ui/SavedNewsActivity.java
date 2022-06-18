package com.moringaschool.thenewsapi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.moringaschool.thenewsapi.Constants;
import com.moringaschool.thenewsapi.R;
import com.moringaschool.thenewsapi.adapters.FirebaseNewsListAdapter;
import com.moringaschool.thenewsapi.adapters.FirebaseNewsViewHolder;
import com.moringaschool.thenewsapi.models.Datum;
import com.moringaschool.thenewsapi.util.ItemTouchHelperAdapter;
import com.moringaschool.thenewsapi.util.OnStartDragListener;
import com.moringaschool.thenewsapi.util.SimpleItemTouchHelperCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedNewsActivity extends AppCompatActivity implements OnStartDragListener {

    //initializing the Databasereference and firebaseRecyclerAdapter member variables
    private DatabaseReference mNewsReference;
    private FirebaseRecyclerAdapter<Datum, FirebaseNewsViewHolder> mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.progressbar) ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //settuing the activity news layout into setcontent view
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        //Retrieving User-Specific Data
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String uid = user.getUid();
//        mNewsReference = FirebaseDatabase
//                .getInstance()
//                .getReference(Constants.FIREBASE_CHILD_NEWS)
//                        .child(uid);


        setUpFirebaseAdapter();
        hideProgressBar();
        showNews();
    }

    public  void setUpFirebaseAdapter() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        Query query = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_NEWS)
                .child(uid)
                .orderByChild(Constants.FIREBASE_QUERY_INDEX);


//        mNewsReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_NEWS).child(uid);


        FirebaseRecyclerOptions<Datum> options =
                new FirebaseRecyclerOptions.Builder<Datum>()
                        .setQuery(query, Datum.class)
                        .build();
        mFirebaseAdapter = new FirebaseNewsListAdapter(options, query, this, this);

//        mFirebaseAdapter = new FirebaseRecyclerAdapter<Datum, FirebaseNewsViewHolder>(options) {
//        mFirebaseAdapter = new FirebaseNewsListAdapter(options, mNewsReference, (OnStartDragListener) this, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
        mRecyclerView.setHasFixedSize(true);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback((ItemTouchHelperAdapter) mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);



    }


//            @Override
//            protected void onBindViewHolder(@NonNull FirebaseNewsViewHolder firebaseRestaurantViewHolder, int position, @NonNull Datum news) {
//                firebaseRestaurantViewHolder.bindNews(news);
//
//            }

//            @NonNull
//            @Override
//            public FirebaseNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsrecyclerview,parent,false);
//
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item_drag,parent,false);
//
//                return new FirebaseNewsViewHolder(view);
//            }
//        };

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setAdapter(mFirebaseAdapter);
//
//    }
    @Override
    protected void onStart(){
        super.onStart();
        mFirebaseAdapter.startListening();
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

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.stopListening();
     }

}