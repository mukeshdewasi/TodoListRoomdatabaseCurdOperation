package com.app.todolist.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.todolist.dao.UserDao
import com.app.todolist.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun userDao():UserDao
}