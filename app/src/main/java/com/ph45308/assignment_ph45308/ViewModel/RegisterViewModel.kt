package com.ph45308.assignment_ph45308.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph45308.assignment_ph45308.ApiService
import com.ph45308.assignment_ph45308.Model.User
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterViewModel: ViewModel() {

    private var BASE_URL = "http://192.168.1.4:3000/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    fun registerUser(user: User, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.registerUser(user)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Đăng ký thất bại")
            }
        }
    }

}