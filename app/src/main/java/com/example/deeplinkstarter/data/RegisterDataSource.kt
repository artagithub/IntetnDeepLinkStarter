package com.example.deeplinkstarter.data

import com.example.deeplinkstarter.data.model.RegisteredUser
import com.orhanobut.hawk.Hawk
import java.io.IOException
import com.example.deeplinkstarter.enum.AppParametersEnum.*

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class RegisterDataSource {

    fun login(username: String, password: String, country: String): Result<RegisteredUser> {
        try {
            val userId = java.util.UUID.randomUUID().toString()
            Hawk.put(USER_NAME.value, username)
            Hawk.put(USER_PASS.value, password)
            Hawk.put(COUNTRY.value, country)
            Hawk.put(USER_ID.value, userId)
            val currentUser = RegisteredUser(userId, username)
            return Result.Success(currentUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        Hawk.delete(USER_ID.value)
        Hawk.delete(USER_NAME.value)
        Hawk.delete(USER_PASS.value)
        Hawk.delete(COUNTRY.value)
    }
}