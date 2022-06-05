package com.moringaschool.thenewsapi.adapters;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.thenewsapi.R;
import com.moringaschool.thenewsapi.models.Datum;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private Context mContext;
    private List<Datum> dataList;

    public NewsAdapter(Context mContext, List<Datum> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsrecyclerview,parent,false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(view);
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        holder.bindNews(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.newsTitle) TextView mTitle;
        @BindView(R.id.imageView) ImageView mImage;
        @BindView(R.id.newsDescription) TextView mDescription;
        @BindView(R.id.newsSource) TextView mSource;
        @BindView(R.id.newsPublished) TextView mPublished;
        @BindView(R.id.viewUrl) TextView mUrl;


        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }
        public void bindNews(Datum datum){
            mTitle.setText(datum.getTitle());
            Picasso.get().load(datum.getImageUrl()).into(mImage);
            mDescription.setText(datum.getDescription());
            mUrl.setText(datum.getUrl());
            mUrl.setMovementMethod(LinkMovementMethod.getInstance());
            mSource.setText(datum.getSource());
            mPublished.setText(datum.getPublishedAt());

        }
    }
}
