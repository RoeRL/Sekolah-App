package com.bimo.sekolahku2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        btnLogin.setOnClickListener {

            login()
        }


    }

    fun login() {
        val username = "admin"
        val password = "admin123"


        var inputUsername = inputUsername.text.toString()
        var inputPassword = inputPassword.text.toString()

        if (inputUsername == username && inputPassword == password) {
            Toast.makeText(this, "username atau password salah", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(this, "username atau password salah", Toast.LENGTH_SHORT).show()
        }
    }
}