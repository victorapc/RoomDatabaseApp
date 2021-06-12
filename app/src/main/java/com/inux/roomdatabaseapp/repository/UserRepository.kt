package com.inux.roomdatabaseapp.repository

import androidx.lifecycle.LiveData
import com.inux.roomdatabaseapp.data.user.UserDao
import com.inux.roomdatabaseapp.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deteleUser(user)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }

    fun readData(idTable: Int) : LiveData<User> {
        return userDao.readData(idTable)
    }

    fun searchDataBase(searchQuery: String) : Flow<List<User>>{
        return userDao.searchDataBase(searchQuery)
    }
}