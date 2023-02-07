package com.example.apps.roomdatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class Users(
    @PrimaryKey(autoGenerate = true) val userId : Int,
    val userName : String,
    val userRepos : String,
    val desc : String
)
