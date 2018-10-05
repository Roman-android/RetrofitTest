package ru.roman.retrofittest.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.roman.retrofittest.MainActivity;
import ru.roman.retrofittest.download_all_from_SQL.Messages;
import ru.roman.retrofittest.download_all_from_SQL.MessagesApi;
import ru.roman.retrofittest.downloadText.Text;
import ru.roman.retrofittest.downloadText.TextApi;
import ru.roman.retrofittest.insertText.ResponseInsert;
import ru.roman.retrofittest.testPost.Post;
import ru.roman.retrofittest.testPost.TestAPI;
import ru.roman.retrofittest.uploadImage.ResponseUpload;

import static android.app.Activity.RESULT_OK;

public class DownloadFromSQL {

    Context context;

    public DownloadFromSQL(Context context) {
        this.context = context;
    }

    private final String LOG_DOWNLOAD = "main_download";
    private final String LOG_POST = "main_insert";
    private final String LOG_UPLOAD = "main_upload";

    private TextApi textApi = TextApi.retrofit.create(TextApi.class);

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

    public void uploadImg(String imagePath, final ImageView imageView){
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Идет загрузка");
        progressDialog.show();

        File file = new File(imagePath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file",file.getName(),requestFile);

        String descriptionString = "Описание картинки";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"),descriptionString);

        Call <ResponseUpload> resultCall = textApi.uploadImg(description,body);
        resultCall.enqueue(new Callback<ResponseUpload>() {
            @Override
            public void onResponse(Call<ResponseUpload> call, Response<ResponseUpload> response) {
               progressDialog.dismiss();
                if (response.isSuccessful()){
                    if (response.body().getError()){
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(LOG_UPLOAD,"Путь к файлу: "+response.body().getMessage());
                        imageView.setImageURI(null);
                    }
                }else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUpload> call, Throwable t) {
progressDialog.dismiss();
                Toast.makeText(context, "Непредвиденная ошибка"+t.toString(), Toast.LENGTH_SHORT).show();
                Log.d(LOG_UPLOAD,"Непредвиденная ошибка"+t.toString());
            }
        });
    }


}
