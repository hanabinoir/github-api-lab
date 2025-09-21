package xyz.hanabinoir.githubuserslab.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import xyz.hanabinoir.githubuserslab.model.UserItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onUserClick: (String) -> Unit
) {
    LaunchedEffect(true) {
        viewModel.getUsers()
    }

    val uiState = viewModel.uiState
    when (uiState) {
        is HomeUiState.Loading -> {
            CircularProgressIndicator()
        }
        is HomeUiState.Error -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Error loading users")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.getUsers() }) {
                    Text(text = "Retry")
                }
            }
        }
        is HomeUiState.Success -> {
            PullToRefreshBox(
                isRefreshing = viewModel.isRefreshing,
                onRefresh = { viewModel.getUsers() },
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.users) { user ->
                        UserItemRow(user = user, onClick = { onUserClick(user.login) })
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun UserItemRow(user: UserItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = "Avatar",
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = user.login,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
