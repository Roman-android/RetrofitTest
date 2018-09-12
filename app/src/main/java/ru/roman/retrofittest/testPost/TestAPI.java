package ru.roman.retrofittest.testPost;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TestAPI {

    String testUrl = "https://jsonplaceholder.typicode.com/";

    Retrofit retrofiClient = new Retrofit.Builder()
            .baseUrl(testUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @POST("posts")
    @FormUrlEncoded
    Call<Post> savePost(@Field("title") String title,
                  @Field("body") String body,
                  @Field("userId") long userId);

}
