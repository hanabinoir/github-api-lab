package xyz.hanabinoir.githubuserslab.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.hanabinoir.githubuserslab.model.UserItem
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

    init {
        getUsers()
    }

    fun getUsers() {
        Timber.i("Loading users list")
        viewModelScope.launch {
            uiState = try {
                val users = userRepository.getUsers()
                Timber.i("Successfully loaded users list, count: ${users.size}")
                HomeUiState.Success(users)
            } catch (e: Exception) {
                Timber.e(e.localizedMessage)
                HomeUiState.Error
            }
        }
    }
}