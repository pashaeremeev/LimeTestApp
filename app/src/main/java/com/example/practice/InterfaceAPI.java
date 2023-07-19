package com.example.practice;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InterfaceAPI {

    @GET("fTuatK")
    Call<Data> getData();
}
