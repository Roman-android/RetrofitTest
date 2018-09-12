package ru.roman.retrofittest.downloadText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.roman.retrofittest.insertText.ResponseInsert;

public interface TextApi {

    /*@GET("get_all_json.php")
    Call<List<Text>> text();*/
    String baseText = "http://u47689.netangels.ru/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseText)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("get_all_json.php")
    Call<List<Text>> text(@Query("fav")int isFavour);

    //@POST("insert.php")
    @POST("insert.php")
    @FormUrlEncoded
    Call<ResponseInsert>saveText(@Field("desc")String desc,
                                 @Field("text")String text,
                                 @Field("favour")String favour);

}