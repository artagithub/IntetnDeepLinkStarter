package com.example.deeplinkstarter.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deeplinkstarter.data.RegisterRepository
import com.example.deeplinkstarter.data.Result

import com.example.deeplinkstarter.R

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _loginForm

    private val _loginResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _loginResult

    fun register(username: String, password: String,country:String) {
        // can be launched in a separate asynchronous job
        val result = registerRepository.register(username, password,country)

        if (result is Result.Success) {
            _loginResult.value =
                RegisterResult(success = RegisteredUserView(displayName = result.data.displayName))
        } else {
            _loginResult.value = RegisterResult(error = R.string.login_failed)
        }
    }

    fun registerDataChanged(username: String, password: String,confirmation_password : String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = RegisterFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password,confirmation_password)) {
            _loginForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = RegisterFormState(isDataValid = true)
        }
    }



    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return (username.isNotBlank())
    }

    private fun isPasswordValid(password: String,confirmation_password: String): Boolean {
        return password.length >= 6 && confirmation_password.length >= 6 && password.equals(confirmation_password)
    }
}