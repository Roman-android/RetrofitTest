package ru.roman.retrofittest.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import ru.roman.retrofittest.R;
import ru.roman.retrofittest.adapters.RecycleViewAdapter;
import ru.roman.retrofittest.interfaces.OnItemClickListener;
import ru.roman.retrofittest.model.DataModel;
import ru.roman.retrofittest.viewModels.ViewModels;

public class RecycleFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;

    RecyclerView recyclerView;
    RecycleViewAdapter adapter;
    DividerItemDecoration dividerItemDecoration;

    Spinner spinner;

    BottomNavigationView bottomNavigationView;
    MenuItem editItemNavigation;
    MenuItem deleteItemNavigation;

    int positionItem;
    ArrayList<Integer> selectedItem = new ArrayList<>();

    private DataModel liveDataModel;
    private ViewModels mViewModel;
    private Boolean isLongClick = false;

    private final String LOG_RECYCLE_FRAGMENT = "recycle_fragment";

    // TODO: 25.12.2018 fields for get data from DataModel class
    //region Fields for get data from DataModel class
    private ArrayList<String> getId = new ArrayList<>();
    private ArrayList<String> getCategory = new ArrayList<>();
    private ArrayList<String> getText = new ArrayList<>();
    private ArrayList<String> getFavour = new ArrayList<>();
    private ArrayList<String> getImg = new ArrayList<>();
    private String getFragmentName;
    //endregion

    View checkedItems;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_RECYCLE_FRAGMENT, "onCreate");
        mViewModel = ViewModelProviders.of(getActivity()).get(ViewModels.class);
        setHasOptionsMenu(true);
    }

 /*   final Observer<ArrayList<ArrayList<String>>> recycleObserver = new Observer<ArrayList<ArrayList<String>>>() {
        @Override
        public void onChanged(@Nullable ArrayList<ArrayList<String>> arrayLists) {
            liveDataModel = arrayLists;
            if (liveDataModel != null && liveDataModel.get(5).get(0).equals("RecycleFragment")) {
                Log.d(LOG_RECYCLE_FRAGMENT, "1. Сработал recycleObserver: liveDataModel size: " + liveDataModel.get(0).size());
                Log.d(LOG_RECYCLE_FRAGMENT, "2. Сработал recycleObserver: getId = " + mViewModel.getId());
                Log.d(LOG_RECYCLE_FRAGMENT, "3.Сработал recycleObserver: getIsFlavour = " + mViewModel.getIsFlavour());
                initRecyclerView(view);
            }
        }
    };*/



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_RECYCLE_FRAGMENT, "onCreateView");

        final View view = inflater.inflate(R.layout.recycle_fragment_layout, container, false);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Recycle fragment");

        mViewModel.setId(null);
        mViewModel.setIsFlavour("0");
        mViewModel.setNameFragment("RecycleFragment");
        mViewModel.getLiveDataModel().observe(this, new Observer<DataModel>() {
            @Override
            public void onChanged(@Nullable DataModel dataModel) {
                liveDataModel = dataModel;
                if (dataModel != null) {
                    getId = dataModel.getId();
                    getCategory = dataModel.getCategory();
                    getText = dataModel.getText();
                    getFavour = dataModel.getFavour();
                    getImg = dataModel.getImg();
                    getFavour = dataModel.getFavour();
                    getFragmentName = dataModel.getFragmentName();
                }

                if (getFragmentName.equals("RecycleFragment")) {
                    Log.d(LOG_RECYCLE_FRAGMENT, "1. getFragmentName: " + getFragmentName);
                    Log.d(LOG_RECYCLE_FRAGMENT, "2. Сработал recycleObserver: liveDataModel size: " + getId.size());
                    Log.d(LOG_RECYCLE_FRAGMENT, "3. Сработал recycleObserver: getId = " + mViewModel.getId());
                    Log.d(LOG_RECYCLE_FRAGMENT, "4.Сработал recycleObserver: getIsFlavour = " + mViewModel.getIsFlavour());
                    initRecyclerView(view);
                }
            }
        });
        //Log.d(LOG_RECYCLE_FRAGMENT, "fragment name: " + mViewModel.getDataFromSQL().getValue().get(5).get(0));

        Log.d(LOG_RECYCLE_FRAGMENT, "onCreateView mViewModel.getId() = " + mViewModel.getId());
        Log.d(LOG_RECYCLE_FRAGMENT, "onCreateView mViewModel.getIsFlavour = " + mViewModel.getIsFlavour());

        spinner = getActivity().findViewById(R.id.spinnerFavorite);
        spinner.setVisibility(View.VISIBLE);
        //region Spinner
        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    mViewModel.setDataFromSQL(null);
                    liveDataModel = null;
                }
                switch (position) {
                    case 1:
                        mViewModel.setId(null);
                        mViewModel.setIsFlavour(null);
                        mViewModel.getLiveDataModel();
                        break;
                    case 2:
                        mViewModel.setId(null);
                        mViewModel.setIsFlavour("0");
                        mViewModel.getLiveDataModel();
                        break;
                    case 3:
                        mViewModel.setId(null);
                        mViewModel.setIsFlavour("1");
                        mViewModel.getLiveDataModel();
                        break;
                }
                if (position != 0){
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        //endregion

        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        editItemNavigation = bottomNavigationView.getMenu().getItem(1);
        deleteItemNavigation = bottomNavigationView.getMenu().getItem(2);

        return view;
    }


    //==========================================================================
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_RECYCLE_FRAGMENT, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG_RECYCLE_FRAGMENT, "onStart");
        Log.d(LOG_RECYCLE_FRAGMENT, "onStart mViewModel.getId() = " + mViewModel.getId());
        Log.d(LOG_RECYCLE_FRAGMENT, "onStart mViewModel.getIsFlavour = " + mViewModel.getIsFlavour());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_RECYCLE_FRAGMENT, "onResume");
        Log.d(LOG_RECYCLE_FRAGMENT, "1. onResume mViewModel.getId = " + mViewModel.getId());
        Log.d(LOG_RECYCLE_FRAGMENT, "2. onResume mViewModel.getIsFlavour = " + mViewModel.getIsFlavour());
        /*if (liveDataModel.size()>0) {
            liveDataModel.clear();
        }
        adapter.notifyDataSetChanged();*/
    }
    //=================================================================================

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycleViewMain);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new RecycleViewAdapter(liveDataModel, getActivity(), new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                positionItem = position;
                if (isLongClick) {
                    view.setBackgroundColor(getResources().getColor(R.color.colorOnLongClick));
                    selectedItem.add(positionItem);
                    if (selectedItem.size() > 1) {
                        editItemNavigation.setEnabled(false);
                    }
                } else {
                    Toast.makeText(getActivity(), "OnClick: position: " + positionItem, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "OnClick: arrays: " + getId.get(positionItem), Toast.LENGTH_SHORT).show();
                    mViewModel.setSwitchFragment("InfoFragment");
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                isLongClick = true;
                positionItem = position;
                Toast.makeText(getActivity(), "OnLongClick: position: " + position, Toast.LENGTH_SHORT).show();

                if (liveDataModel != null) {
                    Toast.makeText(getActivity(), "OnLongClick: position: " + getId.get(position), Toast.LENGTH_SHORT).show();
                } else if (liveDataModel == null){
                    Log.d(LOG_RECYCLE_FRAGMENT, "OnLongClick: liveDataModel.get(0).isEmpty(((");
                }

                view.setBackgroundColor(getResources().getColor(R.color.colorOnLongClick));

                selectedItem.add(positionItem);
                Log.d(LOG_RECYCLE_FRAGMENT, "selectedItem.size: " + selectedItem.size());
                ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
                deleteItemNavigation.setEnabled(true);
                if (selectedItem.size() == 1) {
                    editItemNavigation.setEnabled(true);
                } else if (selectedItem.size() > 1) {
                    editItemNavigation.setEnabled(false);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        Log.d(LOG_RECYCLE_FRAGMENT, "init recyclerView");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(getActivity(), "Нажата кнопка Home", Toast.LENGTH_SHORT).show();
            deleteItemNavigation.setEnabled(false);
            isLongClick = false;

            for (int i = 0; i < selectedItem.size(); i++) {
                checkedItems = recyclerView.getLayoutManager().findViewByPosition(selectedItem.get(i));
                checkedItems.setBackgroundColor(getResources().getColor(R.color.colorStart));
            }

            selectedItem.clear();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        selectedItem.clear();
        isLongClick = false;
        switch (menuItem.getItemId()) {
            case R.id.add:
                Toast.makeText(getActivity(), "Нажата кнопка ADD", Toast.LENGTH_SHORT).show();
                mViewModel.setSwitchFragment("AddFragment");
                mViewModel.setId("-1");
                break;
            case R.id.edit:
                Toast.makeText(getActivity(), "Нажата кнопка Edit", Toast.LENGTH_SHORT).show();
                mViewModel.setSwitchFragment("EditFragment");
                mViewModel.setId(getId.get(positionItem));
                //mViewModel.getDataFromSQL().removeObserver(recycleObserver);
                mViewModel.clearLiveDataModel();
                break;
            case R.id.delete:
                Toast.makeText(getActivity(), "Нажата кнопка Delete", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    // TODO: 10.12.2018 ЖИЗНЕННЫЙ ЦИКЛ


    @Override
    public void onPause() {
        super.onPause();
        Log.d(LOG_RECYCLE_FRAGMENT, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(LOG_RECYCLE_FRAGMENT, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(LOG_RECYCLE_FRAGMENT, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_RECYCLE_FRAGMENT, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(LOG_RECYCLE_FRAGMENT, "onDetach");
    }
}
