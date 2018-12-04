package ru.roman.retrofittest.fragments;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import ru.roman.retrofittest.R;

public class EditViewModel extends AndroidViewModel {

    private MutableLiveData<String> selected = new MutableLiveData<>();
    private MutableLiveData<Boolean>switchFrag = new MutableLiveData<>();

    public EditViewModel(@NonNull Application application) {
        super(application);

        //fragmentManager = application.getApplicationContext().getSupportFragmentManager();
    }

    public void select(String s){
        selected.setValue(s);
    }

    public LiveData<String> getSelected(){
        return selected;
    }



}
