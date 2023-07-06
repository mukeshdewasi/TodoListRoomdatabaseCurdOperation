package com.app.todolist

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View

import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Query
import androidx.room.Room
import com.app.todolist.adapter.UserListAdapter
import com.app.todolist.database.AppDatabase

import com.app.todolist.databinding.ActivityHomePageBinding

import com.app.todolist.model.User
import com.google.android.gms.common.util.ArrayUtils.contains
import com.google.firebase.auth.FirebaseAuth


class HomePageActivity : AppCompatActivity() {
    private var filteredList = mutableListOf<User>()
    lateinit var database: AppDatabase
    lateinit var madapter: UserListAdapter
    lateinit var mAuth: FirebaseAuth
    private var searchBarOpen = false


    var list = mutableListOf<User>()
    lateinit var binding: ActivityHomePageBinding
    lateinit var listadapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        supportActionBar!!.title = "${title}"

        database = Room.databaseBuilder(this, AppDatabase::class.java, name = "dixit.db")
            .allowMainThreadQueries().build()

        mAuth = FirebaseAuth.getInstance()
        binding.toolBar.title = View.GONE.toString()





        binding.floatingButton.setOnClickListener {
            startActivity(Intent(this, DailyWorkActivity::class.java))
        }
        madapter = UserListAdapter(this, list)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = madapter

        updatelist()


    }

    private fun updatelist() {
        list = database.userDao().getAllElement()
        madapter.setitem(list)

    }

    override fun onResume() {
        super.onResume()
        if (database != null) {
            updatelist()
            if (searchBarOpen) {
                filterUsers(binding.etSerach.text.toString())
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {

                  toggleSearch(item)


                true
            }

            R.id.menu_setting -> {
                true
            }
            R.id.menu_logout -> {

                mAuth.signOut()
                startActivity(Intent(this, LogInActivity::class.java))


                true
            }
            else -> super.onOptionsItemSelected(item)

        }


    }


    override fun onBackPressed() {
        if (searchBarOpen) {
            // Hide the search bar and collapse the action view
            binding.etSerach.visibility = View.GONE
            binding.toolBarTittle.visibility = View.VISIBLE
            binding.image.visibility = View.VISIBLE
            //   binding.toolBar.title.visibility = View.VISIBLE
            searchBarOpen = false
            val searchItem = binding.toolBar.menu.findItem(R.id.menu_search)
            searchItem.collapseActionView()
        } else {

            super.onBackPressed()
        }
    }


    private fun toggleSearch(item: MenuItem) {
        if (searchBarOpen) {
            binding.etSerach.visibility = View.GONE
         //   binding.spiner.visibility=View.VISIBLE
            binding.image.visibility=View.VISIBLE
            binding.toolBarTittle.visibility=View.VISIBLE
            searchBarOpen =false
        } else {
            binding.etSerach.visibility = View.VISIBLE
           // binding.spiner.visibility=View.GONE
            binding.image.visibility=View.GONE
            binding.toolBarTittle.visibility=View.GONE
            searchBarOpen=true
            binding.etSerach.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    filterUsers(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                    filterUsers(s.toString())
                    database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, name = "dixit.db")
                        .allowMainThreadQueries().build()

                }



            })
        }
    }

    private fun filterUsers(query: String) {

        filteredList.clear()
        if (query.isEmpty()) {
            filteredList.addAll(list)
        } else {
            for (user in list) {
                if (user.tittle.contains(query, true)) {
                    filteredList.add(user)
                }
            }
        }
        madapter.setitem(filteredList)

    }


}
