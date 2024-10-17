package com.ph45308.assignment_ph45308.Screens.Manage

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ph45308.assignment_ph45308.Screens.Manage.ui.theme.Assignment_PH45308Theme
import com.ph45308.assignment_ph45308.Model.Product
import com.ph45308.assignment_ph45308.MyTopBar
import com.ph45308.assignment_ph45308.ViewModel.ProductViewModel

class ManageProduct : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment_PH45308Theme {
//                PreviewManageProduct()
                ManagerProductScreen(rememberNavController())
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ManagerProductScreen(
    navController: NavController,
    viewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var searchQuery by remember { mutableStateOf("") }

    var showConfirmDelete by remember { mutableStateOf(false) }
    var prodToDelete by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Scaffold(
        topBar = {
            MyTopBar(title = "Quản lý món ăn")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("AddProductScreen")
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add icon"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color(0xFFFFFFFF))
                .padding(paddingValues),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                item {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { query ->
                            searchQuery = query
                            viewModel.searchProducts(query)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Search Products") },
                        leadingIcon = { androidx.compose.material.Icon(Icons.Default.Search, contentDescription = null) }
                    )
                }
                items(viewModel.filteredProductList.value) { product ->
                    ManageProdItem(product = product,
                        navController = navController,
                        onDeleteClick = {
                            prodToDelete = product
                            showConfirmDelete = true
                        })
                }

            }
        }


        if (showConfirmDelete && prodToDelete != null) {
            AlertDialog(onDismissRequest = {
                showConfirmDelete = false
                prodToDelete = null
            },
                title = { Text(text = "Xác Nhận Xóa?") },
                text = { Text(text = "Bạn có chắc chắn xóa món ăn này không?") },
                confirmButton = {
                    TextButton(onClick = {
                        prodToDelete?.let { viewModel.deleteProduct(it._id) }
                        showConfirmDelete = false
                        prodToDelete = null
                    }) {
                        Text(text = "Xóa")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showConfirmDelete = false
                        prodToDelete = null
                    }) {
                        Text(text = "Không")
                    }
                }
            )
        }


    }
}


@Composable
fun ManageProdItem(
    product: Product,
    navController: NavController,
    viewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onDeleteClick: () -> Unit,
) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 2.dp)
            .clickable {
                navController.navigate("ProductDetailScreen/${product._id}")
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.image_url)
                    .crossfade(true)
                    .build(),
                contentDescription = "Product image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,

                )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = product.price.toString(),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxHeight()
            ) {
                IconButton(
                    onClick = {
                        navController.navigate("EditProductScreen/${product._id}")
                    },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit icon",
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                IconButton(
                    onClick = {
                        onDeleteClick()
                    },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete icon",
                    )
                }
            }


        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewManageProduct() {
    Assignment_PH45308Theme {
        ManagerProductScreen(rememberNavController())
    }
}