package ru.roman.retrofittest.utils;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.roman.retrofittest.model.DownloadModel;
import ru.roman.retrofittest.model.InsertModel;
import ru.roman.retrofittest.model.UploadImageModel;

public interface DownloadApi {

    @GET("get.php")
    Call<List<DownloadModel>> text(@Query("id") String id,
                                   @Query("fav") String isFavour);
    //Call<List<DownloadModel>> text(@QueryMap Map<String,String> options);


    @POST("insert.php")
    @FormUrlEncoded
    Call<InsertModel>saveText(@Field("category")String category,
                              @Field("text")String text,
                              @Field("favour")String favour);

    @Multipart
    @POST("upload.php")
    Call<UploadImageModel>uploadImg(@Part ("category") RequestBody category,
                                    @Part MultipartBody.Part file,
                                    @Query("id") String id);

    @PUT("put_img.php/{id}")
    Call<UploadImageModel>putImg(@Path("id")String id, @Body String img_name);

}