package com.example.webshopstrandapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button shoppingCart;
    ListView lv;
    ItemAdapter adapter;
    private RequestQueue requestQueue;
    //private Context context;

    static List<Item> cart = new ArrayList<>();
    static List<Item> inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        requestQueue = Volley.newRequestQueue(this);
        getItems();
        //showAdvancedListView();
        openShoppingCart();
    }

    /*
    void showAdvancedListView(){
        List<Item> inventory = Data.AddData();
        ItemAdapter adapter = new ItemAdapter(this, 0, inventory);
        ListView lv = findViewById(R.id.lv_stringlist);
        lv.setAdapter(adapter);
    }

     */

    @Override
    public void onRestart(){
        super.onRestart();
        //lv = findViewById(R.id.lv_stringlist);
        //adapter = new ItemAdapter(getApplicationContext(), 0, inventory);
        //lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    void openShoppingCart() {
        shoppingCart = findViewById(R.id.cart);

        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShoppingCartActivity.class);
                startActivity(intent);
            }
        });
    }

    void getItems() {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                "http://10.131.209.19:8989/item",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Type listType = new TypeToken<List<Item>>() {
                        }.getType();
                        inventory = new Gson().fromJson(response, listType);
                        lv = findViewById(R.id.lv_stringlist);
                        adapter = new ItemAdapter(getApplicationContext(), 0, inventory);
                        lv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("***** Volley *****", "onErrorResponse: ", error);
                    }
                });

        requestQueue.add(request);
    }

}