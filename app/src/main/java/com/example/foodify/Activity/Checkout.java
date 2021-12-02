package com.example.foodify.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodify.R;

public class Checkout extends AppCompatActivity {
    String totalprice="price";
    String totalcalories="calories";
    TextView Price;
    TextView Calories;
    EditText address;
    Button place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Calories=(TextView)findViewById(R.id.totalCalories);
       Price=(TextView)findViewById(R.id.totalPrice);

        place=findViewById(R.id.sendcart);
        totalprice=getIntent().getStringExtra("totalprice");
        totalcalories=getIntent().getStringExtra("totalcalories");
        Log.d("Price",totalprice);
        Log.d("Calories",totalcalories);
        Price.setText(totalprice);
        Calories.setText(totalcalories);
//        totalPrice.setText("price");
//        totalCalories.setText(getIntent().getExtras().getString("calories"));
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Checkout.this,orderSuccess.class);
                startActivity(intent);
            }
        });


    }
}