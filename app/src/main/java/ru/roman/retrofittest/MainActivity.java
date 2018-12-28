package ru.roman.retrofittest;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ru.roman.retrofittest.fragments.AddEditFragment;
import ru.roman.retrofittest.fragments.InfoFragment;
import ru.roman.retrofittest.viewModels.ViewModels;
import ru.roman.retrofittest.fragments.RecycleFragment;
import ru.roman.retrofittest.utils.DownloadFromSQL;

public class MainActivity extends AppCompatActivity {


    private final String LOG_MAIN = "main_log";

    DownloadFromSQL downloadFromSQL;

    public Toolbar toolbar;
    Spinner spinnerFav;
    ImageView imageDownload;
    TextView description;

    String imagePath;
    FragmentManager fragmentManager;
    public ViewModels mViewModel;

    RecycleFragment recycleFragment;
    AddEditFragment addEditFragment;
    InfoFragment infoFragment;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_MAIN, "onCreate");

        toolbar = findViewById(R.id.toolbar);
        spinnerFav = findViewById(R.id.spinnerFavorite);
        setSupportActionBar(toolbar);

        downloadFromSQL = new DownloadFromSQL(this);

        fragmentManager = getSupportFragmentManager();

        recycleFragment = new RecycleFragment();
        addEditFragment = new AddEditFragment();
        infoFragment = new InfoFragment();




        mViewModel = ViewModelProviders.of(this).get(ViewModels.class);
        /*String getSwitchFragment = mViewModel.getSwitchFragment().getValue();
        if (getSwitchFragment == null){
            Log.d(LOG_MAIN,"mViewModel.getSwitchFragment() = null");
            addFragment();
        }else {
            Log.d(LOG_MAIN,"mViewModel.getSwitchFragment() = "+ getSwitchFragment);
        }*/
        addFragment();
        mViewModel.getSwitchFragment().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s != null) {
                    spinnerFav.setVisibility(View.GONE);
                    switch (s) {
                        case "InfoFragment":
                            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, infoFragment)
                                    .addToBackStack(null)
                                    .commit();

                            setUpButton(true);
                            break;
                        case "AddFragment":
                            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, addEditFragment)
                                    .addToBackStack(null)
                                    .commit();

                            setUpButton(true);
                            break;
                        case "Edit fragment":
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, addEditFragment)
                                    .addToBackStack(null)
                                    .commit();

                            setUpButton(true);
                            break;
                        default:
                            Log.d(LOG_MAIN, "Значение не опеделено");
                            break;
                    }
                    Log.d(LOG_MAIN, s);
                    //mViewModel.setSwitchFragment(null);
                    toolbar.setTitle(s);
                }

            }
        });

    }

    private void setUpButton(Boolean b) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(b);
            getSupportActionBar().setDisplayHomeAsUpEnabled(b);
            getSupportActionBar().setDisplayShowHomeEnabled(b);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            fragmentManager.popBackStack();
            setUpButton(false);
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addFragment() {
        fragmentManager.beginTransaction()
                .replace(R.id.container, recycleFragment)
                .commit();
    }

    public void OnPostRequest(View view) {
        downloadFromSQL.postRequest();
    }


    // TODO: 17.11.2018 Методы для выбора и загрузки изображения из галлереи на сервер в БД
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
            downloadFromSQL.uploadImg(imagePath, imageDownload);
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
