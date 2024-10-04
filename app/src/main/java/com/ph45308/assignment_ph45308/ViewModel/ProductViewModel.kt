package com.ph45308.assignment_ph45308.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph45308.assignment_ph45308.ApiService
import com.ph45308.assignment_ph45308.Model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductViewModel: ViewModel() {
    var productList= mutableStateOf<List<Product>>(emptyList())
        private set

    var selectedProduct = mutableStateOf<Product?>(null)
        private set

    init {
        fetchProducts()
    }
    private var BASE_URL = "http://192.168.1.8:3000/"

    private fun fetchProducts() {
        viewModelScope.launch (Dispatchers.IO){
            val retrofit=Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService=retrofit.create(ApiService::class.java)

            try {
                val products= apiService.getProducts()
                productList.value=products
            }catch (e:Exception){
                productList.value= emptyList()
            }
        }
    }

    // Hàm để lấy chi tiết sản phẩm
    fun fetchProductDetail(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)

            try {
                val product = apiService.getProductDetail(productId)
                selectedProduct.value = product
            } catch (e: Exception) {
                selectedProduct.value = null
            }
        }
    }


}