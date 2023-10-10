package com.example.kapenapud1;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("categories")
    Call<List<Category>> getCategories();

    @GET("products")
    Call<List<Product>> getProducts();

    @FormUrlEncoded
    @POST("users") // Update the endpoint to "users"
    Call<RegistrationResponse> registerUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("role_id") int roleId
    );

    @FormUrlEncoded
    @POST("users")
    Call<LoginResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );


}
