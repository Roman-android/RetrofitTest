package ru.roman.retrofittest.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ru.roman.retrofittest.R;
import ru.roman.retrofittest.viewModels.ViewModels;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    private ViewModels mViewModel;
    TextView selected;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_fragment, container, false);
        selected = view.findViewById(R.id.selected);
        mViewModel = ViewModelProviders.of(getActivity()).get(ViewModels.class);
        selected.setText(mViewModel.getId());

        return view;
    }

}
