package com.app.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.app.todolist.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern


class LogInActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth



    lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        binding.btnLoginSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }


        binding.btnLogin.setOnClickListener {
            var email = binding.etLogEmail.text.toString().trim()
            var password = binding.etLoginPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.etLogEmail.error = "enter the email"
                return@setOnClickListener
            } else if (isValidPassword(password)) {
                binding.etLoginPassword.error = "enter valid password"
                return@setOnClickListener
            }
            accountLogin(email, password,)


        }
    }

    private fun accountLogin( email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {

                /* Toast.makeText(applicationContext, "Welcome", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,HomePageActivity::class.java))*/
                navigateToHome()
            } else {

                Toast.makeText(applicationContext, "Invalid Credential", Toast.LENGTH_SHORT)
                    .show()

            }
        }


    }


    private fun navigateToHome() {
        Toast.makeText(applicationContext, "Welcome", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this,HomePageActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        var user = mAuth.currentUser
        if (user != null) {

          // startActivity((Intent(this, HomePageActivity::class.java)))
            navigateToHome()


        }
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length == 10
    }
}







