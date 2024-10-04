package com.ph45308.assignment_ph45308

import com.ph45308.assignment_ph45308.History.Order
import com.ph45308.assignment_ph45308.Model.Cart
import com.ph45308.assignment_ph45308.Model.LoginResponse
import com.ph45308.assignment_ph45308.Model.Product
import com.ph45308.assignment_ph45308.Model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("products/getProduct")
    suspend fun getProducts(): List<Product>

    @POST("products/addProduct")
    suspend fun addProduct(@Body product: Product): Product

    @PUT("products/editProduct/{id}")
    suspend fun editProduct(@Path("id") id: String, @Body product: Product): Product

    @DELETE("products/deleteProduct/{id}")
    suspend fun deleteProduct(@Path("id") id: String): Unit

    @GET("products/getProductDetail/{productId}")
    suspend fun getProductDetail(@Path("productId") productId: String): Product

    @POST("products/addToCart")
    suspend fun addToCart(@Body cart: Cart): Cart

    @GET("products/getCart")
    suspend fun getCart(): List<Cart>

    @POST("products/checkout")
    suspend fun checkout(@Body order: Order): Order

    // User API
    @POST("users/register")
    suspend fun registerUser(@Body user: User): User

    @POST("users/login")
    suspend fun login(@Body user: User): LoginResponse



}