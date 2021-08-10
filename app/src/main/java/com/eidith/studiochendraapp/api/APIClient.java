package com.eidith.studiochendraapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;


public class APIClient {


    public static final String baseURL = "http://10.0.2.2/StudioChendraWebService/";
    public static final String imageURL = "http://10.0.2.2/StudioChendraWebService/gambar/";
    public static final String videoURL = "http://10.0.2.2/StudioChendraWebService/video/";
    private static Retrofit retrofit;

    //Connect retrofit to server to get Json and convert using Gson
    public static Retrofit connectRetrofitGson(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit connectRetrofitMoshi(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
