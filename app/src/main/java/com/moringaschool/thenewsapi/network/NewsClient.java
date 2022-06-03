package com.moringaschool.thenewsapi.network;

import com.moringaschool.thenewsapi.Constants;
import com.moringaschool.thenewsapi.models.TheNews;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsClient {
    private static Retrofit retrofit = null;

    public static NewsApi getClient(){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(Constants.NEWS_BASE_URL).client(client)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  retrofit.create(NewsApi.class);
    }



}

