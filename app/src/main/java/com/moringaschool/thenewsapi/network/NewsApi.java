package com.moringaschool.thenewsapi.network;

import com.moringaschool.thenewsapi.Constants;
import com.moringaschool.thenewsapi.models.TheNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("news/all")
    Call<TheNews> getNews (@Query("api_token") String apiToken, @Query("categories") String category, @Query("language") String language);

    }

