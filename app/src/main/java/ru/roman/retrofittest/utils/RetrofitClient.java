package ru.roman.retrofittest.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClient {

    static Retrofit getRetrofitApi(){
        String baseText = "http://u47689.netangels.ru/";
        return new Retrofit.Builder()
                .baseUrl(baseText)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


}
