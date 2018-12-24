package ru.roman.retrofittest.utils;

import android.app.ProgressDialog;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.roman.retrofittest.downloadText.DownloadText;
import ru.roman.retrofittest.insertText.InsertText;
import ru.roman.retrofittest.uploadImage.UploadImage;

public class DownloadFromSQL {

    private Context context;

    public DownloadFromSQL(Context context) {
        this.context = context;
    }

    private final String LOG_DOWNLOAD = "main_download";
    private final String LOG_POST = "main_insert";
    private final String LOG_UPLOAD = "main_upload";

    private ArrayList<ArrayList<String>> dataFromSQL = new ArrayList<>();

    private DownloadApi downloadApi = RetrofitClient.getRetrofitApi().create(DownloadApi.class);
    private MutableLiveData<ArrayList<ArrayList<String>>> loadData = new MutableLiveData<>();

    public MutableLiveData<ArrayList<ArrayList<String>>> downloadText(String id, String isFavour, final String nameFragment) {
        /*
        Map<String, String> data = new HashMap<>();
        //data.put("fav","1");
        data.put("id", "5");

        Call<List<DownloadText>> text = downloadApi.text(data);
*/

        Call<List<DownloadText>> text = downloadApi.text(id, isFavour);

        text.enqueue(new Callback<List<DownloadText>>() {
            @Override
            public void onResponse(@NonNull Call<List<DownloadText>> call, @NonNull Response<List<DownloadText>> response) {

                ArrayList<String> idArray = new ArrayList<>();
                ArrayList<String> categoryArray = new ArrayList<>();
                ArrayList<String> textArray = new ArrayList<>();
                ArrayList<String> favourArray = new ArrayList<>();
                ArrayList<String> imgArray = new ArrayList<>();

                if (response.body() != null) {
                    if (!dataFromSQL.isEmpty()) {
                        dataFromSQL.clear();
                    }
                    for (int i = 0; i < response.body().size(); i++) {


                        String idToArray = response.body().get(i).getId();
                        String categoryToArray = response.body().get(i).getCategory();
                        String textToArray = response.body().get(i).getText();
                        String favourToArray = response.body().get(i).getFavour();
                        String imgToArray = response.body().get(i).getImg_name();

                        idArray.add(idToArray);
                        categoryArray.add(categoryToArray);
                        textArray.add(textToArray);
                        favourArray.add(favourToArray);
                        imgArray.add(imgToArray);
                    }
                    ArrayList<String> fragment = new ArrayList<>();
                    fragment.add(nameFragment);
                    dataFromSQL.add(idArray);
                    dataFromSQL.add(categoryArray);
                    dataFromSQL.add(textArray);
                    dataFromSQL.add(favourArray);
                    dataFromSQL.add(imgArray);
                    dataFromSQL.add(fragment);
                    loadData.setValue(dataFromSQL);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DownloadText>> call, @NonNull Throwable t) {
                Log.d(LOG_DOWNLOAD, "failure " + t);
            }

        });
        Log.d(LOG_DOWNLOAD, "Сработал downloadText()");
        return loadData;
    }

    public void postRequest() {
        Call<InsertText> call = downloadApi.saveText("заголовок_1", "мой_текст_1", "1");

        call.enqueue(new Callback<InsertText>() {
            @Override
            public void onResponse(Call<InsertText> call, Response<InsertText> response) {
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
            public void onFailure(Call<InsertText> call, Throwable t) {
                Log.d(LOG_POST, "LOG_POST: " + t.toString());
            }
        });
    }

    public void uploadImg(String imagePath, final ImageView imageView) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Идет загрузка");
        progressDialog.show();

        File file = new File(imagePath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);

        String categoryString = "Описание картинки";
        RequestBody category = RequestBody.create(MediaType.parse("multipart/form-data"), categoryString);

        Call<UploadImage> resultCall = downloadApi.uploadImg(category, body);
        resultCall.enqueue(new Callback<UploadImage>() {
            @Override
            public void onResponse(Call<UploadImage> call, Response<UploadImage> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getError()) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(LOG_UPLOAD, "Путь к файлу: " + response.body().getMessage());
                        imageView.setImageURI(null);
                    }
                } else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadImage> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Непредвиденная ошибка" + t.toString(), Toast.LENGTH_SHORT).show();
                Log.d(LOG_UPLOAD, "Непредвиденная ошибка" + t.toString());
            }
        });
    }

}
