package xyz.hanabinoir.githubuserslab.network

import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService
) {
    suspend fun getUsers() = userService.getUsers()

    suspend fun getUserDetail(username: String) = userService.getUser(username)

    suspend fun getUserEvents(username: String) = userService.getUserEvents(username)
}