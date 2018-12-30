package ru.roman.retrofittest.workSQL;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.roman.retrofittest.model.DownloadModel;
import ru.roman.retrofittest.model.DataModel;

public class GetSQL extends MainSQL {

    private final String LOG_DOWNLOAD = "log_download";
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
}
