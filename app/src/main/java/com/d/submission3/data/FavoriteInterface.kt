package com.d.submission3.data

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.d.submission3.model.FavoriteResponse

@Dao
interface FavoriteInterface {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: FavoriteResponse): Long

    @Query("DELETE FROM favorite_table WHERE userId = :userId")
    fun deleteUser(userId: Int): Int

    @Query("SELECT * FROM favorite_table ORDER BY username ASC")
    fun getUsers(): ArrayList<FavoriteResponse>

    @Query("SELECT * FROM favorite_table WHERE username = :username")
    fun getUserByName(username: String): ArrayList<FavoriteResponse>

    @Query("DELETE FROM favorite_table")
    suspend fun deleteAll()
}