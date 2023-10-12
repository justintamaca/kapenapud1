package com.example.kapenapud1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context , List<Product> products) {
        this.context = context;
        this.products = products;
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

        holder.productNameTextView.setText(product.getName());
        holder.productPriceTextView.setText(String.valueOf(product.getPrice()));
        holder.productDescriptionTextView.setText(product.getDescription());

        // Load the product image using Glide
        Glide.with(context)
                .load(product.getImage()) // Replace with the actual image URL from your data
                .placeholder(R.drawable.logo) // Placeholder image while loading
                .error(R.drawable.logo) // Image to display in case of error
                .into(holder.productImageView);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productimage);
            productNameTextView = itemView.findViewById(R.id.productname);
            productPriceTextView = itemView.findViewById(R.id.productPrice);
            productDescriptionTextView = itemView.findViewById(R.id.productdesc);
        }
    }
}
