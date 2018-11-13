package ru.roman.retrofittest.utils;

import java.util.List;
import java.util.Map;

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
import retrofit2.http.QueryMap;
import ru.roman.retrofittest.downloadText.DownloadText;
import ru.roman.retrofittest.insertText.InsertText;
import ru.roman.retrofittest.uploadImage.UploadImage;

public interface DownloadApi {

    @GET("test_get_all_json.php")
    Call<List<DownloadText>> text(@Query("id") String id,
                                  @Query("fav") String isFavour);
    //Call<List<DownloadText>> text(@QueryMap Map<String,String> options);


    @POST("insert.php")
    @FormUrlEncoded
    Call<InsertText>saveText(@Field("category")String category,
                             @Field("text")String text,
                             @Field("favour")String favour);

    @Multipart
    @POST("upload.php")
    Call<UploadImage>uploadImg(@Part ("category") RequestBody category,
                               @Part MultipartBody.Part file);

}