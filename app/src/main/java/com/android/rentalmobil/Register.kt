package com.android.rentalmobil

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {

    private var inputUsername: EditText?=null
    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var auth: FirebaseAuth? = null
    private var progressBar: ProgressBar? = null
    private var btnSignup: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val w = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        auth = FirebaseAuth.getInstance()

        if (auth!!.currentUser != null) {
            startActivity(Intent(this@Register, Login::class.java))
            finish()
        }

        setContentView(R.layout.activity_register)

        inputUsername = findViewById<EditText>(R.id.et_username)
        inputEmail = findViewById<EditText>(R.id.et_email)
        inputPassword = findViewById<EditText>(R.id.et_password)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        btnSignup = findViewById<TextView>(R.id.txt_register)
        btnSignup!!.setOnClickListener { userRegister() }

    }
    private fun userRegister() {
        val email = inputEmail!!.text.toString().trim { it <= ' ' }
        val password = inputPassword!!.text.toString().trim { it <= ' ' }

        when {
            TextUtils.isEmpty(email) -> inputEmail!!.error = "Enter email address!"

            TextUtils.isEmpty(password) -> inputPassword!!.error = "Enter password!"

            password.length < 6 -> inputPassword!!.error = "Password too short, enter minimum 6 characters!"

            else -> {
                progressBar!!.visibility = View.VISIBLE

                auth!!.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        this@Register
                    ) { task ->
                        Toast.makeText(
                            this@Register,
                            "Account Created. Here you go to next activity." + task.isSuccessful,
                            Toast.LENGTH_SHORT
                        ).show()
                        progressBar!!.visibility = View.GONE

                        if (!task.isSuccessful) {
                            Toast.makeText(
                                this@Register, "Authentication failed." + task.exception!!,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            startActivity(Intent(this@Register, Login::class.java))
                            finish()
                        }
                    }
            }
        }
    }
}