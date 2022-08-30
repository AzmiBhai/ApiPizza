package com.example.apipizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    dbHelper dbHelper;

    FloatingActionButton add;
    User loginUser;
    RecyclerView recyclerView;
    PizzaAdapter adapter;
    OkHttpClient client;
    ArrayList<User> userArrayList ;
    private static final String API  = "https://63045ba00de3cd918b45944a.mockapi.io/api/v1/pizza";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new dbHelper(MainActivity.this);

        if (dbHelper.getUser().size() >0){
            userArrayList = dbHelper.getUser();
        }
        else {
            userArrayList = null;
        }

        add = findViewById(R.id.add);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));


        Intent intent = getIntent();
        loginUser =(User) intent.getSerializableExtra("user");



        if(userArrayList == null){
            Intent intent2 = new Intent(MainActivity.this,Login.class);
            startActivity(intent2);
            finish();
        }
        client = new OkHttpClient();

        getAllPizza();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void getAllPizza(){
        Request request = new Request.Builder()
                .url(API)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    String data = responseBody.string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            ArrayList<Pizza> pizzaList = gson.fromJson(data,new TypeToken<ArrayList<Pizza>>(){}.getType());
                            adapter = new PizzaAdapter(MainActivity.this,pizzaList);
                            recyclerView.setAdapter(adapter);

                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

        getMenuInflater().inflate(R.menu.optionmenu,menu);
        
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.logout){
            dbHelper.deleteUser(userArrayList.get(0).getId());
            Intent intent =new Intent(MainActivity.this,Login.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}