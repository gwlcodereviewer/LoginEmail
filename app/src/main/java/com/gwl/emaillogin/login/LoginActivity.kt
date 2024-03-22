package com.gwl.emaillogin.login

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.gwl.emaillogin.BaseActivity
import com.gwl.emaillogin.R
import com.gwl.emaillogin.dashboard.DashboardActivity
import com.gwl.emaillogin.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel: LoginVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        initObserver()
    }

    private fun initObserver() {
        viewModel.navigateToNext.observe(this) {
            if (it) {
                if (binding.checkbox.isChecked) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setCancelable(false) // if you want user to wait for some process to finish,

                    builder.setView(R.layout.layout_loading_dialog)
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                    GlobalScope.launch(Dispatchers.IO) {
                        delay(5000)
                        dialog.dismiss()
                        navigateToDashboard()
                    }

                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.select_checkbaox),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                if (binding.email.text?.isEmpty() == true) {
                    Toast.makeText(
                        this,
                        getString(R.string.please_enter_email),
                        Toast.LENGTH_SHORT
                    ).show()
                } else
                    if (binding.etPassword.text?.isEmpty() == true) {
                        Toast.makeText(
                            this,
                            getString(R.string.please_enter_password),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
        viewModel.wrongEmail.observe(this) {
            Toast.makeText(this, getString(R.string.please_enter_valid_email), Toast.LENGTH_SHORT)
                .show()
        }
        viewModel.wrongPassword.observe(this) {
            Toast.makeText(
                this,
                getString(R.string.please_enter_password_minimum_characters),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun navigateToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
    }
}