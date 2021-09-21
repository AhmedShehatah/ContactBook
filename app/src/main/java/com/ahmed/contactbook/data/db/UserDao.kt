package com.ahmed.contactbook.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ahmed.contactbook.data.db.entity.UserData

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveUser(user: UserData)
}