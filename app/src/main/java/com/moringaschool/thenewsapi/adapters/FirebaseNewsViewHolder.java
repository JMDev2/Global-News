package com.moringaschool.thenewsapi.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.thenewsapi.Constants;
import com.moringaschool.thenewsapi.R;
import com.moringaschool.thenewsapi.models.Datum;
import com.moringaschool.thenewsapi.ui.NewsDetailsActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    View mView;
    Context mContext;

    public FirebaseNewsViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }
        //binding the views and setting the views
    public void bindNews(Datum news){
        ImageView newsImage = (ImageView) mView.findViewById(R.id.imageView);
        TextView newsTitle = (TextView) mView.findViewById(R.id.newsTitle);
        TextView newsDescription = (TextView) mView.findViewById(R.id.newsDescription);
        TextView newsPublished = (TextView) mView.findViewById(R.id.newsPublished);
        Picasso.get().load(news.getImageUrl()).into(newsImage);

        newsTitle.setText(news.getTitle());
        newsDescription.setText(news.getDescription());
        newsPublished.setText(news.getPublishedAt());


    }
    //creating a valueEvent Listener to grab out
    // the current list of news from the firebase  which was passed along to the newsdeatils activity

    @Override
    public void onClick(View v) {
        final ArrayList<Datum> news = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_NEWS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for ( DataSnapshot dataSnapshot : snapshot.getChildren()){
                    news.add(dataSnapshot.getValue(Datum.class));
                }
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, NewsDetailsActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("news", Parcels.wrap(news));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
