package com.inux.roomdatabaseapp.data.user

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    fun readData(idTable: Int) : LiveData<User> {
        return userDao.readData(idTable)
    }
}