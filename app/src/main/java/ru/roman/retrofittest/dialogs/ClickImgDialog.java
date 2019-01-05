package ru.roman.retrofittest.dialogs;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.widget.Toast;

import ru.roman.retrofittest.fragments.AddEditFragment;
import ru.roman.retrofittest.model.EditFragmentViewModel;
import ru.roman.retrofittest.model.RecycleViewModels;
import ru.roman.retrofittest.workSQL.UploadImgSQL;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class ClickImgDialog extends AppCompatDialogFragment {

    RecycleViewModels mRecycleViewModels;

    private final String LOG_CLICK_FRAGMENT = "log_click_fragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecycleViewModels = ViewModelProviders.of(getActivity()).get(RecycleViewModels.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String [] items = {"Загрузить","Удалить"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выберите действие")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Выбран пункт: "+which, Toast.LENGTH_SHORT).show();
                        switch (which){
                            case 0:
                                mRecycleViewModels.setSwitchFragment("ClickImg fragment");
                                break;
                            case 1:
                                break;
                        }
                    }
                });


        return builder.create();
    }

}
