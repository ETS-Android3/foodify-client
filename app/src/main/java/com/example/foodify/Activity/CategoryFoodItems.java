package com.example.foodify.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.foodify.Adapter.FoodAdapter;
import com.example.foodify.Model.Category;
import com.example.foodify.R;
import com.example.foodify.Retrofit.NetworkClient;
import com.example.foodify.Retrofit.RetrofitInterface;
import com.example.foodify.Utils.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CategoryFoodItems extends AppCompatActivity {
    String categoryId="";
    FloatingActionButton cart;
    DBHelper DB;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitInterface service;
    RecyclerView recycler_food;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        Retrofit retrofitClient = NetworkClient.getInstance();
        service = retrofitClient.create(RetrofitInterface.class);
        recycler_food=findViewById(R.id.recycler_food);
        DB=new DBHelper(this);
        recycler_food.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recycler_food.setLayoutManager(layoutManager);
        cart=findViewById(R.id.view_cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res=DB.getData();
                StringBuffer buffer=new StringBuffer();
                if(res.getCount()==0)
                    Toast.makeText(CategoryFoodItems.this, "NO Data", Toast.LENGTH_SHORT).show();
               else
                {

                    while (res.moveToNext()) {
                        buffer.append( " ID: "+res.getInt(0));
                        buffer.append(" Quantity: "+res.getInt(1));
                        buffer.append("\n");

                    }

                }
                AlertDialog.Builder builder=new AlertDialog.Builder(CategoryFoodItems.this);
               builder.setCancelable(true);
               builder.setTitle("Entries");
               builder.setMessage(buffer.toString());
               builder.show();


            }
        });

        if(getIntent()!=null)
        {
            categoryId=getIntent().getStringExtra("categoryId");
            Log.d("TAG", "Check category " +categoryId);
        }
        if(!categoryId.isEmpty() && categoryId!=null)
        {
            compositeDisposable.add(service.allFood(categoryId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse, this::handleError)
            );
        }


    }

    private void handleError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void handleResponse(com.example.foodify.Model.CategoryById categoryById) {
//        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
//        food_name.setText(categoryById.getItems().get(0).getName());
        recycler_food.setAdapter(new FoodAdapter(categoryById));

    }

}