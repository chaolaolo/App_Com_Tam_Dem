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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ph45308.assignment_ph45308.Screens.Manage.ui.theme.Assignment_PH45308Theme
import com.ph45308.assignment_ph45308.Model.Category
import com.ph45308.assignment_ph45308.Model.Product
import com.ph45308.assignment_ph45308.MyTopBar
import com.ph45308.assignment_ph45308.ViewModel.ProductViewModel

class ManageCategory : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment_PH45308Theme {
                PreviewManageCategory()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ManagerCategoryScreen(
    navController: NavController,
    viewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(true) }
    var categoryName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
    }

    Scaffold(
        topBar = {
            MyTopBar(title = "Quản lý loại món ăn")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showAddDialog = true
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
                items(viewModel.categoryList.value) { category ->
                    CategoryItem(
                        category = category,
                        onEditClick = {
                            selectedCategory = category
                            categoryName = category.name
                            showEditDialog = true
                        },
                        onDeleteClick = {
                            selectedCategory = category
                            showDeleteDialog = true
                        }
                    )
                }
            }

            ///Show Add
            if (showAddDialog) {
                AddCategoryDialog(
                    name = categoryName,
                    onNameChange = { categoryName = it },
                    onDismiss = { showAddDialog = false },
                    onConfirm = {
                        // Gọi hàm thêm danh mục khi nhấn "Thêm"
                        viewModel.addCategory(
                            Category(_id = "", name = categoryName),
                        )
                        showAddDialog = false
                    }
                )
            }

            //Show Edit
            if (showEditDialog && selectedCategory != null) {
                EditCategoryDialog(
                    category = selectedCategory!!,
                    newCategoryName = categoryName,
                    onNameChange = { categoryName = it },
                    onDismiss = { showEditDialog = false },
                    onConfirm = {
                        selectedCategory?.let {
                            val updatedCategory = it.copy(name = categoryName)
                            viewModel.editCategory(
                                it._id,
                                updatedCategory,
                            )
                            showEditDialog = false
                        }
                    }
                )
            }

            if (showDeleteDialog && selectedCategory != null) {
                androidx.compose.material3.AlertDialog(
                    onDismissRequest = {
                        showDeleteDialog = false
                        selectedCategory = null
                    },
                    title = { Text(text = "Xác Nhận Xóa?") },
                    text = { Text(text = "Bạn có chắc chắn thể loại này không?") },
                    confirmButton = {
                        TextButton(onClick = {
                            selectedCategory?.let { viewModel.deleteCategory(it._id) }
                            showDeleteDialog = false
                            selectedCategory = null
                        }) {
                            Text(text = "Xóa")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDeleteDialog = false
                            selectedCategory = null
                        }) {
                            Text(text = "Không")
                        }
                    }
                )
            }

        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 2.dp)
            .height(50.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp, 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row {
                IconButton(
                    onClick = {
                        onEditClick()
                    },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit icon",
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
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


@Composable
fun AddCategoryDialog(
    name: String,
    onNameChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Thêm danh mục mới",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column {
                Text(text = "Nhập tên danh mục:")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    placeholder = { Text(text = "Tên danh mục") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Thêm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Hủy")
            }
        }
    )
}


@Composable
fun EditCategoryDialog(
    category: Category,
    newCategoryName: String,
    onNameChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Chỉnh sửa danh mục") },
        text = {
            Column {
                Text(text = "Tên danh mục hiện tại: ${category.name}")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = newCategoryName,
                    onValueChange = onNameChange,
                    placeholder = { Text(text = "Tên danh mục") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Lưu")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Hủy")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewManageCategory() {
    Assignment_PH45308Theme {
        ManagerCategoryScreen(rememberNavController())
    }
}