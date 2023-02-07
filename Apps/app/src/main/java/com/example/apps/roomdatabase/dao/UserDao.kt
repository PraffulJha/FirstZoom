package com.example.apps.roomdatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.apps.roomdatabase.entity.Users

@Dao
interface UserDao {
    @Insert
    fun insert(users: Users)
    @Query("Select * from Users")
    fun get() : LiveData<List<Users>>
    @Query("delete from USers where userId = :userId")
    fun delete(userId : Int)
}