package com.ph45308.assignment_ph45308.ViewModel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph45308.assignment_ph45308.APIServices.ApiService
import com.ph45308.assignment_ph45308.APIServices.RetrofitClient
import com.ph45308.assignment_ph45308.APIServices.RetrofitClient.apiService
import com.ph45308.assignment_ph45308.Model.Cart
import com.ph45308.assignment_ph45308.Model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query
import kotlin.math.log

class ProductViewModel: ViewModel() {
    var productList= mutableStateOf<List<Product>>(emptyList())
        private set

    var selectedProduct = mutableStateOf<Product?>(null)
        private set

    var filteredProductList = mutableStateOf<List<Product>>(emptyList())
        private set

    var searchQuery = mutableStateOf("")

    var cartItems = mutableStateOf<List<Cart>>(emptyList())



    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch (Dispatchers.IO){

            try {
                val products = RetrofitClient.apiService.getProducts()
                productList.value=products
                filteredProductList.value = products
            }catch (e:Exception){
                productList.value= emptyList()
                filteredProductList.value = emptyList()
            }
        }
    }

    // Hàm để lấy chi tiết sản phẩm
    fun fetchProductDetail(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val product = RetrofitClient.apiService.getProductDetail(productId)
                selectedProduct.value = product
            } catch (e: Exception) {
                selectedProduct.value = null
            }
        }
    }

    fun filterProductsByCategory(categoryId: String) {
        val filteredProducts = productList.value.filter { product ->
            product.category == categoryId
        }
        filteredProductList.value = filteredProducts
    }

    //Seacrh
    fun searchProducts(query: String) {
        searchQuery.value = query

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (query.isNotEmpty()) {
                    val searchResults = RetrofitClient.apiService.searchProducts(query)
                    filteredProductList.value = searchResults
                } else {
                    filteredProductList.value = productList.value
                }
            } catch (e: Exception) {
                filteredProductList.value = emptyList()
            }
        }
    }

    //Add to cart
    // Add product to cart
    fun getTokenFromSharedPreferences(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPref.getString("token", null)
    }

    fun addToCart(context: Context, token: String, productId: String, quantity: Int) {
         val url = "http://172.20.10.8:3000/products/addToCart"
//        val url = "http://192.168.1.8:3000/products/addToCart"
//        val url = "http://10.24.18.21:3000/products/addToCart"
        val jsonObject = JSONObject().apply {
            put("productId", productId)
            put("quantity", quantity)
        }

        val client = OkHttpClient()

        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json")
            .build()

        viewModelScope.launch(Dispatchers.IO) {
            try {
//                val cartItem = Cart(productId, quantity)
//                val response = RetrofitClient.apiService.addToCart(token, cartItem)
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    Log.d("TAG", "addToCart: Product added to cart successfully: ${response.body?.string()}")
                } else {
                    Log.d("TAG", "addToCart: Failed to add product to cart: ${response.message}")
                }
            } catch (e: Exception) {
                Log.d("TAG", "addToCart: Error: ${e.localizedMessage}")
            }
        }
    }


}