package com.abdullah.socialmedia.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullah.socialmedia.common.Resource
import com.abdullah.socialmedia.model.User
import com.abdullah.socialmedia.usecases.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val useCase: GetUserUseCase
): ViewModel() {

    private val _user: MutableStateFlow<Resource<User>> = MutableStateFlow(Resource.Init())
    val user get() = _user

    fun getUserById(userId: Int) {
        useCase.findUser(userId).onEach {
            _user.value = it
        }.launchIn(viewModelScope)
    }
}