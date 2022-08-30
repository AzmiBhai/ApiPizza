package com.example.apipizza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Signup extends AppCompatActivity {
    EditText username,email,password;
    Button signup;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = new User();

                user.setUsername(String.valueOf(username.getText()));
                user.setEmail(String.valueOf(email.getText()));
                user.setPassword(String.valueOf(password.getText()));

                new ApiUsers(Signup.this,user);
            }
        });


    }
}