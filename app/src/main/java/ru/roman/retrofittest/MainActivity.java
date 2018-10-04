package ru.roman.retrofittest;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import ru.roman.retrofittest.utils.DownloadFromSQL;

public class MainActivity extends AppCompatActivity {

    DownloadFromSQL downloadFromSQL;
    ImageView imageView;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        downloadFromSQL = new DownloadFromSQL(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                downloadFromSQL.downloadText();
            }
        });

        imageView = findViewById(R.id.imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        Uri imageUri = null;

        if (resultCode == RESULT_OK && requestCode == 100) {
            if (data == null) {
                Toast.makeText(this, "Не удалось загрузить изображение", Toast.LENGTH_SHORT).show();
                return;
            }
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
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
            downloadFromSQL.uploadImg(imagePath);
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
}
