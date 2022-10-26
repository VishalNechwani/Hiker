package com.example.hiker.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HikeDao {

    @Query("SELECT * FROM hiker")
    fun getAll(): LiveData<List<HikeEntity>>

    @TypeConverters(RoomConverters::class)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addHike(hike: HikeEntity):Long

    @TypeConverters(RoomConverters::class)
    @Query("delete from hiker where company_id in (:deleteHike)")
    fun deleteHike(deleteHike: ArrayList<Int>)
}