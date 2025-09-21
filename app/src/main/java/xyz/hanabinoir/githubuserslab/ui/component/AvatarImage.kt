package xyz.hanabinoir.githubuserslab.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun AvatarImage(
    avatarUrl: String,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    AsyncImage(
        model = avatarUrl,
        contentDescription = "Avatar",
        modifier = modifier,
        contentScale = contentScale
    )
}