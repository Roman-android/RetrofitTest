package ru.roman.retrofittest.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.roman.retrofittest.PickImage;
import ru.roman.retrofittest.R;
import ru.roman.retrofittest.constants.Constants;
import ru.roman.retrofittest.dialogs.ClickImgDialog;
import ru.roman.retrofittest.libs.ImgGlide;
import ru.roman.retrofittest.model.DataModel;
import ru.roman.retrofittest.model.EditFragmentViewModel;
import ru.roman.retrofittest.model.RecycleViewModels;
import ru.roman.retrofittest.workSQL.UploadImgSQL;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddEditFragment extends Fragment implements View.OnClickListener {

    private String LOG_ADD_EDIT = "log_add_edit";
    private RecycleViewModels mViewModel;
    private EditFragmentViewModel mEditFragmentModel;
    private ImgGlide glide;
    UploadImgSQL uploadImgSQL = new UploadImgSQL();

    public ImageView fotoImg;
    EditText category;
    EditText text;
    Button btn_favour;
    Button btn_save;

    TextView text_selected;
    TextView text_size_array;
    Button btn_update;
    private ArrayList<ArrayList<String>> dataFromSQL = new ArrayList<>();

    DataModel liveDataModel;

    // TODO: 25.12.2018 fields for get data from DataModel class
    //region Fields for get data from DataModel class
    private ArrayList<String> getId = new ArrayList<>();
    private ArrayList<String> getCategory = new ArrayList<>();
    private ArrayList<String> getText = new ArrayList<>();
    private ArrayList<String> getFavour = new ArrayList<>();
    private ArrayList<String> getImg = new ArrayList<>();
    private String getFragmentName;
    String imagePath;
    private PickImage pickImage;
    //endregion

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(RecycleViewModels.class);
        mEditFragmentModel = ViewModelProviders.of(getActivity()).get(EditFragmentViewModel.class);
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
        pickImage = new PickImage(getActivity());
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

    public void pickImg(Uri imageUri){
        fotoImg.setImageURI(imageUri);
        imagePath = pickImage.getRealPathFromURI(imageUri);
        //mEditFragmentModel.setImagePath(imagePath);
        pickImage.uploadImg(fotoImg,getActivity(),imagePath);


        uploadImgSQL.uploadImg(imagePath,fotoImg,"2");
    }

}
