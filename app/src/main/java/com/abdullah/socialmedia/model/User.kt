package com.abdullah.socialmedia.model

import com.google.gson.annotations.SerializedName

data class User(
    val address: String,
    val albums: List<Album>,
    val company: String,
    val email: String,
    val id: Int,
    @SerializedName("user_name")
    val userName: String
)