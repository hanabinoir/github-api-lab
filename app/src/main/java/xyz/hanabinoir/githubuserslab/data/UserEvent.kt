package xyz.hanabinoir.githubuserslab.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventRepo(
    val id: Int,
    val name: String,
    val url: String
)

@Serializable
data class UserEvent(
    val id: String,
    val type: String,
    val repo: EventRepo,
    @SerialName(value = "created_at") val createdAt: String
)
