package com.android.rentalmobil

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.rentalmobil.Admin.Activity.DashboardAdmin
import com.android.rentalmobil.Customer.Activity.MainBottom
import com.google.firebase.auth.FirebaseAuth


class Login: AppCompatActivity() {

    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var auth: FirebaseAuth? = null
    private var progressBar: ProgressBar? = null
    private var btnSignup: TextView? = null
    private var btnLogin: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val w = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        auth = FirebaseAuth.getInstance()

        if (auth!!.currentUser != null) {
            if (auth!!.currentUser?.email=="fazri@gmail.com") {

                startActivity(Intent(this@Login, DashboardAdmin::class.java))
                finish()
            }else{
                startActivity(Intent(this@Login, MainBottom::class.java))
                finish()
            }
        }

        setContentView(R.layout.activity_login)

        inputEmail = findViewById<EditText>(R.id.et_username)
        inputPassword = findViewById<EditText>(R.id.et_password)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        btnSignup = findViewById<TextView>(R.id.txt_register)
        btnLogin = findViewById<Button>(R.id.btnlogin)
        btnLogin!!.setOnClickListener { userLoginIn() }

        btnSignup!!.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }
    }

    private fun userLoginIn() {
        val email = inputEmail!!.text.toString()
        val password = inputPassword!!.text.toString()

        when {
            TextUtils.isEmpty(email) -> inputEmail!!.error = "Enter email address!"

            TextUtils.isEmpty(password) -> inputPassword!!.error = "Enter password!"

            else -> {
                progressBar!!.visibility = View.VISIBLE

                auth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        this@Login
                    ) { task ->
                        progressBar!!.visibility = View.GONE
                        if (task.isSuccessful) {
//                            if (password.length < 6) {
//                                inputPassword!!.error = "Password too short, enter minimum 6 characters!"
//                            } else {
//                                inputPassword!!.error =
//                                    "Authentication failed, Check your Email and Password or Sign Up"
//                            }
                            if (email=="fazri@gmail.com") {

                                startActivity(Intent(this@Login, DashboardAdmin::class.java))
                                finish()
                            }else{
                                startActivity(Intent(this@Login, MainBottom::class.java))
                                finish()
                            }
                        } else {
                            Toast.makeText(this, "akun tidak terdaftar", Toast.LENGTH_SHORT).show()
//                            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
//                            startActivity(intent)
//                            finish()
                        }
                    }
            }
        }
    }

}