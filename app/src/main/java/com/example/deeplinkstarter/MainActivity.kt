package com.example.deeplinkstarter

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.deeplinkstarter.databinding.ActivityMainBinding
import com.example.deeplinkstarter.enum.AppParametersEnum
import com.example.deeplinkstarter.ui.location.LocationActivity
import com.example.deeplinkstarter.ui.profile.ProfileActivity
import com.example.deeplinkstarter.ui.register.RegisterViewModel
import com.example.deeplinkstarter.ui.register.RegisterViewModelFactory
import com.example.deeplinkstarter.ui.register.RegisteredUserView
import com.orhanobut.hawk.Hawk

class MainActivity : AppCompatActivity() {


    private lateinit var registerViewModel: RegisterViewModel
    private var country: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Hawk.init(this).build()
        val mainViewBinding = ActivityMainBinding.inflate(layoutInflater)


        val username = mainViewBinding.userName
        val password = mainViewBinding.password
        val confim_password = mainViewBinding.confrimPassword
        val register = mainViewBinding.register
        val chooseLocation = mainViewBinding.chooseCountry
        val loading = mainViewBinding.loading

        chooseLocation.setOnClickListener {
            chooseCountry();
        }

        register.setOnClickListener({
            registerViewModel.registerDataChanged(
                mainViewBinding.userName.text.toString(),
                mainViewBinding.password.text.toString(),
                mainViewBinding.confrimPassword.text.toString()
            )

        })

        registerViewModel = ViewModelProviders.of(this, RegisterViewModelFactory())
            .get(RegisterViewModel::class.java)

        registerViewModel.registerFormState.observe(this@MainActivity, Observer {
            val registerState = it ?: return@Observer

            // disable login button unless both username / password is valid
            if (!country.isNullOrBlank()) {
                if (registerState.usernameError != null) {
                    mainViewBinding.userName.error = getString(registerState.usernameError)
                    Toast.makeText(this,  getString(registerState.usernameError), Toast.LENGTH_SHORT).show()
                }
                if (registerState.passwordError != null) {
                    mainViewBinding.password.error = getString(registerState.passwordError)
                    Toast.makeText(this, getString(registerState.passwordError), Toast.LENGTH_SHORT).show()
                }
                if (registerState.passwordError != null) {
                    mainViewBinding.confrimPassword.error = getString(registerState.passwordError)
                    Toast.makeText(this, getString(registerState.passwordError), Toast.LENGTH_SHORT).show()

                }

                if(registerState.isDataValid){
                    registerViewModel.register(
                        mainViewBinding.userName.text.toString(),
                        mainViewBinding.password.text.toString(),
                        country
                    )
                }

            } else {
                mainViewBinding.confrimPassword.error = getString(R.string.country_must_have_value)
                Toast.makeText(this, R.string.country_must_have_value, Toast.LENGTH_SHORT).show()
            }





        })

        registerViewModel.registerResult.observe(this@MainActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                goToProfile(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        val view = mainViewBinding.root
        setContentView(view)


    }


    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    private fun goToProfile(registeredUser: RegisteredUserView?) {
        val profileIntent = Intent(this, ProfileActivity::class.java)
        val parameters = Bundle()
        parameters.putString(AppParametersEnum.USER_NAME.value, registeredUser?.displayName)
        profileIntent.putExtras(parameters)
        startActivity(profileIntent)
        finish()
    }

    private fun chooseCountry() {
        val chooseCountryIntent = Intent(this, LocationActivity::class.java)
        startActivityForResult(chooseCountryIntent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                country = data?.getStringExtra(AppParametersEnum.COUNTRY.value).toString()

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, R.string.country_must_have_value, Toast.LENGTH_SHORT).show()
            }
        }
    }
}