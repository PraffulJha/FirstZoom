package com.example.apps.roomdatabase.repository


import androidx.lifecycle.LiveData
import com.example.apps.roomdatabase.dao.UserDao
import com.example.apps.roomdatabase.entity.Users

class UserRepository(var userDao: UserDao) {
    fun insert(users: Users){
        userDao.insert(users)
    }
    var usersList : LiveData<List<Users>> = userDao.get()
    fun get() {
        userDao.get()
    }
    fun delete(userId :Int) {
        userDao.delete(userId)
    }
}