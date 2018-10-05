package ru.roman.retrofittest.downloadText;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import ru.roman.retrofittest.insertText.ResponseInsert;
import ru.roman.retrofittest.uploadImage.ResponseUpload;

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

    @POST("insert.php")
    @FormUrlEncoded
    Call<ResponseInsert>saveText(@Field("desc")String desc,
                                 @Field("text")String text,
                                 @Field("favour")String favour);

    @Multipart
    @POST("upload.php")
    Call<ResponseUpload>uploadImg(@Part ("desc") RequestBody desc,
                                  @Part MultipartBody.Part file);

}