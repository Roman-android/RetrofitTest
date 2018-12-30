package ru.roman.retrofittest.workSQL;

import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.roman.retrofittest.model.InsertModel;

public class InsertSQL extends MainSQL {

    private final String LOG_POST = "log_insert";

    public void postRequest() {
        Call<InsertModel> call = downloadApi.saveText("заголовок_1", "мой_текст_1", "1");

        call.enqueue(new Callback<InsertModel>() {
            @Override
            public void onResponse(Call<InsertModel> call, Response<InsertModel> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_POST, response.body().getValue());
                    Log.d(LOG_POST, response.body().getMessage());
                } else {
                    ResponseBody errorBody = response.errorBody();
                    Log.d(LOG_POST, errorBody.toString());
                    Log.d(LOG_POST, String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<InsertModel> call, Throwable t) {
                Log.d(LOG_POST, "LOG_POST: " + t.toString());
            }
        });
    }
}
