package com.app.todolist

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.app.todolist.database.AppDatabase
import com.app.todolist.databinding.ActivityUpdateBinding
import com.app.todolist.model.User

class UpdateActivity : AppCompatActivity() {
    lateinit var database: AppDatabase
    lateinit var binding: ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(this, AppDatabase::class.java, name = "dixit.db")
            .allowMainThreadQueries().build()

       var name= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("USER",User::class.java)
        }else{
            intent.getParcelableExtra("USER")
        }
        if (name!=null){
            binding.etTittle.setText(name.tittle)
            binding.etDes.setText(name.description)
        }

        binding.updateButton.setOnClickListener {
            var title=binding.etTittle.text.toString().trim()
            var des=binding.etDes.text.toString().trim()
            var muser=User(tittle = title, description = des, id =name!!.id)
            database.userDao().updateuser(muser)
            onBackPressed()
        }

    }
}