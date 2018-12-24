package ru.roman.retrofittest.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import ru.roman.retrofittest.viewModels.ViewModels;

public class AddEditFragment extends Fragment {

    private String LOG_ADD_EDIT = "log_add_edit";
    private ViewModels mViewModel;

    ImageView fotoImg;
    EditText category;
    EditText text;
    Spinner spinner;
    Button btn_save;

    TextView text_selected;
    TextView text_size_array;
    Button btn_update;
    private ArrayList<ArrayList<String>> dataFromSQL = new ArrayList<>();

    private String nameFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ViewModels.class);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        /*View view = inflater.inflate(R.layout.add_edit_fragment, container, false);
        fotoImg = view.findViewById(R.id.foto);
        category = view.findViewById(R.id.category);
        text = view.findViewById(R.id.text);
        spinner = view.findViewById(R.id.spinner);
        btn_save = view.findViewById(R.id.btnSave);*/

        View view = inflater.inflate(R.layout.test_edit_fragment, container, false);
        mViewModel.setNameFragment("EditFragment");
        text_selected = view.findViewById(R.id.text_selected);
        text_size_array = view.findViewById(R.id.text_size_array);
        btn_update = view.findViewById(R.id.btn_update);

        text_selected.setText(String.valueOf(mViewModel.getId()));
        //mViewModel.setId(String.valueOf(mViewModel.getSelect().getValue()));
        //mViewModel.setIsFlavour(null);
        mViewModel.getDataFromSQL().observe(this, editObserver);

        return view;
    }

    final Observer<ArrayList<ArrayList<String>>> editObserver = new Observer<ArrayList<ArrayList<String>>>() {
        @Override
        public void onChanged(@Nullable ArrayList<ArrayList<String>> arrayLists) {
            text_size_array.setText(String.valueOf(mViewModel.getDataFromSQL().getValue().get(0).get(0)));
            Toast.makeText(getActivity(), "Add edit fragment: сработал editObserver", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel.getDataFromSQL().removeObserver(editObserver);
        mViewModel.setId(null);
        mViewModel.setIsFlavour("1");
        mViewModel.setDataFromSQL(null);
        Toast.makeText(getActivity(), "onDestroyView", Toast.LENGTH_SHORT).show();
    }
}
