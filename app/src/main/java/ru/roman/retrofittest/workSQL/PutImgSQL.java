package ru.roman.retrofittest.workSQL;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.roman.retrofittest.model.UploadImageModel;

public class PutImgSQL extends MainSQL {

    private final String LOG_PUT = "log_put";

    public void putImg(String id,String img_name) {
        Call<UploadImageModel> call = downloadApi.putImg(id,img_name);
        call.enqueue(new Callback<UploadImageModel>() {
            @Override
            public void onResponse(@NonNull Call<UploadImageModel> call, @NonNull Response<UploadImageModel> response) {
                if (response.isSuccessful()) {
                    //Log.d(LOG_PUT, response.body().getValue());
                    Log.d(LOG_PUT, "Success: "+response.body().getMessage());
                    Log.d(LOG_PUT, "Success: "+response.body().getError().toString());
                } else {
                    ResponseBody errorBody = response.errorBody();
                    Log.d(LOG_PUT, "Error " + errorBody.toString());
                    Log.d(LOG_PUT, "Error " + String.valueOf(response.code()));
                    try {
                        if (response.errorBody() != null) {
                            //полный текст ошибки
                            Log.d(LOG_PUT, "Error " + response.errorBody().string());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UploadImageModel> call, Throwable t) {
                Log.d(LOG_PUT, "LOG_PUT: " + t.toString());
            }
        });
    }
}
