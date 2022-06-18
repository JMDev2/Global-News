package com.moringaschool.thenewsapi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.moringaschool.thenewsapi.R;
import com.moringaschool.thenewsapi.models.Datum;
import com.moringaschool.thenewsapi.util.ItemTouchHelperAdapter;
import com.moringaschool.thenewsapi.util.OnStartDragListener;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseNewsListAdapter extends FirebaseRecyclerAdapter<Datum, FirebaseNewsViewHolder> implements ItemTouchHelperAdapter {

    private Query mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    private ChildEventListener mChildEventListener;
    private ArrayList<Datum> mNews = new ArrayList<>();


    public FirebaseNewsListAdapter(@NonNull FirebaseRecyclerOptions<Datum> options, Query mRef, OnStartDragListener mOnStartDragListener, Context mContext) {
        super(options);
        this.mRef = mRef;
        this.mOnStartDragListener = mOnStartDragListener;
        this.mContext = mContext;this.mChildEventListener = mChildEventListener;


    mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                mNews.add(snapshot.getValue(Datum.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onBindViewHolder(@NonNull FirebaseNewsViewHolder firebaseNewsViewHolder, int position, @NonNull Datum model) {
        firebaseNewsViewHolder.bindNews(model);
        firebaseNewsViewHolder.bindNews(model);
        firebaseNewsViewHolder.newsImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(firebaseNewsViewHolder);
                }
                return false;
            }
        });
    }

    @NonNull
    @Override
    public FirebaseNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item_drag,parent,false);
        return new FirebaseNewsViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mNews, fromPosition, toPosition); //to update the order of our mNews ArrayList items passing in the ArrayList of items and the starting and ending positions.
        notifyItemMoved(fromPosition, toPosition);
        setIndexInFirebase();
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mNews.remove(position);
        getRef(position).removeValue();

    }

    private void setIndexInFirebase(){
        for(Datum datum : mNews){
            int index = mNews.indexOf(datum);
            DatabaseReference ref = getRef(index);
            datum.setIndex(Integer.toString(index));
            ref.setValue(datum);
        }
    }

    @Override
    public void stopListening() {
        super.stopListening();
        mRef.removeEventListener(mChildEventListener);
     }
}
