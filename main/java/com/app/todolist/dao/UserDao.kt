package com.app.todolist.dao

import androidx.room.*
import com.app.todolist.model.User

@Dao
interface UserDao {
    @Insert
    fun insetUser(userData: User)
    @Query("select * from user_table")
    fun getAllElement():MutableList<User>
    @Update
    fun updateuser(userData: User)
    @Delete
    fun deleteUser(userData: User)
}