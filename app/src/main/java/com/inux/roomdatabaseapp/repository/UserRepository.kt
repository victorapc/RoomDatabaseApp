package com.inux.roomdatabaseapp.repository

import androidx.lifecycle.LiveData
import com.inux.roomdatabaseapp.data.user.UserDao
import com.inux.roomdatabaseapp.model.User

class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    fun readData(idTable: Int) : LiveData<User> {
        return userDao.readData(idTable)
    }
}