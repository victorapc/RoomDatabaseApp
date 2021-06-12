package com.inux.roomdatabaseapp.data.user

import androidx.lifecycle.LiveData
import androidx.room.*
import com.inux.roomdatabaseapp.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deteleUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE(id = :idTable)")
    fun readData(idTable: Int): LiveData<User>

    @Query("SELECT * FROM user_table WHERE(firstName LIKE :searchQuery OR lastName LIKE :searchQuery)")
    fun searchDataBase(searchQuery: String) : Flow<List<User>>
}