package com.app.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.todolist.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    val PREF_NAME="dixit"
    val KEYLOGIN="islogin"
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Thread({
            try {

                Thread.sleep(3000)
                val preference=getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                val satus=preference.getBoolean(KEYLOGIN,false)
                if (!satus){
                    startActivity(Intent(this,LogInActivity::class.java))
                }else{
                    startActivity(Intent(this,HomePageActivity::class.java))
                }
            }catch (e:Exception){
                e.printStackTrace()
            }


        }).start()


    }
}