package com.example.kapenapud1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<CartItem> cartItems;
    private CartActivity cartActivity;

    public CartAdapter(Context context, List<CartItem> cartItems, CartActivity cartActivity) {
        this.context = context;
        this.cartItems = cartItems;
        this.cartActivity = cartActivity;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cartitems, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        ImageButton plusButton = holder.itemView.findViewById(R.id.plusButton);
        ImageButton minusButton = holder.itemView.findViewById(R.id.minusButton);
        TextView quantityTextView = holder.itemView.findViewById(R.id.quantitycart);

        CartItem cartItem = cartItems.get(position);
        holder.productNameTextView.setText(cartItem.getProductName());
        holder.quantityTextView.setText("Quantity: " + cartItem.getQuantity());

        quantityTextView.setText(String.valueOf(cartItem.getQuantity()));

        // Handle the plus button click
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newQuantity = cartItem.getQuantity() + 1;
                cartItem.setQuantity(newQuantity);
                quantityTextView.setText(String.valueOf(newQuantity));
                cartActivity.updatePrices(); // Call the method in CartActivity to update prices
            }
        });

        // Handle the minus button click
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newQuantity = cartItem.getQuantity() - 1;
                if (newQuantity >= 0) {
                    cartItem.setQuantity(newQuantity);
                    quantityTextView.setText(String.valueOf(newQuantity));
                    cartActivity.updatePrices(); // Call the method in CartActivity to update prices
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        public TextView productNameTextView;
        public TextView quantityTextView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productnamecart);
            quantityTextView = itemView.findViewById(R.id.quantitycart);
        }
    }
}