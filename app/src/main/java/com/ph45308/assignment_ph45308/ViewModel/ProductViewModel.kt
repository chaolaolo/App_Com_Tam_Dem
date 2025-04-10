package com.ph45308.assignment_ph45308.ViewModel

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph45308.assignment_ph45308.APIServices.RetrofitClient
import com.ph45308.assignment_ph45308.Model.Cart
import com.ph45308.assignment_ph45308.Model.Category
import com.ph45308.assignment_ph45308.Model.Order
import com.ph45308.assignment_ph45308.Model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

@SuppressLint("StaticFieldLeak")
class ProductViewModel: ViewModel() {
    var productList= mutableStateOf<List<Product>>(emptyList())
        private set
    var categoryList = mutableStateOf<List<Category>>(emptyList())
        private set

    var selectedProduct = mutableStateOf<Product?>(null)
        private set

    var filteredProductList = mutableStateOf<List<Product>>(emptyList())
        private set

    var orderList = mutableStateOf<List<Order>>(emptyList())
        private set

    var searchQuery = mutableStateOf("")

    var cartItems = mutableStateOf<List<Cart>>(emptyList())


    init {
        fetchProducts()
        fetchCategories()
    }

    fun fetchProducts() {
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

    //add product
    // Hàm thêm sản phẩm
    fun addProduct(product: Product, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.addProduct(product)
                if (response != null) {
                    Log.d("ProductViewModel", "Add product success: ${response.name}")
                    onSuccess()
                } else {
                    onError("Add product failed")
                }
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error adding product: ${e.message}")
                onError("Error adding product: ${e.message}")
            }
        }
    }

    //edit product
    fun editProduct(productId: String, updatedProduct: Product, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.editProduct(productId, updatedProduct)
                if (response != null) {
                    Log.d("ProductViewModel", "Edit product success: ${response.name}")
                    onSuccess()
                } else {
                    onError("Edit product failed")
                }
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error editing product: ${e.message}")
                onError("Error editing product: ${e.message}")
            }
        }
    }

    //Delete
    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.deleteProduct(productId)
                productList.value = productList.value.filter { it._id != productId }
                filteredProductList.value = filteredProductList.value.filter { it._id != productId }
                Log.d("ProductViewModel", "Product deleted successfully with ID: $productId")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ProductViewModel", "Error deleting product: ${e.message}")
            }
        }
    }

    //Seacrh
    fun searchProducts(query: String) {
        searchQuery.value = query

        viewModelScope.launch(Dispatchers.IO) {
            try {
                filteredProductList.value = if (query.isNotEmpty()) {
                    productList.value.filter { product ->
                        product.name.contains(query, ignoreCase = true) // Tìm kiếm theo tên sản phẩm
                    }
                } else {
                    productList.value
                }
            } catch (e: Exception) {
                filteredProductList.value = emptyList()
            }
        }
    }

    fun filterProductsByCategory(categoryId: String) {
        if (categoryId == null) {
            println("Category ID is null")
            return
        }
        if (categoryId == "Tất cả") {
            filteredProductList.value = productList.value // Hiện tất cả sản phẩm
            return
        }
        val filteredProducts = productList.value.filter { product ->
            product.category.name == categoryId
        }
        filteredProductList.value = filteredProducts
    }

    ///Get Categories
    fun fetchCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val categories = RetrofitClient.apiService.getCategories()
                categoryList.value = categories
            } catch (e: Exception) {
                categoryList.value = emptyList()
            }
        }
    }

    // Add Category
    fun addCategory(newCategory: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.addCategory(newCategory)
                if (response != null) {
                    Log.d("TAG", "Add category success: ${response.name}")
                    fetchCategories() // Cập nhật lại danh sách categories sau khi thêm mới thành công
                } else {
                    Log.e("TAG", "addCategory: Add category failed")
                }
            } catch (e: Exception) {
                Log.e("TAG", "Error adding category: ${e.message}")
                Log.e("TAG", "Error adding category: ${e.message}")
            }
        }
    }

    // Edit Category
    fun editCategory(categoryId: String, updatedCategory: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.editCategory(categoryId, updatedCategory)
                if (response != null) {
                    Log.d("TAG", "Edit category success: ${response.name}")
                    fetchCategories()
                } else {
                    Log.d("TAG", "editCategory: Edit category failed")
                }
            } catch (e: Exception) {
                Log.e("TAG", "Error editing category: ${e.message}")
                Log.e("TAG", "editCategory: Error editing category: ${e.message}")
            }
        }
    }

    // Delete Category
    fun deleteCategory(categoryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                RetrofitClient.apiService.deleteCategory(categoryId)
                categoryList.value = categoryList.value.filter { it._id != categoryId }
                Log.d("ProductViewModel", "Category deleted successfully with ID: $categoryId")
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error deleting category: ${e.message}")
                Log.e("TAG", "Error deleting category: ${e.message}")
            }
        }
    }

    //getTokenFromSharedPreferences
    fun getTokenFromSharedPreferences(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPref.getString("token", null)
    }

    //addToCart
    fun addToCart(context: Context, token: String, productId: String, quantity: Int) {
        val url = "http://192.168.1.14:3000/products/addToCart"
//        val url = "http://10.24.55.42:3000/products/addToCart"
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
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    fetchCart(context)
                    Log.d("TAG", "addToCart: Product added to cart successfully: ${response.body?.string()}")
                } else {
                    Log.d("TAG", "addToCart: Failed to add product to cart: ${response.message}")
                }
            } catch (e: Exception) {
                Log.d("TAG", "addToCart: Error: ${e.localizedMessage}")
            }
        }
    }//end

    //fetchCart
    fun fetchCart(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = getTokenFromSharedPreferences(context)

                if (token != null) {
                    val items = RetrofitClient.apiService.getCart("Bearer $token")
                    cartItems.value = items

                    for (item in items) {
                        Log.d("TAG", "Product ID: ${item.product._id}, Quantity: ${item.quantity}")
                    }
                } else {
                    Log.d("TAG", "No authentication token found")
                    cartItems.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("TAG", "Error fetching cart: ${e.message}")
                cartItems.value = emptyList()
            }
        }
    }

    fun ClearCart(context: Context) {
        viewModelScope.launch {
            try {
                val token = getTokenFromSharedPreferences(context)
                if (token != null) {
                    val response = RetrofitClient.apiService.clearCart("Bearer $token")
                    if (response.isSuccessful) {
                        cartItems.value = emptyList()
                        Log.d("TAG", "ClearCart: Clear Cart Successfully!")
                    } else {
                        Log.d("TAG", "clearCart: Failed to clear cart: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                Log.e("TAG", "ClearCart: err to clearing cart: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }

    //tăng giảm số lượng
    fun increaseQuantity(cart: Cart) {
        // Tăng số lượng sản phẩm trong giỏ hàng
        cartItems.value = cartItems.value.map {
            if (it.product._id == cart.product._id) {
                it.copy(quantity = it.quantity + 1)
            } else it
        }
    }

    fun decreaseQuantity(cart: Cart) {
        // Giảm số lượng sản phẩm trong giỏ hàng nhưng không dưới 1
        cartItems.value = cartItems.value.map {
            if (it.product._id == cart.product._id && it.quantity > 1) {
                it.copy(quantity = it.quantity - 1)
            } else it
        }
    }


    //Checkout
// Checkout
    fun checkout(context: Context, order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = getTokenFromSharedPreferences(context)
                if (token != null) {
                    val response = RetrofitClient.apiService.checkout("Bearer $token", order)
                    if (response.isSuccessful) {
                        // Clear the cart items
                        cartItems.value = emptyList()
                        Log.d("TAG", "Checkout successful: ${response.body()?.toString()}")
                    } else {
                        Log.d("TAG", "Checkout failed: ${response.message()}")
                    }
                } else {
                    Log.d("TAG", "No authentication token found")
                }
            } catch (e: Exception) {
                Log.e("TAG", "Error during checkout: ${e.message}")
            }
        }
    }


    fun getOrders(context: Context) {
        viewModelScope.launch {
            try {
                val token = getTokenFromSharedPreferences(context)
                if (token != null) {
                    val orders = RetrofitClient.apiService.getOrder("Bearer $token")
                    orderList.value = orders
                    Log.d("TAG", "Orders fetched successfully: $orders")
                }
            } catch (e: Exception) {
                Log.e("TAG", "Error fetching orders: ${e.message}")
                orderList.value = emptyList()
            }
        }
    }

}