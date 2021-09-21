package com.abdullah.socialmedia.usecases

import com.abdullah.socialmedia.common.Resource
import com.abdullah.socialmedia.model.Post
import com.abdullah.socialmedia.repository.SocialMediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FindAllPostsUseCase @Inject constructor(
    private val repository: SocialMediaRepository
) {
    fun findAllPosts(): Flow<Resource<List<Post>>> {
        return flow {
            try {
                emit(Resource.Loading<List<Post>>())
                val posts = repository.findAllPosts()?.posts
                if (posts == null) {
                    emit(Resource.Error<List<Post>>("Not Found"))
                } else {
                    emit(Resource.Success(posts))
                }
            } catch (e: HttpException) {
                emit(Resource.Error<List<Post>>(e.localizedMessage ?: "Unknown Error"))
            } catch (e: IOException) {
                emit(Resource.Error<List<Post>>("Couldn't reach server. Check your internet connection."))
            }
        }.flowOn(Dispatchers.IO)
    }
}