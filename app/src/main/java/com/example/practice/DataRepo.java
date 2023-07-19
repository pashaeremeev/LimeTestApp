package com.example.practice;

import android.util.Log;

import java.io.IOException;
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
                    Data body = response.body();
                    newList.add(body);
                    listData.add(body);
                }
                callback.invoke(newList);
                Log.d("request", newList.size() + "");
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                callback.invoke(new ArrayList<>());
            }
        });
//        try {
//            Response<Data> execute = RetrofitClient.getInstance().getApi().getData().execute();
//            Log.d("request", execute + "");
//
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static DataRepo get(Function1<ArrayList<Data>,Void> callback) {
        if (listData == null) {
            listData = new DataRepo(callback);
        }
        return listData;
    }

    public static DataRepo getList() {
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
