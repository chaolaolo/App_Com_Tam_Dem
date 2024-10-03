package com.ph45308.assignment_ph45308.Model


data class Product(
    var _id: String,
    var image_url: String,
    var name: String,
    var price: Double,
    val description: String,
    var quantity: Int,
    var category: String,
)

data class User(
    var name: String,
    var userName: String,
    var email: String,
    var password: String,
)
{
    constructor(email: String, password: String) : this(
        name = "",
        email = email,
        userName = "",
        password = password
    )
}

data class LoginResponse(
    val token: String?,
    val message: String?
)

data class Cart(
    var _id: String,
)





