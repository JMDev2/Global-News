package com.moringaschool.thenewsapi.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class SavedNewsFragment extends Fragment implements OnStartDragListener {

    private DatabaseReference mNewsReference;
    private FirebaseRecyclerAdapter<Datum, FirebaseNewsViewHolder> mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
//    @BindView(R.id.progressbar)
//    ProgressBar mProgressBar;


//    public SavedNewsFragment() {
//        // Required empty public constructor
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_saved_news, container, false);
        ButterKnife.bind(this, view);
        setUpFirebaseAdapter();
        return view;
    }


    public void setUpFirebaseAdapter() {

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
        mFirebaseAdapter = new FirebaseNewsListAdapter(options, query, this, getActivity());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mFirebaseAdapter);
        mRecyclerView.setHasFixedSize(true);

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                mFirebaseAdapter.notifyDataSetChanged();
            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback((ItemTouchHelperAdapter) mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);


    }
    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    //method is now public
    public void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.stopListening();
    }
    @Override
    public void onStart(){
        super.onStart();
        mFirebaseAdapter.startListening();
    }
    @Override
    public void onStop(){
        super.onStop();
        mFirebaseAdapter.stopListening();
    }
    private void showNews(){
        mRecyclerView.setVisibility(View.VISIBLE);
    }
//    private void hideProgressBar(){
//        mProgressBar.setVisibility(View.GONE);
//    }
}