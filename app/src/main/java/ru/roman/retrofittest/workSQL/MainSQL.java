package ru.roman.retrofittest.workSQL;

import android.content.Context;

import ru.roman.retrofittest.utils.DownloadApi;
import ru.roman.retrofittest.utils.RetrofitClient;

class MainSQL {

    DownloadApi downloadApi = RetrofitClient.getRetrofitApi().create(DownloadApi.class);
}
