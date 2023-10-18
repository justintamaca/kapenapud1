package com.example.kapenapud1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context context;
    private List<Product> products;
    private List<Product> selectedProducts;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
        this.selectedProducts = new ArrayList<>(); // Initialize the selectedProducts list
    }

    public void setProducts(List<Product> newProducts) {
        products.clear();
        products.addAll(newProducts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);

        // Bind other product data (e.g., name, price, etc.) to your views
        holder.productNameTextView.setText(product.getName());
        float price = Float.parseFloat(product.getPrice());
        String formattedPrice = "₱" + (int) price; // Assuming the price is stored as a string
        holder.productPriceTextView.setText(formattedPrice);
        holder.productDescriptionTextView.setText(product.getDescription());
        // Load product image using Glide
        String imageUrl = "https://kapenapud.com/storage/" + product.getImage(); // Assuming product.getImageUrl() returns the image filename
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.logo) // Optional: Placeholder image while loading
                .error(R.drawable.logo) // Optional: Image to show in case of error
                .into(holder.productImageView);

        // Handle click on "Add to Cart" button
        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Example product information (replace with actual product details)
                int productId = product.getId();
                String productName = product.getName();
                double productPrice = Double.parseDouble(product.getPrice()); // Assuming the price is stored as a string

                // Create a CartItem with initial quantity
                CartItem cartItem = new CartItem(productId, productName, productPrice, 1);

                // Add the cart item to the cartItems list or a shared data storage
                // Assuming you have access to the cartItems list or a data storage
                Cart.getInstance().addItem(product); // Assuming you have a Cart class

                // Notify that the cart has been updated
                notifyDataSetChanged();

                // Show a "Added to Cart" message
                Toast.makeText(context, "Added to Cart: " + productName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Add a method to get the selected products (cart items)
    public List<Product> getSelectedProducts() {
        return selectedProducts;
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageView;
        TextView productNameTextView;
        TextView productPriceTextView;
        TextView productDescriptionTextView;
        Button addToCartButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productimage);
            productNameTextView = itemView.findViewById(R.id.productname);
            productPriceTextView = itemView.findViewById(R.id.productPrice);
            productDescriptionTextView = itemView.findViewById(R.id.productdesc);
            addToCartButton = itemView.findViewById(R.id.addToCartButton); // Initialize the button
        }
    }
}

