package xyz.hanabinoir.githubuserslab.network

import retrofit2.http.GET
import retrofit2.http.Path
import xyz.hanabinoir.githubuserslab.data.UserDetail
import xyz.hanabinoir.githubuserslab.data.UserEvent
import xyz.hanabinoir.githubuserslab.data.UserItem

interface UserService {
    @GET("users")
    suspend fun getUsers(): List<UserItem>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: String): UserDetail

    @GET("users/{id}/events/public")
    suspend fun getUserEvents(@Path("id") id: String): List<UserEvent>
}