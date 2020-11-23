package com.example.clientiuk2

import com.google.gson.annotations.SerializedName

class Post(
    val userId: Int, val id: Int,
    val title: String, @SerializedName("body")
    val message: String
) {

}