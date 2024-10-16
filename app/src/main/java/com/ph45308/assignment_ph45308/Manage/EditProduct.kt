package com.ph45308.assignment_ph45308.Manage

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ph45308.assignment_ph45308.Component.ProductCategoryDropdown
import com.ph45308.assignment_ph45308.Manage.ui.theme.Assignment_PH45308Theme
import com.ph45308.assignment_ph45308.Model.Category
import com.ph45308.assignment_ph45308.Model.Product
import com.ph45308.assignment_ph45308.MyTopBar
import com.ph45308.assignment_ph45308.ViewModel.ProductViewModel

class EditProduct : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment_PH45308Theme {
                EditProductScreen("", rememberNavController())
            }
        }
    }
}

@Composable
fun EditProductScreen(
    productId: String,
    navController: NavController,
    viewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {

    LaunchedEffect(productId) {
        viewModel.fetchProductDetail(productId)
    }

    Scaffold(
        topBar = {
            MyTopBar(
                title = "Chỉnh sửa món ăn",
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier.size(100.dp)
                    ) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back")
                    }
                },
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(paddingValues)
                .background(Color(0xFFFFFFFF)), // white background color
//        contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                BodyEdit(navController, viewModel, productId)
            }
        }
    }
}

@Composable
fun BodyEdit(navController: NavController, viewModel: ProductViewModel, productId: String) {
    val product by viewModel.selectedProduct
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    val categories = viewModel.categoryList.value
    val context = LocalContext.current

    LaunchedEffect(product) {
        product?.let {
            name = it.name
            price = it.price.toString()
            quantity = it.quantity.toString()
            image = it.image_url
            description = it.description
            selectedCategory = it.category
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Image(
//            painter = painterResource(id = R.drawable.add_image),
//            contentDescription = "Add image",
//            modifier = Modifier
//                .size(150.dp)
//        )
        item {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Tên món ăn") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 2.dp)
            )
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text(text = "Đơn giá") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 2.dp)
            )
            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text(text = "Số lượng") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 2.dp)
            )
            OutlinedTextField(
                value = image,
                onValueChange = { image = it },
                label = { Text(text = "Ảnh món ăn") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 2.dp)
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(text = "Mô tả") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 2.dp)
            )

            ProductCategoryDropdown(categories = categories) { selectedCate ->
                // Handle category selection
                selectedCate?.let {
                    viewModel.filterProductsByCategory(it.name)
                    selectedCategory = selectedCate
                    println("Selected category: ${it._id}")
                }
            }
        }
        item {
            Button(
                onClick = {
                    if (name.isNotEmpty() && price.isNotEmpty() && quantity.isNotEmpty() &&
                        image.isNotEmpty() && description.isNotEmpty() && selectedCategory != null
                    ) {

                        val updatedProduct = Product(
                            _id = productId,
                            name = name,
                            price = price.toDoubleOrNull() ?: 0.0,
                            quantity = quantity.toIntOrNull() ?: 0,
                            image_url = image,
                            description = description,
                            category = selectedCategory!!
                        )

                        viewModel.editProduct(
                            productId = productId,
                            updatedProduct = updatedProduct,
                            onSuccess = {
//                                Toast.makeText(context, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show()
//                                navController.popBackStack()
                                Log.d("TAG", "BodyEdit: Sửa sản phẩm thành công")
                            },
                            onError = { errorMessage ->
//                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                                Log.d("TAG", "BodyEdit: Sửa sản phẩm thất bại" + errorMessage)
                            }
                        )
                        navController.popBackStack()

                    } else {
                        Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFC9CACC))
            ) {
                Text(
                    text = "Sửa",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewManager() {
    Assignment_PH45308Theme {
        EditProductScreen("", rememberNavController())
    }
}