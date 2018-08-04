package ru.roman.retrofittest.downloadMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MessagesApi {

    @GET("messages.json")
    Call<List<Messages>>messages();

}
