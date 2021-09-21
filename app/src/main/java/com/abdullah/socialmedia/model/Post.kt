package com.abdullah.socialmedia.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val body: String,
    val comments: List<Comment>,
    @SerializedName("company_name")
    val companyName: String,
    val id: Int,
    val title: String,
    @SerializedName("user_image_url")
    val userImageUrl: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("image_url")
    val imageUrl: String?
): Parcelable