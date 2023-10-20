package com.example.kapenapud1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SelectedProductsAdapter extends RecyclerView.Adapter<SelectedProductsAdapter.ViewHolder> {
    private Context context;
    private List<Product> selectedProducts;
    private List<Integer> selectedQuantities; // Add this field

    public SelectedProductsAdapter(Context context, List<Product> selectedProducts) {
        this.context = context;
        this.selectedProducts = selectedProducts;
       // Initialize the quantities
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.selected_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = selectedProducts.get(position);

        // Update the item views with the product details and quantity
        holder.productNameTextView.setText(product.getName());
        holder.productPriceTextView.setText("Price: ₱" + product.getPrice());
        holder.productQuantityTextView.setText("Quantity: " + product.getQuantity()); // Display the quantity from selectedQuantities
    }

    @Override
    public int getItemCount() {
        return selectedProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView productPriceTextView;
        TextView productQuantityTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            productQuantityTextView = itemView.findViewById(R.id.quantity); // Make sure the ID matches your XML
        }
    }
}
