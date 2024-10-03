package com.ph45308.assignment_ph45308.Cart

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ph45308.assignment_ph45308.Cart.ui.theme.Assignment_PH45308Theme
import com.ph45308.assignment_ph45308.MyTopBar
import com.ph45308.assignment_ph45308.R

class Cart : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment_PH45308Theme {
                Scaffold(
                    topBar = {
                        MyTopBar(title = "Giỏ Hàng")
                    }
                ) {
                    PreviewCart()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartScreen() {
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
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color(0xFFFFFFFF)), // white background color
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                EmptyCart()
            }
        }
    }
}


@Composable
fun EmptyCart() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_cart),
            contentDescription = "img cart",
            modifier = Modifier
                .size(100.dp),
            colorFilter = ColorFilter.tint(Color.Black)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Không có đơn hàng!",
            color = Color.Red,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
//                .background(Color(0xFFC9CACC))
            ,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFC9CACC))
        ) {
            Text(
                text = "Đặt hàng",
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewCart() {
    Assignment_PH45308Theme {
            CartScreen()
    }
}