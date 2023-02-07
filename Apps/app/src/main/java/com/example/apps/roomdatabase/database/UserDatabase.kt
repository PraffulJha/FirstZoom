package com.example.apps.roomdatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.apps.roomdatabase.dao.UserDao
import com.example.apps.roomdatabase.entity.Users
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Users::class], version = 1)
abstract class UserDatabase  :RoomDatabase() {
    abstract fun userDao() : UserDao
    companion object{
        @Volatile
        private var INSTANCE : UserDatabase?= null
        fun getDatabase(context: Context, applicationsScope: CoroutineScope) : UserDatabase {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                "UserDatabase"
            )
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            return instance
        }
    }
    class UserDatabaseCallback(applicationsScope: CoroutineScope) : Callback() {
        var scope : CoroutineScope = applicationsScope
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

        }
    }
}