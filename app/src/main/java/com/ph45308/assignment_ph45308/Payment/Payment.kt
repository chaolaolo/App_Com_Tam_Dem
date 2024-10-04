package com.ph45308.assignment_ph45308.Payment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ph45308.assignment_ph45308.Component.PaymentOption
import com.ph45308.assignment_ph45308.MyTopBar
import com.ph45308.assignment_ph45308.Payment.ui.theme.Assignment_PH45308Theme
import com.ph45308.assignment_ph45308.R

class Payment : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment_PH45308Theme {

                GreetingPreview()

            }
        }
    }
}

@Composable
fun PaymentScreen() {
    Scaffold(
        topBar = {
            MyTopBar(title = "Thanh Toán")
        },
        bottomBar = {
            BottomCart()
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                //Thông tin cá nhân
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "Thông tin người nhận",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Icon(
                                imageVector = Icons.Default.AccountBox,
                                contentDescription = "Icon Name",
                            )
                            Text(
                                text = "Chao lao lo",
                                fontSize = 18.sp,
                            )
                        }
                        Row {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Icon phone",
                            )
                            Text(
                                text = "0987654321",
                                fontSize = 18.sp,
                            )
                        }
                        Row {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Icon Address",
                                tint = Color.Red
                            )
                            Text(
                                text = "Sapa - Lào Cai",
                                fontSize = 18.sp,
                            )
                        }


                    }
                }

                //Thông tin thanh toán
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "Chọn phương thức thanh toán",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        var selectedPayment by remember { mutableStateOf("") }
                        PaymentOption(
                            iconRes = R.drawable.img_cashpay,
                            optionName = "Thanh toán trực tiếp",
                            isSelected = selectedPayment == "Thanh toán trực tiếp",
                            onClick = { selectedPayment = "Thanh toán trực tiếp" }
                        )
                        PaymentOption(
                            iconRes = R.drawable.img_zalopay,
                            optionName = "Zalo Pay",
                            isSelected = selectedPayment == "Zalo Pay",
                            onClick = { selectedPayment = "Zalo Pay" }
                        )
                        PaymentOption(
                            iconRes = R.drawable.img_momo,
                            optionName = "Momo",
                            isSelected = selectedPayment == "Momo",
                            onClick = { selectedPayment = "Momo" }
                        )
//
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Món ăn của bạn",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                        ) {
                            item { Text(text = "Hiện danh sách món ăn chính ở đây") }
                        }
                    }
                }


            }

        }
    }
}

@Composable
fun BottomCart() {
    //Thanh toán
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Column(
                Modifier.weight(1f)
            ) {
                Text(
                    text = "Tổng tiền:",
                    fontSize = 16.sp,
                )
                Row {
                    Text(
                        text = "$",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                    Text(
                        text = "100.00",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Button(
                onClick = { /*TODO*/ },
                Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color(0xFFFE724C)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(text = "Thanh Toán")
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    Assignment_PH45308Theme {
        PaymentScreen()
    }
}