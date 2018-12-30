package ru.roman.retrofittest.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.Toast;

import ru.roman.retrofittest.workSQL.PutImgSQL;

public class ClickImgDialog extends AppCompatDialogFragment {

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
                                PutImgSQL putImgSQL = new PutImgSQL();
                                putImgSQL.putImg("4","img");
                                break;
                            case 1:
                                break;
                        }
                    }
                });


        return builder.create();
    }
}
