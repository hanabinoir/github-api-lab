package xyz.hanabinoir.githubuserslab.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.hanabinoir.githubuserslab.model.UserDetail
import xyz.hanabinoir.githubuserslab.model.UserEvent
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
        Timber.i("Loading user detail for $username")
        viewModelScope.launch {
            uiState = try {
                val userDetail = userRepository.getUserDetail(username)
                Timber.i("Successfully loaded user detail for $username")
                UserDetailUiState.Success(userDetail)
            } catch (e: Exception) {
                Timber.e(e.localizedMessage)
                UserDetailUiState.Error
            }
        }
    }

    fun getUserEvents(username: String) {
        Timber.i("Loading user events for $username")
        viewModelScope.launch {
            userEvents = try {
                val events = userRepository.getUserEvents(username)
                Timber.i("Successfully loaded user events for $username, count: ${events.size}")
                events
            } catch (e: Exception) {
                Timber.e(e.localizedMessage)
                emptyList()
            }
        }
    }
}