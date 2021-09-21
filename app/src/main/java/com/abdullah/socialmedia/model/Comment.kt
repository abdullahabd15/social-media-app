package com.abdullah.socialmedia.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    @SerializedName("author_name")
    val authorName: String,
    @SerializedName("author_image_url")
    val authorImageUrl: String?,
    val body: String,
    val id: Int
): Parcelable