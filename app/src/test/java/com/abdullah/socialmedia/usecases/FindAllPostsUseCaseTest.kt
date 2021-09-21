package com.abdullah.socialmedia.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.abdullah.socialmedia.LiveDataTestUtil.getOrAwaitValue
import com.abdullah.socialmedia.MainCoroutineRule
import com.abdullah.socialmedia.common.Resource
import com.abdullah.socialmedia.model.dto.PostsDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FindAllPostsUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var findAllPostsUseCase: FindAllPostsUseCase

    @Test
    fun `find all posts, success`() = runBlockingTest {
        val dummyPosts = PostsDto(mutableListOf())
        `when`(findAllPostsUseCase.findAllPosts()).thenAnswer {
            flowOf(Resource.Success(dummyPosts))
        }
        val posts = findAllPostsUseCase.findAllPosts().asLiveData().getOrAwaitValue()
        assertNotNull(posts)
        assert(posts is Resource.Success)
    }

    @Test
    fun `find all posts, error`() = runBlockingTest {
        `when`(findAllPostsUseCase.findAllPosts()).thenAnswer {
            flowOf(Resource.Error<PostsDto>("Unknown Error"))
        }
        val posts = findAllPostsUseCase.findAllPosts().asLiveData().getOrAwaitValue()
        assert(posts is Resource.Error)
    }
}