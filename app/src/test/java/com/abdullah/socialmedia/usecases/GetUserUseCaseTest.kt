package com.abdullah.socialmedia.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import app.cash.turbine.test
import com.abdullah.socialmedia.LiveDataTestUtil.getOrAwaitValue
import com.abdullah.socialmedia.MainCoroutineRule
import com.abdullah.socialmedia.common.Resource
import com.abdullah.socialmedia.model.User
import com.abdullah.socialmedia.repository.SocialMediaRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetUserUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var fakeUseCase: GetUserUseCase

    @Mock
    private lateinit var repository: SocialMediaRepositoryImpl

    private lateinit var getUserUseCase: GetUserUseCase

    @Before
    fun setup() {
        getUserUseCase = GetUserUseCase(repository)
    }

    @Test
    fun `get user by id then return success`() = runBlockingTest {
        val fakeUser = User(
            "Jl. Sasak I No 11, Kelapa Dua Kebon Jeruk",
            listOf(),
            "Buana varia Komputama",
            "abdullahabd915@gmail.com",
            1,
            "Abdullah"
        )
        `when`(fakeUseCase.findUser(fakeUser.id)).thenAnswer {
            flowOf(Resource.Success(fakeUser))
        }
        val result = fakeUseCase.findUser(fakeUser.id).asLiveData().getOrAwaitValue()
        assert(result is Resource.Success)
        assertSame(fakeUser, (result as Resource.Success).data)
    }

    @Test
    fun `get user by id, where given id is less than 1 then return error`() = runBlockingTest {
        val userId = 0
        val message = "Invalid User ID"
        val result = getUserUseCase.findUser(userId).asLiveData().getOrAwaitValue()
        assert(result is Resource.Error)
        assertSame(message, (result as Resource.Error).message)
    }
}