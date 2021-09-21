package com.abdullah.socialmedia.api

import com.abdullah.socialmedia.model.dto.PostsDto
import com.abdullah.socialmedia.model.dto.UsersDto
import retrofit2.http.GET

interface SocialMediaApi {
    @GET("all-posts")
    suspend fun findAllPosts(): PostsDto?

    @GET("all-users")
    suspend fun findAllUsers(): UsersDto?
}