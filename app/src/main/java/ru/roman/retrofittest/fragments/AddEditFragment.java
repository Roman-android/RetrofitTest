package ru.roman.retrofittest.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;

import ru.roman.retrofittest.R;
import ru.roman.retrofittest.constants.Constants;
import ru.roman.retrofittest.model.DataModel;
import ru.roman.retrofittest.viewModels.ViewModels;

public class AddEditFragment extends Fragment {

    private String LOG_ADD_EDIT = "log_add_edit";
    private ViewModels mViewModel;

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

        /*View view = inflater.inflate(R.layout.test_edit_fragment, container, false);
        text_selected = view.findViewById(R.id.text_selected);
        text_size_array = view.findViewById(R.id.text_size_array);
        btn_update = view.findViewById(R.id.btn_update);*/

        mViewModel.setNameFragment(Constants.EDIT_FRAGMENT);
        mViewModel.clearLiveDataModel();
        mViewModel.getLiveDataModel().observe(this, editObserver);


        return view;
    }

    final Observer<DataModel> editObserver = new Observer<DataModel>() {
        @Override
        public void onChanged(@Nullable DataModel dataModel) {
            liveDataModel = dataModel;

            if (liveDataModel != null) {

                getFragmentName = liveDataModel.getFragmentName();

                if (getFragmentName.equals(Constants.EDIT_FRAGMENT)) {
                    Log.d(LOG_ADD_EDIT,"Данные загружены");
                    getId = liveDataModel.getId();
                    getCategory = liveDataModel.getCategory();
                    getText = liveDataModel.getText();
                    getFavour = liveDataModel.getFavour();
                    getImg = liveDataModel.getImg();

                    fotoImg.setImageURI(Uri.parse(getImg.get(0)));
                    category.setText(getCategory.get(0));
                    text.setText(getText.get(0));

                    //text_selected.setText(mViewModel.getId());
                    //text_size_array.setText(getText.get(0));
                }
            }

        }
    };

}
