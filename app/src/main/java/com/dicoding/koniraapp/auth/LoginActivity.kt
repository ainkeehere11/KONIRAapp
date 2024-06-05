package com.dicoding.koniraapp.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.koniraapp.MainActivity
import com.dicoding.koniraapp.R
import com.dicoding.koniraapp.databinding.ActivityLoginBinding
import com.dicoding.koniraapp.helper.ViewModelFactory
import com.dicoding.koniraapp.pref.UserModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var email: String
    private lateinit var password: String
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        playAnimation()

        binding.progressBar.visibility = View.GONE

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }


        viewModel.token.observe(this) {
            if (it.token != null) {
                runBlocking {
                    delay(1000)
                }
                showToast("Login berhasil")
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                val token = it.token
                UserModel(email, token)
                    .let { it2 -> viewModel.saveSession(it2) }
                finish()
            }
        }

        viewModel.response.observe(this) {
            if (it != null) {
                if (it.isNotEmpty()) {
                    showToast(it)
                }
            }
        }

        binding.haveAcc.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }

        playAnimation()
    }

    companion object {
        const val TAG = "Login Activity"
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.logo, View.TRANSLATION_X, -60f, 60f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.etEmail, View.ALPHA, 1f).setDuration(1000)

        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.etPass, View.ALPHA, 1f).setDuration(1000)

        AnimatorSet().apply {
            playSequentially(
                emailEditTextLayout,
                passwordEditTextLayout,
            )
            startDelay = 100
        }.start()
    }

    private fun showToast(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupAction() {

        binding.btnLogin.setOnClickListener {
            email = binding.emailUser.text.toString()
            password = binding.passUser.text.toString()

            binding.progressBar.visibility = View.VISIBLE

            viewModel.login(email, password)
        }

        binding.haveAcc.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}