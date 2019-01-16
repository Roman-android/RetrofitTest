package ru.roman.retrofittest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.CursorLoader;
import android.widget.ImageView;
import android.widget.Toast;

import ru.roman.retrofittest.model.EditFragmentViewModel;
import ru.roman.retrofittest.workSQL.UploadImgSQL;

public class PickImage {

    private UploadImgSQL uploadImgSQL = new UploadImgSQL();
Context context;
    private EditFragmentViewModel mEditFragmentModel = new EditFragmentViewModel();

    public PickImage(Context context) {
        this.context = context;
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = 0;
        String result = "";
        if (cursor != null) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
            cursor.close();
        }

        return result;
    }

    public void uploadImg(ImageView fotoImg, FragmentActivity fragmentActivity, String imagePath) {
        if (imagePath != null) {
            verifyStoragePermissions(fragmentActivity);
            //uploadImgSQL.uploadImg(mEditFragmentModel.getImagePath(), fotoImg);
            Toast.makeText(context, "Изображение выбрано!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Пожалуйста сначала выберите изображение!", Toast.LENGTH_SHORT).show();
        }
    }

    private static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
