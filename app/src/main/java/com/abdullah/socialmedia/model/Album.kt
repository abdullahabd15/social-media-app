package com.abdullah.socialmedia.model

import com.google.gson.annotations.SerializedName

data class Album(
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String
)