package com.example.hiker.model

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [HikeEntity::class], version = 3)
@TypeConverters(RoomConverters::class)
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
                    .addTypeConverter(RoomConverters())
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