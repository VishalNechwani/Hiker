package com.example.hiker.model

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Database(entities = [HikeEntity::class], version = 1)
abstract class HikeDatabase  : RoomDatabase(){

    abstract fun hikerDao(): HikeDao

    companion object {
        private var instance: HikeDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): HikeDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, HikeDatabase::class.java,
                    "hike_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            return instance!!

        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }

}