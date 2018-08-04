package ru.roman.retrofittest.Utils;

import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.roman.retrofittest.downloadText.TextApi;
import ru.roman.retrofittest.insertText.ResponseInsert;

public class InsertIntoSQL {

    private final String LOG_INSERT = "main_insert";

    public void insertText(){
        TextApi textApi = TextApi.retrofit.create(TextApi.class);

        Call<ResponseInsert>saveText = textApi.saveText("descPOST");
        saveText.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                Log.d(LOG_INSERT,"Получилось!");
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Log.d(LOG_INSERT,t.toString());
            }
        });
    }
}
