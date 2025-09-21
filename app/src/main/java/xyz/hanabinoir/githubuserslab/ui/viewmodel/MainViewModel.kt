package xyz.hanabinoir.githubuserslab.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.hanabinoir.githubuserslab.data.UserItem
import xyz.hanabinoir.githubuserslab.network.UserRepository
import javax.inject.Inject

sealed interface HomeUiState {
    data class Success(val users: List<UserItem>) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    var uiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set
    var isRefreshing by mutableStateOf(false)
        private set

    fun getUsers() {
        Timber.Forest.i("Loading users list")
        viewModelScope.launch {
            uiState = try {
                isRefreshing = uiState is HomeUiState.Success
                val users = userRepository.getUsers()
                Timber.Forest.i("Successfully loaded users list, count: ${users.size}")
                isRefreshing = false
                HomeUiState.Success(users)
            } catch (e: Exception) {
                Timber.Forest.e(e.localizedMessage)
                isRefreshing = false
                HomeUiState.Error
            }
        }
    }
}