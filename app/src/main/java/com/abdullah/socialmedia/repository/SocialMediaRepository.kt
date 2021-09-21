package com.abdullah.socialmedia.repository

import com.abdullah.socialmedia.model.dto.PostsDto
import com.abdullah.socialmedia.model.dto.UsersDto

interface SocialMediaRepository {
    suspend fun findAllPosts(): PostsDto?
    suspend fun findAllUsers(): UsersDto?
}