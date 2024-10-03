package com.ph45308.assignment_ph45308.Profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ph45308.assignment_ph45308.MainActivity
import com.ph45308.assignment_ph45308.Profile.ui.theme.Assignment_PH45308Theme
import com.ph45308.assignment_ph45308.R

class EditProfile : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment_PH45308Theme {

                    PreviewEditProfile()

            }
        }
    }
}



@Composable
fun EditProfileScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
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
            EditProfileScreenUI()
        }
    }
}


@Composable
fun EditProfileScreenUI() {
    var name by remember { mutableStateOf(TextFieldValue("Chao lao Lo")) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue("0342128462")) }
    var ward by remember { mutableStateOf(TextFieldValue("Trung Mỹ Tây")) }
    var street by remember { mutableStateOf(TextFieldValue("Đường Tô Ký")) }
    var houseNumber by remember { mutableStateOf(TextFieldValue("413")) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFF))
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // Replace with your image resource
            contentDescription = "Avatar",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        EditTextField(label = "Họ và tên", value = name) {
            name = it
        }
        EditTextField(label = "Số điện thoại", value = phoneNumber) {
            phoneNumber = it
        }
        EditTextField(label = "Phường", value = ward) {
            ward = it
        }
        EditTextField(label = "Đường", value = street) {
            street = it
        }
        EditTextField(label = "Số nhà", value = houseNumber) {
            houseNumber = it
        }

        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {
//                    navController.navigate(BottomBarScreens.Home.screen)
                var intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF774D)), // Button color
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = "Lưu",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }


    }
}


@Composable
fun EditTextField(label: String, value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            color = Color.Black,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
            ),
//            label={ Text(text = label)},
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewEditProfile() {
    Assignment_PH45308Theme {
        EditProfileScreen()
    }
}
