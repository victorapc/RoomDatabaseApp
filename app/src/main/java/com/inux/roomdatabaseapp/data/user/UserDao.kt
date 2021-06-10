package com.inux.roomdatabaseapp.data.user

import androidx.lifecycle.LiveData
import androidx.room.*
import com.inux.roomdatabaseapp.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE(id = :idTable)")
    fun readData(idTable: Int): LiveData<User>
}