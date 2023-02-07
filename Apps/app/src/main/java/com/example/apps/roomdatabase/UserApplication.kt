package com.example.apps.roomdatabase

import android.app.Application
import com.example.apps.roomdatabase.database.UserDatabase
import com.example.apps.roomdatabase.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class UserApplication :Application() {
    val applicationsScope = CoroutineScope(SupervisorJob())
    val database by lazy { UserDatabase.getDatabase(this, applicationsScope) }
    val repository by lazy { UserRepository(database.userDao()) }
}