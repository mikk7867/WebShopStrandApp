package com.example.webshopstrandapp;

import static com.example.webshopstrandapp.MainActivity.cart;
import static com.example.webshopstrandapp.MainActivity.inventory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {

    private List<Item> itemList;
    private Context context;

    public ItemAdapter(@NonNull Context context, int resource, @NonNull List<Item> itemList) {
        super(context, resource, itemList);
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);

        Item currentItem = itemList.get(position);
        TextView tvName = listItem.findViewById(R.id.txt_name);
        tvName.setText(currentItem.name);
        TextView tvPrice = listItem.findViewById(R.id.txt_price);
        String price = currentItem.price + "$";
        tvPrice.setText(price);
        TextView tvQuantity = listItem.findViewById(R.id.txt_quantity);
        tvQuantity.setText(String.valueOf(currentItem.quantity));

        Button addBtn = listItem.findViewById(R.id.add_btn);
        addBtn.setOnClickListener(view -> {
                addItem(currentItem);
                if (context instanceof ShoppingCartActivity) {
                ((ShoppingCartActivity) context).setTotal();
            }
        });

        Button removeBtn = listItem.findViewById(R.id.remove_btn);
        removeBtn.setOnClickListener(view -> {
            removeItem(currentItem);
            if (context instanceof ShoppingCartActivity) {
                ((ShoppingCartActivity) context).setTotal();
            }
        });

        listItem.setOnClickListener(view -> {
            //if (currentItem.quantity > 0) {
                //if shoppingcart contains item => item.quantity ++, else add item
                Intent intent = new Intent(context.getApplicationContext(), SpecificItemActivity.class);

                intent.putExtra("ITEM_ID", currentItem.getId());

                /*
                intent.putExtra("ITEM_NAME", currentItem.getName());
                intent.putExtra("ITEM_DESC", currentItem.getDescription());
                intent.putExtra("ITEM_TYPE", currentItem.getType());
                intent.putExtra("ITEM_PRICE", currentItem.getPrice());
                intent.putExtra("ITEM_AMOUNT", currentItem.getQuantity());

                 */
                context.startActivity(intent);

            //}
        });

        return listItem;
    }

    void addItem(Item temp) {
        if (temp.quantity > 0) {
            boolean exists = false;
            for (int i = 0; i < cart.size(); i++) {
                if (cart.get(i).id == temp.id) {
                    cart.get(i).setQuantity(cart.get(i).quantity + 1);
                    exists = true;
                }
            }
            if (!exists) {
                cart.add(new Item(temp.id, temp.name, temp.description, temp.type, temp.price, 1));
            }
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.get(i).id == temp.id) {
                    inventory.get(i).setQuantity(inventory.get(i).quantity - 1);
                }
            }
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Cannot add item", Toast.LENGTH_SHORT).show();
        }
    }

    void removeItem(Item temp) {
        boolean exists = false;
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).id == temp.id) {
                cart.get(i).setQuantity(cart.get(i).quantity - 1);
                if (cart.get(i).quantity == 0) {
                    cart.remove(cart.get(i));
                }
                exists = true;
            }
        }
        if (exists) {
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.get(i).id == temp.id) {
                    inventory.get(i).setQuantity(inventory.get(i).quantity + 1);
                }
            }
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "No item to remove", Toast.LENGTH_SHORT).show();
        }
    }
}