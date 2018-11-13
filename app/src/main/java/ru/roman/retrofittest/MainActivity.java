package ru.roman.retrofittest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.roman.retrofittest.adapters.RecycleViewAdapter;
import ru.roman.retrofittest.interfaces.DataFromSQLCallback;
import ru.roman.retrofittest.utils.DownloadFromSQL;

public class MainActivity extends AppCompatActivity implements DataFromSQLCallback {


    private final String LOG_MAIN = "main_log";

    DownloadFromSQL downloadFromSQL;

    RecyclerView recyclerView;
    ImageView imageDownload;
    TextView description;

    DividerItemDecoration dividerItemDecoration;

    String imagePath;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        downloadFromSQL = new DownloadFromSQL(this);
        downloadFromSQL.registerCallback(this);
        downloadFromSQL.downloadText();

        recyclerView = findViewById(R.id.recycleViewMain);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);

        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        imageDownload = findViewById(R.id.imgLoad);
        description = findViewById(R.id.description);
    }

    @Override
    public void callBackDataFromSQL(ArrayMap<String, String> data, ArrayList<String>img) {
        Log.d(LOG_MAIN,"size: "+data.size());

        RecycleViewAdapter adapter = new RecycleViewAdapter(data, img, this);
        recyclerView.setAdapter(adapter);

    }

    public void OnPostRequest(View view) {
        downloadFromSQL.postRequest();
    }

    public void OnPickImg(View view) {
        showImage();
    }

    private void showImage() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");

        Intent chooseIntent = Intent.createChooser(galleryIntent, "Выбор изображения");
        startActivityForResult(chooseIntent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Uri imageUri;

        if (resultCode == RESULT_OK && requestCode == 100) {
            if (data == null) {
                Toast.makeText(this, "Не удалось загрузить изображение", Toast.LENGTH_SHORT).show();
                return;
            }
            imageUri = data.getData();
            imageDownload.setImageURI(imageUri);
            imagePath = getRealPathFromURI(imageUri);
        } else if (resultCode == RESULT_CANCELED && requestCode == 100) {
            Toast.makeText(this, "Вы отменили загрузку изображения((", Toast.LENGTH_SHORT).show();
        }


    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
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

    public void OnUploadImg(View view) {
        if (imagePath != null) {
            verifyStoragePermissions(MainActivity.this);
            downloadFromSQL.uploadImg(imagePath,imageDownload);
        } else {
            Toast.makeText(this, "Пожалуйста сначала выберите изображение!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
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

    public void OnDownloadImg(View view) {
    }

}
