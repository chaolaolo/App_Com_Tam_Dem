package com.ph45308.assignment_ph45308.Account

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ph45308.assignment_ph45308.Account.ui.theme.Assignment_PH45308Theme
import com.ph45308.assignment_ph45308.R

class UpdateInformation : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            Assignment_PH45308Theme {
            PreviewUpdateInfor()
//            }
        }
    }
}

@Composable
fun UpdatInformation() {
    var phoneNumber by remember { mutableStateOf("") }
    var ward by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var houseNumber by remember { mutableStateOf("") }

    // Background color
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xFFFFFF)), // Dark background color
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // Image and Header Section
            Image(
                painter = painterResource(id = R.drawable.logo), // Replace with your image resource
                contentDescription = "Logo",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Hello ...",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = "Vui lòng cập nhật thông tin chính xác để thuận tiện cho việc giao hàng",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input fields
            CustomTextField(value = phoneNumber, label = "Số điện thoại",onValueChange = { phoneNumber = it }, isPassword = false)
            CustomTextField(value = ward, label = "Phường", onValueChange={ ward = it }, isPassword = false)
            CustomTextField(value = street, label = "Đường",onValueChange= { street = it },isPassword = false)
            CustomTextField(value = houseNumber, label = "Số nhà",onValueChange= { houseNumber = it },isPassword = false)

            Spacer(modifier = Modifier.height(24.dp))

            // Confirm Button
            Button(
                onClick = { /* Handle confirm */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF774D)), // Button color
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Xác nhận",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    errorMessage: String? = null,
    ) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFE1E1E1), // Background color for the TextField
            focusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        isError = errorMessage != null
    )
    // Hiển thị lỗi nếu có
    if (errorMessage != null) {
        Text(
            text = errorMessage,
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewUpdateInfor() {
//    Assignment_PH45308Theme {
    UpdatInformation()
//    }
}