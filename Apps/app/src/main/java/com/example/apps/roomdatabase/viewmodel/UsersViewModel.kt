package com.example.apps.roomdatabase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apps.roomdatabase.entity.Users
import com.example.apps.roomdatabase.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun insert(users: Users) = CoroutineScope(Dispatchers.IO).launch {
        userRepository.insert(users)
    }
    var Userslist : LiveData<List<Users>> = userRepository.usersList
    fun get(){
        userRepository.get()
    }
    fun delete(userId :Int) = CoroutineScope(Dispatchers.IO).launch{
        userRepository.delete(userId)
    }



}
class UsersViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UsersViewModel::class.java)){
            return UsersViewModel(userRepository) as T
        }
        throw java.lang.IllegalArgumentException("unknown error")
    }
}