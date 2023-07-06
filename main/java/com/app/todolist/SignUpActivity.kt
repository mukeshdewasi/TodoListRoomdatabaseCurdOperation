package com.app.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.app.todolist.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    lateinit var mAuth:FirebaseAuth

    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth=FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {


            var name = binding.etName.text.toString().trim()
            var email = binding.etEmail.text.toString().trim()
            var password = binding.etPassword.text.toString().trim()

            intent.putExtra("name", name)



            if (name.isEmpty()) {
                binding.etName.error = "enter the name"
                return@setOnClickListener
            } else if (!isValidEmail(email)) {
                binding.etEmail.error = "enter valid email address"
                return@setOnClickListener
            } else if (password.isEmpty()) {
                binding.etPassword.error = "enter valid password"
                return@setOnClickListener

            }else{

            }

            accountCreated(name,email,password)





        }


    }

    private fun accountCreated(name: String, email: String, password: String) {

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {

            if (it.isSuccessful){

                var user=it.result.user
                Toast.makeText(applicationContext, "Welcome,${name}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,HomePageActivity::class.java))


            }else{

                Toast.makeText(applicationContext, "${it.exception.toString()}", Toast.LENGTH_SHORT).show()
            }
        }

    }



    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()

    }

    private fun isValidContact(contact: String): Boolean {
        return contact.length == 10
    }





}




