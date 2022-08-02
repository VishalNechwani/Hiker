package com.example.hiker.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HikeDao {

    @Query("SELECT * FROM hiker")
    fun getAll(): LiveData<List<HikeEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHike(hike: HikeEntity):Long
}