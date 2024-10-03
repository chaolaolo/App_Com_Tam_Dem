package com.ph45308.assignment_ph45308.ViewModel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph45308.assignment_ph45308.ApiService
import com.ph45308.assignment_ph45308.Model.User
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorEmail by mutableStateOf<String?>(null)
    var errorPass by mutableStateOf<String?>(null)
    var rememberMe by mutableStateOf(false)

    private var BASE_URL = "http://192.168.1.4:3000/"
    private val PREFS_NAME = "user_prefs"
    private val TOKEN_KEY = "token"
    private val EMAIL_KEY = "email"
    private val PASSWORD_KEY = "password"
    private val REMEMBER_ME_KEY = "remember_me"

    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    fun validateInputs(): Boolean {
        var err = true
        if (email.isBlank()) {
            errorEmail = "Email không được để trống"
            err = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorEmail = "Email không hợp lệ"
            err = false
        } else {
            errorEmail = null
        }
        if (password.isBlank()) {
            errorPass = "Mật khẩu không được để trống"
            err = false
        } else if (password.length < 6) {
            errorPass = "Mật khẩu phải có ít nhất 6 ký tự"
            err = false
        } else {
            errorPass = null
        }
        return err
    }

    fun login(context: Context, onLoginSuccess: () -> Unit) {
        if (!validateInputs()) {
            return
        }
        viewModelScope.launch {
            isLoading = true
            try {
                val response = apiService.login(
                    User(
                        email = email, password = password,
                    )
                )
                if (response.token != null) {
                    saveLoginInfo(context, response.token)
                    errorEmail = null
                    errorPass = null
                    Log.d("TAG", "login token: ${response.token}")
                    onLoginSuccess()
                } else {
                    Toast.makeText(context, "Email hoặc mật khẩu không khớp", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Đã xảy ra lỗi. Vui lòng thử lại.", Toast.LENGTH_LONG).show()
            } finally {
                isLoading = false
            }
        }
    }

    private fun saveLoginInfo(context: Context, token: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(TOKEN_KEY, token)
            if (rememberMe) {
                putString(EMAIL_KEY, email)
                putString(PASSWORD_KEY, password)
            }
            putBoolean(REMEMBER_ME_KEY, rememberMe)
            apply()
        }
    }

    fun loadLoginInfo(context: Context) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean(REMEMBER_ME_KEY, false)) {
            email = sharedPreferences.getString(EMAIL_KEY, "") ?: ""
            password = sharedPreferences.getString(PASSWORD_KEY, "") ?: ""
            rememberMe = true
        }
    }

    fun checkLogin(context: Context): Boolean {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(TOKEN_KEY, null) != null
    }

    fun logout(context: Context) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove(TOKEN_KEY)
            apply()
        }
    }


}