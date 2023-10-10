package com.example.kapenapud1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private RecyclerView productRecyclerView;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;
    private static final String BASE_URL = "https://kapenapud.com/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerViews
        categoryRecyclerView = findViewById(R.id.categories_recyclerview);
        productRecyclerView = findViewById(R.id.product_recyclerview);

        // Set layout managers
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize empty data for adapters
        List<Category> categories = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        // Initialize adapters with empty data
        categoryAdapter = new CategoryAdapter(categories);
        productAdapter = new ProductAdapter(this, products);

        categoryRecyclerView.setAdapter(categoryAdapter);
        productRecyclerView.setAdapter(productAdapter);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Fetch Categories
        Call<List<Category>> categoryCall = apiService.getCategories();
        categoryCall.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    List<Category> newCategories = response.body();
                    categoryAdapter.setData(newCategories); // Update data in CategoryAdapter
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                // Handle failure
            }
        });

        // Fetch Products
        Call<List<Product>> productCall = apiService.getProducts();
        productCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> newProducts = response.body();
                    productAdapter.setData(newProducts); // Update data in ProductAdapter
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
