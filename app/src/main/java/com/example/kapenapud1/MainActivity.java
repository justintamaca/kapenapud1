package com.example.kapenapud1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout categoriesLayout;
    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoriesLayout = findViewById(R.id.buttonlayout);
        productRecyclerView = findViewById(R.id.productsrecyclerview);

        BottomNavigationView bottomNavigationView = findViewById(R.id.botNatView);
        bottomNavigationView.setSelectedItemId(R.id.bot_menu);

        productAdapter = new ProductAdapter(this, new ArrayList<>());
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productRecyclerView.setAdapter(productAdapter);

        // Initialize Retrofit and API service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://kapenapud.com/api/") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Fetch and display categories
        Call<CategoryResponse> call = apiService.getCategories();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CategoryResponse categoryResponse = response.body();
                    List<Category> categories = categoryResponse.getData();
                    displayCategories(categories);
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private int selectedCategory = -1; // Initialize as -1 to represent no selection

    private void displayCategories(List<Category> categories) {
        if (categories.size() >= 10) {
            // Update text of buttons for Category 1 to 10 and set click listeners
            for (int i = 0; i < 10; i++) {
                Button categoryButton = findViewById(getResources().getIdentifier("button" + (i + 1), "id", getPackageName()));
                categoryButton.setText(categories.get(i).getName());

                final int categoryId = categories.get(i).getId(); // Make it final

                categoryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (categoryId != selectedCategory) {
                            // Deselect the previously selected category
                            if (selectedCategory != -1) {
                                Button prevCategoryButton = findViewById(getResources().getIdentifier("button" + selectedCategory, "id", getPackageName()));
                                prevCategoryButton.setBackgroundColor(Color.WHITE); // Change it back to the default color
                            }

                            // Set the background color to the selected color
                            categoryButton.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.cream_500));

                            // Fetch products for the selected category
                            fetchProductsForCategory(categoryId);

                            selectedCategory = categoryId; // Update the selected category
                        }
                    }
                });
            }
        }
    }


    private void fetchProductsForCategory(int categoryId) {
        // Fetch products based on the selected category
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://kapenapud.com/api/") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ProductResponse> call = apiService.getProducts();

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductResponse productResponse = response.body();
                    List<Product> allProducts = productResponse.getData(); // Get the list of products from ProductResponse
                    List<Product> productsForCategory = filterProductsByCategory(allProducts, categoryId);
                    productAdapter.setProducts(productsForCategory);
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private List<Product> filterProductsByCategory(List<Product> products, int categoryId) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory_id() == categoryId) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }
}
