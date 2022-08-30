package com.example.apipizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity {

    EditText inputName,inputPrice,inputDescription,inputImageUrl,inputStock;
    Button submit;
    Pizza pizza;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        inputName = findViewById(R.id.inputName);
        inputPrice = findViewById(R.id.inputPrice);
        inputDescription = findViewById(R.id.inputDescription);
        inputImageUrl = findViewById(R.id.inputImageUrl);
        inputStock = findViewById(R.id.inputStock);
        submit = findViewById(R.id.submit);

        Intent intent = getIntent();
        if(intent.getSerializableExtra("pizza") != null){

            pizza = (Pizza) intent.getSerializableExtra("pizza");

            inputName.setText(pizza.getName());
            inputPrice.setText(String.valueOf(pizza.getPrice()));
            inputDescription.setText(pizza.getDescription());
            inputImageUrl.setText(pizza.getImageUrl());
            inputStock.setText(String.valueOf(pizza.getStock()));

        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pizza == null) pizza = new Pizza();
                pizza.setDescription(String.valueOf(inputDescription.getText()));
                pizza.setImageUrl(String.valueOf(inputImageUrl.getText()));
                pizza.setName(String.valueOf(inputName.getText()));
                pizza.setStock(Integer.parseInt(String.valueOf(inputStock.getText())));
                pizza.setPrice(Double.parseDouble(String.valueOf(inputPrice.getText())));

                if(pizza.getId() > 0){
                    new ApiActions(MainActivity2.this,"update",pizza);
                }else{

                    new ApiActions(MainActivity2.this,"insert",pizza);
                }
            }
        });

    }
}