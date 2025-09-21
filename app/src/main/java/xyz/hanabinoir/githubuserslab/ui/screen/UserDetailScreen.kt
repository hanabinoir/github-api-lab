package xyz.hanabinoir.githubuserslab.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import xyz.hanabinoir.githubuserslab.data.UserDetail
import xyz.hanabinoir.githubuserslab.data.UserEvent
import xyz.hanabinoir.githubuserslab.ui.component.AvatarImage
import xyz.hanabinoir.githubuserslab.ui.component.ErrorContent
import xyz.hanabinoir.githubuserslab.ui.viewmodel.UserDetailUiState
import xyz.hanabinoir.githubuserslab.ui.viewmodel.UserDetailViewModel
import xyz.hanabinoir.githubuserslab.utility.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    username: String,
    viewModel: UserDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.getUserDetail(username)
        viewModel.getUserEvents(username)
    }

    val uiState = viewModel.uiState
    when (uiState) {
        is UserDetailUiState.Loading -> {
            CircularProgressIndicator()
        }
        is UserDetailUiState.Error -> {
            ErrorContent("Error loading users") { viewModel.getUserDetail(username) }
        }
        is UserDetailUiState.Success -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    UserProfileContent(username, uiState.userDetail)
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                if (viewModel.userEvents.isEmpty()) {
                    item {
                        Text(
                            text = "No recent activity",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            style = TextStyle(fontWeight = FontWeight.W600, fontSize = 20.sp)
                        )
                    }
                } else {
                    item {
                        Text(
                            text = "Recent Activity",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            style = TextStyle(fontWeight = FontWeight.W600, fontSize = 20.sp)
                        )
                    }
                    items(viewModel.userEvents) { event ->
                        UserEventItem(event)
                    }
                }
            }
        }
    }
}

@Composable
fun UserProfileContent(username: String, userDetail: UserDetail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            val avatarModifier = Modifier
                .size(460.dp)
            AvatarImage(userDetail.avatarUrl, modifier = avatarModifier)
        }
        Spacer(modifier = Modifier.height(16.dp))

        userDetail.name?.let {
            Text(
                text = it,
                style = TextStyle(fontWeight = FontWeight.W600, fontSize = 36.sp)
            )
        }
        userDetail.bio?.let { Text(text = it) }
        val github = createLinkedText(username, userDetail.htmlUrl)
        UserDetailItem(github) { FaIcon(FaIcons.Github) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Icon(Icons.Outlined.Person, contentDescription = "Followers")
            Text(text = "Followers: ${userDetail.followers}  Following: ${userDetail.following}")
        }

        userDetail.hireable?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Available for hiring: ")
                Icon(
                    if (it) Icons.Filled.Check else Icons.Filled.Close,
                    contentDescription = if (it) "Available" else "Not available",
                    tint = if (it) Color.Green else Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        userDetail.company?.let {
            UserDetailItem(AnnotatedString(it)) { Icon(Icons.Outlined.Business, contentDescription = "Company") }
        }

        userDetail.blog?.let {
            val blog = createLinkedText(it)
            UserDetailItem(blog) { Icon(Icons.Outlined.Link, contentDescription = "Location") }
        }

        userDetail.location?.let {
            UserDetailItem(AnnotatedString(it)) { Icon(Icons.Outlined.LocationOn, contentDescription = "Location") }
        }

        userDetail.email?.let {
            val email = createMailToLink(it)
            UserDetailItem(email) { Icon(Icons.Outlined.Email, contentDescription = "Email") }
        }

        userDetail.twitterUsername?.let {
            val twitterUrl = "https://x.com/$it"
            val twitterText = createLinkedText(it, twitterUrl)
            UserDetailItem(twitterText) { FaIcon(FaIcons.Twitter) }
        }
    }
}

@Composable
fun UserDetailItem(text: AnnotatedString, icon: @Composable () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        icon()
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}

private fun createMailToLink(email: String): AnnotatedString {
    return buildAnnotatedString {
        withLink(
            LinkAnnotation.Url(
                "mailto:$email",
                TextLinkStyles(style = SpanStyle(color = Color.Blue))
            )
        ) {
            append(email)
        }
    }
}

private fun createLinkedText(text: String, url: String): AnnotatedString {
    return buildAnnotatedString {
        withLink(
            LinkAnnotation.Url(
                url,
                TextLinkStyles(style = SpanStyle(color = Color.Blue))
            )
        ) {
            append(text)
        }
    }
}

private fun createLinkedText(url: String): AnnotatedString {
    return buildAnnotatedString {
        withLink(
            LinkAnnotation.Url(
                url,
                TextLinkStyles(style = SpanStyle(color = Color.Blue))
            )
        ) {
            append(url)
        }
    }
}

@Composable
fun UserEventItem(userEvent: UserEvent) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(text = userEvent.type)
        Text(text = userEvent.repo.name)
        Text(text = userEvent.createdAt.toLocalDateTime())
    }
}