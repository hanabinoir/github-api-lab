package xyz.hanabinoir.githubuserslab.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.hanabinoir.githubuserslab.data.UserDetail
import xyz.hanabinoir.githubuserslab.data.UserEvent
import xyz.hanabinoir.githubuserslab.network.UserRepository
import javax.inject.Inject

sealed class UserDetailUiState {
    object Loading: UserDetailUiState()
    data class Success(val userDetail: UserDetail): UserDetailUiState()
    object Error: UserDetailUiState()
}

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    var uiState: UserDetailUiState by mutableStateOf(UserDetailUiState.Loading)
        private set
    var userEvents: List<UserEvent> by mutableStateOf(emptyList())
        private set

    fun getUserDetail(username: String) {
        Timber.Forest.i("Loading user detail for $username")
        viewModelScope.launch {
            uiState = try {
                val userDetail = userRepository.getUserDetail(username)
                Timber.Forest.i("Successfully loaded user detail for $username")
                UserDetailUiState.Success(userDetail)
            } catch (e: Exception) {
                Timber.Forest.e(e.localizedMessage)
                UserDetailUiState.Error
            }
        }
    }

    fun getUserEvents(username: String) {
        Timber.Forest.i("Loading user events for $username")
        viewModelScope.launch {
            userEvents = try {
                val events = userRepository.getUserEvents(username)
                Timber.Forest.i("Successfully loaded user events for $username, count: ${events.size}")
                events
            } catch (e: Exception) {
                Timber.Forest.e(e.localizedMessage)
                emptyList()
            }
        }
    }
}