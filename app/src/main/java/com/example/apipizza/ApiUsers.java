package com.example.apipizza;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiUsers {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private OkHttpClient client;
    private final String API = "https://63045ba00de3cd918b45944a.mockapi.io/api/v1/";
    private User user;
    private String jsonData;
    private Context context;
    private String myUrl;

    public ApiUsers(Context context,User user){
        this.context = context;
        this.user = user;
        this.client = new OkHttpClient();
        jsonData = new Gson().toJson(user);
        myUrl = API + "Users";
        setUser();
    }


    public void setUser(){
        RequestBody body = RequestBody.create(jsonData, JSON);
        Request request = new Request.Builder()
                .url(myUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                response.body().string();
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);
                ((AppCompatActivity)context).finish();
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }
        });




    }
}
