package com.eidith.studiochendraapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServer {

    public static final String imagesURL = "http://10.0.2.2/studiochendra/gambar/";
    private static final String baseURL = "http://10.0.2.2/studiochendra/";
    private static Retrofit retro;

    public static Retrofit connectRetrofit(){
        if (retro == null){
            retro = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retro;
    }

}
