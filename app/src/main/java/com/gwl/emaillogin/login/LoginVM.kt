package com.gwl.emaillogin.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern
import kotlinx.coroutines.CoroutineScope

class LoginVM : ViewModel() {

    val navigateToCreateAccount: LiveData<Boolean> get() = _navigateToCreateAccount
    private var _navigateToCreateAccount = MutableLiveData<Boolean>()
    val navigateToNext: LiveData<Boolean> get() = _navigateToNext
    private var _navigateToNext = MutableLiveData<Boolean>()
    val wrongEmail: LiveData<Boolean> get() = _wrongEmail
    private var _wrongEmail = MutableLiveData<Boolean>()
    val wrongPassword: LiveData<Boolean> get() = _wrongPassword
    private var _wrongPassword = MutableLiveData<Boolean>()
    private var email: String = ""
    private var password: String = ""
    fun updateEmail(str: String) {
        email = str
    }

    fun updatePassword(str: String) {
        password = str
    }

    fun clickNext() {
        if (email.isEmpty() || password.isEmpty()) {
            _navigateToNext.postValue(false)
            return
        }
        if (!isValidEmailId(email)) {
            _wrongEmail.postValue(true)
            return
        }
        if (password.length < 6) {
            _wrongPassword.postValue(true)
            return
        }
        Log.i(LoginVM::class.java.name, "clickNext: ")
        _navigateToNext.postValue(true)
    }

    private fun isValidEmailId(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

    fun clickRegisterNow() {

        Log.i(LoginVM::class.java.name, "clickNext: ")
        _navigateToCreateAccount.postValue(true)
    }
}