package com.example.kapenapud1;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("categories")
    Call<CategoryResponse> getCategories();

    @GET("products")
    Call<ProductResponse> getProducts();
    Call<ProductResponse> getProductsByCategory(@Query("category") String category);

    @GET("users")
    Call<UserResponse> getUserData();

    @GET("customers")
    Call<CustomerResponse> getCustomers();

    @GET("payment")
    Call<PaymentResponse> getPaymentOptions();

    @FormUrlEncoded
    @POST("register") // Update the endpoint to "users"
    Call<RegistrationResponse> registerUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("role_id") int roleId
    );

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("order/checkout")
    Call<CheckoutResponse> checkoutOrder(@Body CheckoutRequest checkoutRequest);


}
