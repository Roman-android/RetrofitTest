package ru.roman.retrofittest.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import ru.roman.retrofittest.R;
import ru.roman.retrofittest.constants.Constants;
import ru.roman.retrofittest.dialogs.ClickImgDialog;
import ru.roman.retrofittest.libs.ImgGlide;
import ru.roman.retrofittest.model.DataModel;
import ru.roman.retrofittest.viewModels.ViewModels;

public class AddEditFragment extends Fragment implements View.OnClickListener {

    private String LOG_ADD_EDIT = "log_add_edit";
    private ViewModels mViewModel;
    private ImgGlide glide;

    ImageView fotoImg;
    EditText category;
    EditText text;
    Button btn_favour;
    Button btn_save;

    TextView text_selected;
    TextView text_size_array;
    Button btn_update;
    private ArrayList<ArrayList<String>> dataFromSQL = new ArrayList<>();

    DataModel liveDataModel;
    private String nameFragment;

    // TODO: 25.12.2018 fields for get data from DataModel class
    //region Fields for get data from DataModel class
    private ArrayList<String> getId = new ArrayList<>();
    private ArrayList<String> getCategory = new ArrayList<>();
    private ArrayList<String> getText = new ArrayList<>();
    private ArrayList<String> getFavour = new ArrayList<>();
    private ArrayList<String> getImg = new ArrayList<>();
    private String getFragmentName;
    //endregion

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ViewModels.class);
        glide = new ImgGlide(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_edit_fragment, container, false);
        fotoImg = view.findViewById(R.id.foto);
        category = view.findViewById(R.id.category);
        text = view.findViewById(R.id.text);
        btn_favour = view.findViewById(R.id.btnFavour);
        btn_save = view.findViewById(R.id.btnSave);

        mViewModel.setNameFragment(Constants.EDIT_FRAGMENT);
        mViewModel.clearLiveDataModel();
        mViewModel.getLiveDataModel().observe(this, editObserver);

        fotoImg.setOnClickListener(this);

        return view;
    }

    final Observer<DataModel> editObserver = new Observer<DataModel>() {
        @Override
        public void onChanged(@Nullable DataModel dataModel) {
            liveDataModel = dataModel;

            if (liveDataModel != null) {

                getFragmentName = liveDataModel.getFragmentName();

                if (getFragmentName.equals(Constants.EDIT_FRAGMENT)) {
                    Log.d(LOG_ADD_EDIT, "Данные загружены");
                    getId = liveDataModel.getId();
                    getCategory = liveDataModel.getCategory();
                    getText = liveDataModel.getText();
                    getFavour = liveDataModel.getFavour();
                    getImg = liveDataModel.getImg();

                    glide.showImg(getImg.get(0), 300, 300, fotoImg);
                    category.setText(getCategory.get(0));
                    text.setText(getText.get(0));
                }
            }

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.foto:
                FragmentManager manager = getActivity().getSupportFragmentManager();
                Toast.makeText(getActivity(), "Нажал на картинку!", Toast.LENGTH_SHORT).show();
                ClickImgDialog clickImgDialog = new ClickImgDialog();
                clickImgDialog.show(manager,"clickImg");
        }
    }
}
