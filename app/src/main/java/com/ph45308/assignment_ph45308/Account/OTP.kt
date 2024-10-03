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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ph45308.assignment_ph45308.Account.ui.theme.Assignment_PH45308Theme
import com.ph45308.assignment_ph45308.R

class OTP : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment_PH45308Theme {
                PreviewOTP()
            }
        }
    }
}

@Composable
fun VerifyOTP() {
    var otpCode by remember { mutableStateOf("") }
    val phoneNumber = "0342128462"
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xFFFFFF)), // Dark background color
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Image and Title
            Image(
                painter = painterResource(id = R.drawable.logo), // Replace with your image
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Hello Phước",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Chúng tôi sẽ gửi bạn mã OTP qua số điện thoại này",
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = phoneNumber,
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // OTP input fields
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(4) { index ->
                    BasicTextField(
                        value = otpCode.getOrNull(index)?.toString() ?: "",
                        onValueChange = {
                            if (it.length <= 4) otpCode = it
                        },
                        textStyle = TextStyle(fontSize = 24.sp, textAlign = TextAlign.Center, color = Color.Black),
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE1E1E1)),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                innerTextField()
                            }
                        },
                        cursorBrush = SolidColor(Color.Transparent) // Hide the cursor
                    )
                }
            }

            // Timer and Resend OTP
            Text(
                text = "00:30",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "Không nhận được mã OTP ? Gửi lại OTP",
                fontSize = 14.sp,
                color = Color(0xFFFFA07A), // Orange color
                modifier = Modifier.padding(vertical = 8.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(180.dp))

            // Confirm button
            Button(
                onClick = {
                    // Handle OTP verification here
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF774D)), // Button color
                shape = CircleShape,
                contentPadding = PaddingValues(8.dp)
            ) {
                Text(text = "Xác nhận", fontSize = 18.sp)
            }
        }
    }
}



@Preview(showSystemUi = true)
@Composable
fun PreviewOTP() {
    Assignment_PH45308Theme {
        VerifyOTP()
    }
}