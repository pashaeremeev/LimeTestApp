package com.example.practice;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.functions.Function1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepo extends ArrayList<Data> {

    private static DataRepo listData;

    public DataRepo(Function1<ArrayList<Data>,Void> callback) {
        createDataRepo(callback);
    }

    private void createDataRepo(Function1<ArrayList<Data>,Void> callback) {
        RetrofitClient.getInstance().getApi().getData().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                ArrayList<Data> newList = new ArrayList<>();
                if (response.isSuccessful()) {
                    newList.add(response.body());
                }
                callback.invoke(newList);
                Log.d("request", newList.size() + "");
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                callback.invoke( new ArrayList<>());
            }
        });
    }

    public static DataRepo get(Function1<ArrayList<Data>,Void> callback) {
        if (listData == null) {
            listData = new DataRepo(callback);
        }
        return listData;
    }

    public Data getById(int id) {
        for (int i = 0; i < this.size(); i++) {
            Data channel = this.get(i);
            if (channel.getId() == id) {
                return channel;
            }
        }
        return null;
    }

}