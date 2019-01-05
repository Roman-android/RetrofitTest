package ru.roman.retrofittest;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
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

import ru.roman.retrofittest.constants.Constants;
import ru.roman.retrofittest.fragments.AddEditFragment;
import ru.roman.retrofittest.fragments.InfoFragment;
import ru.roman.retrofittest.fragments.RecycleFragment;
import ru.roman.retrofittest.model.RecycleViewModels;
import ru.roman.retrofittest.utils.DownloadFromSQL;

public class MainActivity extends AppCompatActivity {

    private final String LOG_MAIN = "main_log";

    DownloadFromSQL downloadFromSQL;

    public Toolbar toolbar;
    Spinner spinnerFav;
    ImageView imageDownload;
    TextView description;

    FragmentManager fragmentManager;
    public RecycleViewModels mViewModel;

    RecycleFragment recycleFragment;
    AddEditFragment addEditFragment;
    InfoFragment infoFragment;

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
        mViewModel = ViewModelProviders.of(this).get(RecycleViewModels.class);

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
                        case "ClickImg fragment":
                            showImage();
                            break;
                        default:
                            Log.d(LOG_MAIN, "Значение не опеделено");
                            break;
                    }
                    Log.d(LOG_MAIN, s);
                    toolbar.setTitle(s);
                }

            }
        });

    }

    public void showImage() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");

        Intent chooseIntent = Intent.createChooser(galleryIntent, "Выбор изображения");
        startActivityForResult(chooseIntent, Constants.REQUEST_CODE);
        Log.d(LOG_MAIN, "Сработал showImage()");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_CODE) {
            if (data == null) {
                Log.d(LOG_MAIN, "Не удалось загрузить изображение");
                return;
            }
            Uri imageUri = data.getData();
            addEditFragment.pickImg(imageUri);
            Log.d(LOG_MAIN, "onActivityResult сработал");
        }

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


}
