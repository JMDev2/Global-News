package com.moringaschool.thenewsapi.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.moringaschool.thenewsapi.models.Datum;
import com.moringaschool.thenewsapi.models.TheNews;
import com.moringaschool.thenewsapi.ui.NewsDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class NewsPagerAdapter extends FragmentPagerAdapter {
    private List<Datum> mDatum;
//    private ArrayList<Datum> mNews = new ArrayList<>();


    public NewsPagerAdapter(@NonNull FragmentManager fm, int behavior, List<Datum> news) {
        super(fm, behavior);
        mDatum = news;

    }

    @Override
    public Fragment getItem(int position){
        return NewsDetailsFragment.newInstance(mDatum, position);
    }

    @Override
    public int getCount(){
        return mDatum.size();
    }
    @Override
    public CharSequence getPageTitle(int position){
        return mDatum.get(position).getTitle();
    }


}
