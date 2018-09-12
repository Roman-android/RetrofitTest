package ru.roman.retrofittest.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.roman.retrofittest.download_all_from_SQL.Messages;
import ru.roman.retrofittest.download_all_from_SQL.MessagesApi;
import ru.roman.retrofittest.downloadText.Text;
import ru.roman.retrofittest.downloadText.TextApi;
import ru.roman.retrofittest.insertText.ResponseInsert;
import ru.roman.retrofittest.testPost.Post;
import ru.roman.retrofittest.testPost.TestAPI;

public class DownloadFromSQL {
    private final String LOG_DOWNLOAD = "main_download";
    private final String LOG_POST = "main_insert";

    //TextApi textApi;

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

        Call<List<Text>> text = textApi.text(1);
        text.enqueue(new Callback<List<Text>>() {
            @Override
            public void onResponse(@NonNull Call<List<Text>> call, @NonNull Response<List<Text>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    Log.d(LOG_DOWNLOAD, "response: " + " Столбик TEXT: " + response.body().get(i).getText() + "; Столбик DESC: " + response.body().get(i).getDesc());
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Text>> call, @NonNull Throwable t) {
                Log.d(LOG_DOWNLOAD, "failure " + t);
            }
        });
    }

    public void postRequest() {
        TextApi textApi = TextApi.retrofit.create(TextApi.class);
        Call<ResponseInsert> call = textApi.saveText("заголовок_1","мой_текст_1","1");

        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.isSuccessful()){
                    Log.d(LOG_POST, response.body().getValue());
                    Log.d(LOG_POST, response.body().getMessage());
                }else {
                    ResponseBody errorBody = response.errorBody();
                    Log.d(LOG_POST,errorBody.toString());
                    Log.d(LOG_POST, String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Log.d(LOG_POST,"LOG_POST: "+t.toString());
            }
        });
    }

    public void testRequest (){
        TestAPI testAPI = TestAPI.retrofiClient.create(TestAPI.class);
        Call<Post> call = testAPI.savePost("myTitle_1","myBody_1",1);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                if (response.isSuccessful()){
                    Log.d("POST_REQUEST_SUCCESS",response.body().toString());
                }else {
                    ResponseBody errorBody = response.errorBody();
                    Log.d("POST_REQUEST_ERROR",errorBody.toString());
                    Log.d("POST_REQUEST_ERROR", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                //Log.e("POST_REQUEST", "Unable to submit post to API.");
            }
        });
    }
}
