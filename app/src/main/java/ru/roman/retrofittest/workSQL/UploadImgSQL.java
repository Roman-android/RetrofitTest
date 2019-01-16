package ru.roman.retrofittest.workSQL;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.roman.retrofittest.model.UploadImageModel;

public class UploadImgSQL extends MainSQL {

    private final String LOG_UPLOAD = "log_upload";

    public void uploadImg(String imagePath, final ImageView imageView,String id) {

        File file = new File(imagePath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);

        String categoryString = "Описание картинки";
        RequestBody category = RequestBody.create(MediaType.parse("multipart/form-data"), categoryString);

        Call<UploadImageModel> resultCall = downloadApi.uploadImg(category, body,id);
        resultCall.enqueue(new Callback<UploadImageModel>() {
            @Override
            public void onResponse(@NonNull Call<UploadImageModel> call, @NonNull Response<UploadImageModel> response) {
                if (response.body()!=null) {
                    if (response.isSuccessful()) {
                        if (response.body().getError()) {
                            Log.d(LOG_UPLOAD,"Error :"+response.body().getMessage());
                        } else {
                            Log.d(LOG_UPLOAD, "Successful. Путь к файлу: " + response.body().getMessage());
                            imageView.setImageURI(null);
                            Log.d(LOG_UPLOAD, "uploadImg сработал");
                        }
                    } else {
                        Log.d(LOG_UPLOAD, "Request not successful: " + response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UploadImageModel> call, @NonNull Throwable t) {
                Log.d(LOG_UPLOAD, "Непредвиденная ошибка: " + t.toString());
            }
        });
    }
}
