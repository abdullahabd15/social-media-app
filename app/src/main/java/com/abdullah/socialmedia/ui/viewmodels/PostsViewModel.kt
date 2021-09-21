package com.abdullah.socialmedia.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullah.socialmedia.common.Resource
import com.abdullah.socialmedia.model.Post
import com.abdullah.socialmedia.usecases.FindAllPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val useCase: FindAllPostsUseCase
): ViewModel() {

    private val _posts: MutableStateFlow<Resource<List<Post>>> = MutableStateFlow(Resource.Init())
    val posts get() = _posts

    fun findAllPosts() {
        useCase.findAllPosts().onEach {
            _posts.value = it
        }.launchIn(viewModelScope)
    }
}