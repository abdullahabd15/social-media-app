package com.abdullah.socialmedia.usecases

import com.abdullah.socialmedia.common.Resource
import com.abdullah.socialmedia.model.User
import com.abdullah.socialmedia.repository.SocialMediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: SocialMediaRepository
) {
    fun findUser(userId: Int): Flow<Resource<User>> {
        return if (userId < 1) {
            flowOf(Resource.Error("Invalid User ID"))
        } else
            flow {
                try {
                    emit(Resource.Loading<User>())
                    val data = repository.findAllUsers()
                    val user = data?.users?.find { it.id == userId }
                    if (user == null) {
                        emit(Resource.Error<User>("User not found"))
                    } else {
                        emit(Resource.Success(user))
                    }
                } catch (e: HttpException) {
                    emit(Resource.Error<User>(e.localizedMessage ?: "Unknown Error"))
                } catch (e: IOException) {
                    emit(Resource.Error<User>("Couldn't reach server. Check your internet connection."))
                }
            }.flowOn(Dispatchers.IO)
    }
}