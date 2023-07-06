package com.app.todolist.adapter

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.app.todolist.UpdateActivity
import com.app.todolist.database.AppDatabase
import com.app.todolist.databinding.DaliyWorkUiDesignBinding
import com.app.todolist.model.User

class UserListAdapter(var context: Context,var userlist:MutableList<User>):RecyclerView.Adapter<UserListAdapter.MyViewHolder>() {
    lateinit var binding: DaliyWorkUiDesignBinding
    lateinit var database: AppDatabase

    class MyViewHolder(var binding: DaliyWorkUiDesignBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding= DaliyWorkUiDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var name=userlist[position]
        holder.binding.uiTittle.text="${name.tittle}"
        holder.binding.uiDescription.text="${name.description}"
        holder.binding.carView.setOnClickListener {
            var intent=Intent(context,UpdateActivity::class.java)
            intent.putExtra("USER",name)
            context.startActivity(intent)
        }
        holder.binding.carView.setOnLongClickListener {
            AlertDialog.Builder(context).apply {
                database = Room.databaseBuilder(context, AppDatabase::class.java, name = "dixit.db")
                    .allowMainThreadQueries().build()
                setTitle("Delete")
                    .setMessage("Are you sure you want to delete the item")
                    .setPositiveButton("Yes",DialogInterface.OnClickListener { dialog, which ->
                        database.userDao().deleteUser(name)

                        notifyItemRemoved(position)
                        userlist.removeAt(position)

                    })
                    .setNegativeButton("No",DialogInterface.OnClickListener { dialog, which ->

                    })
            }.show()
            return@setOnLongClickListener true
        }


    }

    override fun getItemCount(): Int {
        return userlist.size
    }
    fun setitem(userlist: MutableList<User>){
        this.userlist=userlist
        notifyDataSetChanged()
    }
}