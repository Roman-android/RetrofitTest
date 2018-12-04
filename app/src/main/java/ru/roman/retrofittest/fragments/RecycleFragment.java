package ru.roman.retrofittest.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import ru.roman.retrofittest.R;
import ru.roman.retrofittest.adapters.RecycleViewAdapter;
import ru.roman.retrofittest.interfaces.OnItemClickListener;

public class RecycleFragment extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    DividerItemDecoration dividerItemDecoration;
    ArrayList<ArrayList<String>> dataFromSQL = new ArrayList<>();
    private EditViewModel mViewModel;

    public static RecycleFragment newInstance(ArrayList<ArrayList<String>> data) {

        Bundle args = new Bundle();

        RecycleFragment fragment = new RecycleFragment();
        for (int i = 0; i < data.size(); i++) {
            args.putStringArrayList(String.valueOf(i), data.get(i));
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null) {
            for (int i=0;i<getArguments().size();i++){
                dataFromSQL.add(getArguments().getStringArrayList(String.valueOf(i)));
            }
        }
        mViewModel = ViewModelProviders.of(this).get(EditViewModel.class);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recycle_fragment_layout, container, false);

        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycleViewMain);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        RecycleViewAdapter adapter = new RecycleViewAdapter(dataFromSQL, getActivity(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "position: " + position, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "arrays: " + dataFromSQL.get(0).get(position), Toast.LENGTH_SHORT).show();
                mViewModel.select(String.valueOf(position));
            }
        });
        recyclerView.setAdapter(adapter);
    }

}
