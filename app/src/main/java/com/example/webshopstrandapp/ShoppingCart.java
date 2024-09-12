package com.example.webshopstrandapp;

import static com.example.webshopstrandapp.MainActivity.cart;
import static com.example.webshopstrandapp.MainActivity.inventory;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ShoppingCart extends AppCompatActivity {

    TextView total;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shopping_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        showCart();
        setTotal();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        //lv = findViewById(R.id.lv_stringlist);
        //adapter = new ItemAdapter(getApplicationContext(), 0, inventory);
        //lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        setTotal();
    }

    void showCart(){
        //List<Item> inventory = ShoppingCart;
        adapter = new ItemAdapter(this, 0, cart);
        ListView lv = findViewById(R.id.lv_stringlist);
        lv.setAdapter(adapter);
    }

    void setTotal(){
        total = findViewById(R.id.price_total);
        double sum = 1e-6;
        for (Item i : cart){
            sum += (i.price * i.quantity);
        }
        sum = (double)Math.round(sum * 100d) / 100d;
        String temp = "Total: " + sum + " $";
        total.setText(temp);
    }
}