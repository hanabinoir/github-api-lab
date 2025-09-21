package xyz.hanabinoir.githubuserslab.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserItem(
    val login: String,
    val id: Int,
    @SerialName("avatar_url") val avatarUrl: String,
)
