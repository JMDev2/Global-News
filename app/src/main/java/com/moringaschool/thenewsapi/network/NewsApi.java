package com.moringaschool.thenewsapi.network;

import com.moringaschool.thenewsapi.Constants;
import com.moringaschool.thenewsapi.models.TheNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("news/all?" + Constants.API_TOKEN + "=" + Constants.NEWS_API_KEY)
    Call<TheNews> getNews (@Query("categories") String categories);
    }

