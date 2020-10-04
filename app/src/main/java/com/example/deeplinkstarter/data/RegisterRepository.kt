package com.example.deeplinkstarter.data

import com.example.deeplinkstarter.data.model.RegisteredUser



class RegisterRepository(val dataSource: RegisterDataSource) {

    // in-memory cache of the loggedInUser object
    var user: RegisteredUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun register(username: String, password: String,country:String): Result<RegisteredUser> {
        val result = dataSource.login(username, password ,country)

        if (result is Result.Success) {
            setRegisteredUser(result.data)
        }

        return result
    }

    private fun setRegisteredUser(registeredUser: RegisteredUser) {
        this.user = registeredUser
    }
}