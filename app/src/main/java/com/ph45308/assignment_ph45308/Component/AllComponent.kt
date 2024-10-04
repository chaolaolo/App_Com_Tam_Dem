package com.ph45308.assignment_ph45308.Component

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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


@Composable
fun PaymentOption(
    iconRes: Int,
    optionName: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(
                color = if (isSelected) Color(0xFFFFA726) else Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = { onClick() },
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.White,
                unselectedColor = Color.Black
            )
        )
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = optionName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewUpdateInfor() {
    Column {
        CustomTextField(
            "String",
            "String",
            onValueChange = { Log.d("TAG", "PreviewUpdateInfor: ") },
            false
        )
        ///=======
        var selectedPayment by remember { mutableStateOf("") }
        PaymentOption(
            iconRes = R.drawable.img_cashpay, // Thay thế bằng ID tài nguyên thực của ảnh Thanh toán trực tiếp
            optionName = "Thanh toán trực tiếp",
            isSelected = selectedPayment == "Thanh toán trực tiếp",
            onClick = { selectedPayment = "Thanh toán trực tiếp" }
        )
        PaymentOption(
            iconRes = R.drawable.img_zalopay, // Thay thế bằng ID tài nguyên thực của ảnh VISA
            optionName = "VISA",
            isSelected = selectedPayment == "VISA",
            onClick = { selectedPayment = "VISA" }
        )
    }
}