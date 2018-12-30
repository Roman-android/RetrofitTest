package ru.roman.retrofittest.utils;

import android.app.ProgressDialog;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
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
import ru.roman.retrofittest.model.DownloadModel;
import ru.roman.retrofittest.model.InsertModel;
import ru.roman.retrofittest.model.DataModel;
import ru.roman.retrofittest.model.UploadImageModel;

public class DownloadFromSQL {

    private Context context;

    public DownloadFromSQL(Context context) {
        this.context = context;
    }

    private final String LOG_DOWNLOAD = "log_download";
    private final String LOG_POST = "log_insert";
    private final String LOG_UPLOAD = "log_upload";

    private DownloadApi downloadApi = RetrofitClient.getRetrofitApi().create(DownloadApi.class);

    private DataModel dataModel = new DataModel();
    private MutableLiveData<DataModel> dataRepository = new MutableLiveData<>();

    public MutableLiveData<DataModel> downloadText(String id, String isFavour, final String fragmentName) {
        /*
        Map<String, String> data = new HashMap<>();
        //data.put("fav","1");
        data.put("id", "5");

        Call<List<DownloadModel>> text = downloadApi.text(data);
*/
        Call<List<DownloadModel>> text = downloadApi.text(id, isFavour);

        text.enqueue(new Callback<List<DownloadModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<DownloadModel>> call, @NonNull Response<List<DownloadModel>> response) {

                if (response.body() != null) {
                    dataModel.clearDataModel();
                    for (int i = 0; i < response.body().size(); i++) {
                        String getId = response.body().get(i).getId();
                        String getCategory = response.body().get(i).getCategory();
                        String getText = response.body().get(i).getText();
                        String getFavour = response.body().get(i).getFavour();
                        String getImg = response.body().get(i).getImg_name();

                        dataModel.setId(getId);
                        dataModel.setCategory(getCategory);
                        dataModel.setText(getText);
                        dataModel.setFavour(getFavour);
                        dataModel.setImg(getImg);
                    }

                    dataModel.setFragmentName(fragmentName);
                    dataRepository.setValue(dataModel);

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DownloadModel>> call, @NonNull Throwable t) {
                Log.d(LOG_DOWNLOAD, "failure " + t);
            }

        });
        Log.d(LOG_DOWNLOAD, "Сработал downloadText()");
        return dataRepository;
    }

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

        Call<UploadImageModel> resultCall = downloadApi.uploadImg(category, body);
        resultCall.enqueue(new Callback<UploadImageModel>() {
            @Override
            public void onResponse(@NonNull Call<UploadImageModel> call, @NonNull Response<UploadImageModel> response) {
                progressDialog.dismiss();
                if (response.body()!=null) {
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
            }

            @Override
            public void onFailure(Call<UploadImageModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Непредвиденная ошибка" + t.toString(), Toast.LENGTH_SHORT).show();
                Log.d(LOG_UPLOAD, "Непредвиденная ошибка" + t.toString());
            }
        });
    }

}
