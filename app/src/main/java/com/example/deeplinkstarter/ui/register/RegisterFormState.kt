package com.example.deeplinkstarter.ui.register

data class RegisterFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val countryError : Int? = null,
    val isDataValid: Boolean = false
)