package com.example.webshopstrandapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.webshopstrandapp.MainActivity.cart;
import static com.example.webshopstrandapp.MainActivity.inventory;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SpecificItemActivity extends AppCompatActivity {

    TextView txtName;
    TextView txtDesc;
    TextView txtType;
    TextView txtPrice;
    TextView txtAmount;
    Button addBtn;
    String temp;
    //Context context;
    Item tempItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_specific_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //findviewbyid x5
        txtName = findViewById(R.id.spec_name);
        txtDesc = findViewById(R.id.spec_desc);
        txtType = findViewById(R.id.spec_type);
        txtPrice = findViewById(R.id.spec_price);
        txtAmount = findViewById(R.id.spec_amount);
        addBtn = findViewById(R.id.spec_add_btn);
        //intent
        Intent intent = getIntent();
        //string temp = intent.getstringextra(name) x5

        //Item tempItem = null;
        int selectedId = intent.getIntExtra("ITEM_ID", 0);
        for (Item i : inventory){
            if (i.id == selectedId){
                tempItem = new Item(selectedId, i.name, i.description, i.type, i.price, i.quantity);
            }
        }
        /*
        Item tempItem = new Item(
                intent.getIntExtra("ITEM_ID", 0),
                intent.getStringExtra("ITEM_NAME"),
                intent.getStringExtra("ITEM_DESC"),
                intent.getStringExtra("ITEM_TYPE"),
                intent.getDoubleExtra("ITEM_PRICE", 0.00),
                intent.getIntExtra("ITEM_AMOUNT", 0)
        );

         */
        if(tempItem == null || tempItem.id == 0){
            finish();
        }
        //if not null, set textviews
        if(tempItem.name != null){
            temp = "Name: " + tempItem.name;
            txtName.setText(temp);
        }
        if(tempItem.description != null){
            temp = "Description: " + tempItem.description;
            txtDesc.setText(temp);
        }
        if(tempItem.type != null){
            temp = "Type: " + tempItem.type;
            txtType.setText(temp);
        }
        temp = "Price: " + tempItem.price + " $";
        txtPrice.setText(temp);
        temp = "Amount: " + tempItem.quantity;
        txtAmount.setText(temp);
        addBtn.setOnClickListener(view -> {
            if(tempItem.quantity > 0) {
                addToCart(tempItem);

                for (Item i : inventory) {
                    if (i.id == tempItem.id) {
                        i.setQuantity(i.quantity - 1);
                        /*
                        if (i.quantity == 0) {
                            finish();
                        } else {
                            break;
                        }

                         */
                    }
                }
                tempItem.setQuantity(tempItem.quantity - 1);
                temp = "Amount: " + tempItem.quantity;
                txtAmount.setText(temp);
            }
            else {
                Toast.makeText(getApplicationContext(), "No more stock " + tempItem.name, Toast.LENGTH_LONG).show();
            }
        });
    }

    static void addToCart(Item temp){
        for (Item i : cart){
            if(i.id == temp.id){
                i.setQuantity(i.quantity + 1);
                return;
            }
        }
        cart.add(new Item(temp.id, temp.name, temp.description, temp.type, temp.price, 1));
    }

}