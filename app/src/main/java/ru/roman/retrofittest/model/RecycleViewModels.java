package ru.roman.retrofittest.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import ru.roman.retrofittest.model.DataModel;
import ru.roman.retrofittest.utils.DownloadFromSQL;
import ru.roman.retrofittest.workSQL.GetSQL;

public class RecycleViewModels extends AndroidViewModel {

    private MutableLiveData<String> switchFragment = new MutableLiveData<>();

    public RecycleViewModels(@NonNull Application application) {
        super(application);
    }
    private GetSQL getSQL = new GetSQL();

    public void setSwitchFragment(String s) {
        switchFragment.setValue(s);
    }

    public LiveData<String> getSwitchFragment() {
        return switchFragment;
    }

    // TODO: 04.12.2018 Загрузка данных из Retrofit
    private String id;
    private String isFavour;
    private String nameFragment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsFavour() {
        return isFavour;
    }

    public void setIsFavour(String isFavour) {
        this.isFavour = isFavour;
    }

    public String getNameFragment() {
        return nameFragment;
    }

    public void setNameFragment(String nameFragment) {
        this.nameFragment = nameFragment;
    }

    private LiveData<DataModel> liveDataModel;

    public void clearLiveDataModel() {
        if (liveDataModel != null) {
            liveDataModel = null;
        }
    }
    public LiveData<DataModel> getLiveDataModel() {
        if (liveDataModel == null) {
            liveDataModel = getSQL.downloadText(getId(), getIsFavour(), getNameFragment());
        }
        return liveDataModel;
    }
}
