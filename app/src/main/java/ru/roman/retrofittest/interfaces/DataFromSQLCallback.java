package ru.roman.retrofittest.interfaces;

import android.support.v4.util.ArrayMap;

import java.util.ArrayList;

public interface DataFromSQLCallback {
        void callBackDataFromSQL(ArrayMap<String, String> data, ArrayList<String> img);
}
