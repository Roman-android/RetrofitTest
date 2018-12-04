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

import ru.roman.retrofittest.R;

public class EditFragment extends Fragment {

    private EditViewModel mViewModel;
    TextView selected;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edit_fragment, container, false);
        selected = view.findViewById(R.id.selected);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EditViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getSelected().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                selected.setText(s);
            }
        });
    }

}
