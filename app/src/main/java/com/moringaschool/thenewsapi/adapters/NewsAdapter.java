package com.moringaschool.thenewsapi.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcel;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.thenewsapi.Constants;
import com.moringaschool.thenewsapi.R;
import com.moringaschool.thenewsapi.models.Datum;
import com.moringaschool.thenewsapi.ui.NewsDetailsActivity;
import com.moringaschool.thenewsapi.ui.NewsDetailsFragment;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private Context mContext;
//    private List<Datum> dataList;
          private ArrayList<Datum> mNews = new ArrayList<>();


    public NewsAdapter(Context mContext, ArrayList<Datum> mNews) {
        this.mContext = mContext;
        this.mNews = mNews;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsrecyclerview, parent, false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(view, mNews);
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        holder.bindNews(mNews.get(position));
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.newsTitle) TextView mTitle;
        @BindView(R.id.imageView) ImageView mImage;
        @BindView(R.id.newsDescription) TextView mDescription;

        @BindView(R.id.newsPublished) TextView mPublished;

        private Context mContext;
        private int mOrientation;
        private ArrayList<Datum> mNews = new ArrayList<>();


        public NewsViewHolder(@NonNull View itemView, ArrayList<Datum> news) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
            mNews = news;

            // Determines the current orientation of the device:
            mOrientation = itemView.getResources().getConfiguration().orientation;

            // Checks if the recorded orientation matches Android's landscape configuration.
            // if so, we create a new DetailFragment to display in our special landscape layout:
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(0);



            }

            itemView.setOnClickListener(this);


        }

        private void createDetailFragment(int position) {
            NewsDetailsFragment newsDetailsFragment = NewsDetailsFragment.newInstance(mNews, position);
            FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.newsDetailContainer, newsDetailsFragment);
            ft.commit();
        }

        public void bindNews(Datum datum) {
            mTitle.setText(datum.getTitle());
            Picasso.get().load(datum.getImageUrl()).into(mImage);
            mDescription.setText(datum.getDescription());
            mPublished.setText(datum.getPublishedAt());


        }

        @Override
        public void onClick(View v) {

            // Determines the position of the news clicked:
            int itemPosition = getLayoutPosition();
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(itemPosition);
            } else {
                Intent intent = new Intent(mContext, NewsDetailsActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                intent.putExtra(Constants.EXTRA_KEY_NEWS, Parcels.wrap(mNews));

                mContext.startActivity(intent);

            }
        }
    }
}
