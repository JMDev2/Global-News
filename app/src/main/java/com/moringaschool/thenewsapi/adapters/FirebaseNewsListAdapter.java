package com.moringaschool.thenewsapi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.moringaschool.thenewsapi.R;
import com.moringaschool.thenewsapi.models.Datum;
import com.moringaschool.thenewsapi.util.ItemTouchHelperAdapter;
import com.moringaschool.thenewsapi.util.OnStartDragListener;

public class FirebaseNewsListAdapter extends FirebaseRecyclerAdapter<Datum, FirebaseNewsViewHolder> implements ItemTouchHelperAdapter {

    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    public FirebaseNewsListAdapter(@NonNull FirebaseRecyclerOptions<Datum> options, DatabaseReference mRef, OnStartDragListener mOnStartDragListener, Context mContext) {
        super(options);
        this.mRef = mRef;
        this.mOnStartDragListener = mOnStartDragListener;
        this.mContext = mContext;
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
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}
