package com.example.apipizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Login extends AppCompatActivity {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    Button register,login;
    EditText email,password;
    OkHttpClient client;
    private static final String API = "https://63045ba00de3cd918b45944a.mockapi.io/api/v1/Users";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        email = findViewById(R.id.userEmail);
        password = findViewById(R.id.userPassword);
        client = new OkHttpClient();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email  = String.valueOf(email.getText());
                String Password = String.valueOf(password.getText());
                getUser(Email,Password);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Login.this,Signup.class);
                startActivity(intent1);
                finish();
            }
        });

    }

    public void getUser(String email,String password){
        Request request = new Request.Builder()
                .url(API)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    String data = responseBody.string();
                    Login.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            ArrayList<User> userList = gson.fromJson(data,new TypeToken<ArrayList<User>>(){}.getType());
                            List<User> loginUser = userList.stream().filter(x -> x.getEmail().equals(email) && x.getPassword().equals(password)).collect(Collectors.toList());
                            if(loginUser.size() == 1){
                                User user = loginUser.get(0);
                                dbHelper dbHelper = new dbHelper(Login.this);

                                dbHelper.addUser(user);
                                Intent intent = new Intent(Login.this,MainActivity.class);
                                intent.putExtra("user",user);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(Login.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}