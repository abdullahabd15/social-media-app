package com.abdullah.socialmedia.repository

import com.abdullah.socialmedia.api.SocialMediaApi
import com.abdullah.socialmedia.model.dto.PostsDto
import com.abdullah.socialmedia.model.dto.UsersDto
import javax.inject.Inject

class SocialMediaRepositoryImpl @Inject constructor(
    private val api: SocialMediaApi
): SocialMediaRepository {

    override suspend fun findAllPosts(): PostsDto? {
        return api.findAllPosts()
    }

    override suspend fun findAllUsers(): UsersDto? {
        return api.findAllUsers()
    }
}