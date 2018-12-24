package ru.roman.retrofittest.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import ru.roman.retrofittest.utils.DownloadFromSQL;

public class ViewModels extends AndroidViewModel {

    private MutableLiveData<String> switchFragment = new MutableLiveData<>();

    public ViewModels(@NonNull Application application) {
        super(application);
    }

    private DownloadFromSQL downloadFromSQL = new DownloadFromSQL(getApplication());

    public void setSwitchFragment(String s) {
        switchFragment.setValue(s);
    }

    public LiveData<String> getSwitchFragment() {
        return switchFragment;
    }

    // TODO: 04.12.2018 Загрузка данных из Retrofit
    private LiveData<ArrayList<ArrayList<String>>> dataFromSQL;
    private String id;
    private String isFlavour;
    private String nameFragment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsFlavour() {
        return isFlavour;
    }

    public void setIsFlavour(String isFlavour) {
        this.isFlavour = isFlavour;
    }

    public String getNameFragment() {
        return nameFragment;
    }

    public void setNameFragment(String nameFragment) {
        this.nameFragment = nameFragment;
    }

    public void setDataFromSQL(LiveData<ArrayList<ArrayList<String>>> dataFromSQL) {
        this.dataFromSQL = dataFromSQL;
    }

    public LiveData<ArrayList<ArrayList<String>>> getDataFromSQL() {
        if (dataFromSQL == null) {
            dataFromSQL = downloadFromSQL.downloadText(getId(), getIsFlavour(),getNameFragment());
        }
        return dataFromSQL;
    }
}
