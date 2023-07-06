package com.app.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.app.todolist.database.AppDatabase

import com.app.todolist.databinding.ActivityDailyWorkBinding
import com.app.todolist.model.User
import com.google.android.material.textfield.TextInputLayout

class DailyWorkActivity : AppCompatActivity() {
    lateinit var database:AppDatabase
    lateinit var binding: ActivityDailyWorkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailyWorkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(this, AppDatabase::class.java, name = "dixit.db")
            .allowMainThreadQueries().build()



        binding.floatingButtonSaved.setOnClickListener {
            val title = binding.etTittle.text.toString().trim()
            val description = binding.etDaliyWork.text.toString().trim()

            if (title.isBlank()) {
                Toast.makeText(this, "Title cannot be blank", Toast.LENGTH_SHORT).show()
            } else {
                inserUser(title, description)
            }
        }



        binding.floatingButtonSaved.setOnClickListener {

            /* var tittle=binding.etTittle.text.toString().trim()
            var description=binding.etDaliyWork.text.toString().trim()
            inserUser(tittle,description)*/
            /* val title = binding.etTittle.text.toString().trim()
            val description = binding.etDaliyWork.text.toString().trim()

            if (title.isEmpty()) {
                binding.etTittle.error = "Enter Title"
                binding.tiltittle.boxStrokeColor = Color.RED
                return@setOnClickListener
            }
            inserUser(title, description)*/
            val title = binding.etTittle.text.toString().trim()
            val description = binding.etDaliyWork.text.toString().trim()

            if (title.isEmpty() || description.isEmpty()) {
                setStrokeColor(binding.tiltittle, R.color.red)
                setStrokeColor(binding.desTil, R.color.red)
                Toast.makeText(this, "Title and Description cannot be blank", Toast.LENGTH_SHORT).show()
            } else {
                setStrokeColor(binding.tiltittle, R.color.white)
                setStrokeColor(binding.desTil, R.color.white)
                inserUser(title, description)
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }


        }

    private fun setStrokeColor(view: TextInputLayout, colorResId: Int) {
        val color = ContextCompat.getColor(this, colorResId)
        view.boxStrokeColor = color

    }





    private fun inserUser(ctittle:String,cdescription:String) {

        var user= User(tittle = ctittle, description = cdescription)

        try {
                database.userDao().insetUser(user)
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            onBackPressed()

        }catch (e:Exception){
            e.printStackTrace()
        }

    }
}