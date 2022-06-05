package com.moringaschool.thenewsapi.ui;

import android.icu.util.ULocale;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moringaschool.thenewsapi.R;
import com.moringaschool.thenewsapi.models.Datum;


import com.moringaschool.thenewsapi.models.TheNews;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailsFragment extends Fragment {

    @BindView(R.id.fragmentTitle) TextView mTitle;
    @BindView(R.id.fragmentNews) TextView mNews;
    @BindView(R.id.fragmentSite) TextView mSite;
    @BindView(R.id.fragmentImageView) ImageView mImage;

    private Datum mDatum;

    public NewsDetailsFragment(){
        //empty public construtor
    }

    //used instead of constructor and returns a new instance of newsdetails fragment
    public static NewsDetailsFragment newInstance(Datum news){
        NewsDetailsFragment newsDetailsFragment = new NewsDetailsFragment(); //instance of newsdetailsfragment

        Bundle args = new Bundle();
        //Parceler library
        args.putParcelable("news", Parcels.wrap(news));
        newsDetailsFragment.setArguments(args);
        return newsDetailsFragment;
    }

    //onCreate method, called when the fragment is created. Used to unwrap
//    our news object from the arguments in the newInstance() method
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mDatum = Parcels.unwrap(getArguments().getParcelable("news"));
    }

    //onCreateView used to get the images and texts
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_news_details, container, false);
        ButterKnife.bind(this, view);
        Picasso.get().load(mDatum.getImageUrl()).into(mImage);

//        List<Datum> categories = new ArrayList<>();
//
//        for (Datum category: mDatum.getCategories()) {
//            categories.add(category.getTitle());
//        }


        mTitle.setText(mDatum.getTitle());
        mNews.setText(mDatum.getDescription());
        mSite.setText(mDatum.getSource());



        return view;
    }

}