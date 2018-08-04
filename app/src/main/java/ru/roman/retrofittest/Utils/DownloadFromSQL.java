package ru.roman.retrofittest.Utils;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.roman.retrofittest.downloadMessage.Messages;
import ru.roman.retrofittest.downloadMessage.MessagesApi;
import ru.roman.retrofittest.downloadText.Text;
import ru.roman.retrofittest.downloadText.TextApi;

public class DownloadFromSQL {
    private final String LOG_DOWNLOAD = "main_download";

    TextApi textApi;

    public void downloadMessages() {
        String baseUrlExampl = "http://u47689.netangels.ru/";
        String baseUrlMy = "http://u47689.netangels.ru/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrlExampl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MessagesApi messagesApi = retrofit.create(MessagesApi.class);

        Call<List<Messages>> messages = messagesApi.messages();
        messages.enqueue(new Callback<List<Messages>>() {
            @Override
            public void onResponse(@NonNull Call<List<Messages>> call, @NonNull Response<List<Messages>> response) {
                Log.d(LOG_DOWNLOAD, "response " + response.body().size());
            }

            @Override
            public void onFailure(@NonNull Call<List<Messages>> call, @NonNull Throwable t) {

            }
        });
    }


    public void downloadText() {

        TextApi textApi = TextApi.retrofit.create(TextApi.class);

        Call<List<Text>> text = textApi.text(0);
        text.enqueue(new Callback<List<Text>>() {
            @Override
            public void onResponse(@NonNull Call<List<Text>> call, @NonNull Response<List<Text>> response) {
                for (int i=0;i<response.body().size();i++){
                    Log.d(LOG_DOWNLOAD, "response: "+response.body().get(i).getText()+" "+response.body().get(i).getDesc());
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Text>> call, @NonNull Throwable t) {
                Log.d(LOG_DOWNLOAD, "failure "+t);
            }
        });
    }
}
