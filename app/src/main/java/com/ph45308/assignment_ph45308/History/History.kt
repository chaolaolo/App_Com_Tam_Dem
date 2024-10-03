package com.ph45308.assignment_ph45308.History

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ph45308.assignment_ph45308.History.ui.theme.Assignment_PH45308Theme
import com.ph45308.assignment_ph45308.MyTopBar


data class Order(
    val status: String,
    val date: String,
    val time: String,
    val items: String,
    val price: String,
)


class History : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment_PH45308Theme {
                PreviewHistory()
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HistoryScreen() {
    val orders = listOf(
        Order("Đơn hàng đã chấp nhận", "10/03/2023", "9:20", "3 món", "98k"),
        Order("Đơn hàng đã bị huỷ", "10/03/2023", "9:20", "3 món", "98k"),
        Order("Đơn hàng đã được giao", "10/03/2023", "9:20", "3 món", "98k"),
    )
    Scaffold(
        topBar = {
            MyTopBar(
                title = "Giỏ Hàng",
//                navigationIcon = {
//                    IconButton(onClick = { /* Handle navigation */ },
//                        modifier = Modifier.size(100.dp)) {
//                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back")
//                    }
//                },
//                actions = {
//                    IconButton(onClick = { /* Handle action 1 */ }) {
//                        Icon(Icons.Default.Search, contentDescription = "Search")
//                    }
//                    IconButton(onClick = { /* Handle action 2 */ }) {
//                        Icon(Icons.Default.Settings, contentDescription = "Settings")
//                    }
//                }
            )
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Order list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(orders) { order ->
                    OrderItem(order)
                }
            }
        }
    }
}

@Composable
fun OrderItem(order: Order) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEAEBEA), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Status (with color conditional styling)
            Text(
                text = order.status,
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Date and Time
            Text(
                text = "${order.date}  ${order.time}",
                color = Color.Black,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Items count and price
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = order.items,
                color = Color.Black,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = order.price,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHistory() {
    Assignment_PH45308Theme {

        HistoryScreen()
    }
}