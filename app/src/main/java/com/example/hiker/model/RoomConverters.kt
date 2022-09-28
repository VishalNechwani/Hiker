package com.example.hiker.model

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.hiker.utils.Component
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class RoomConverters  {
    @TypeConverter
    fun toMeaningJson(componentList: List<Component>) : String {
        return Gson().toJson(componentList)
    }

    @TypeConverter
    fun fromMeaningsJson(json: String): List<Component>{
        val listType = object : TypeToken<List<Component>>() {}.type
        return Gson().fromJson<List<Component>>(json, listType)
    }

    @TypeConverter
    fun toHikerJson(hikerList: List<HikeEntity>) : String {
        return Gson().toJson(hikerList)
    }

    @TypeConverter
    fun fromHikerJson(json: String): List<HikeEntity>{
        val listType = object : TypeToken<List<HikeEntity>>() {}.type
        return Gson().fromJson<List<HikeEntity>>(json, listType)
    }

    @TypeConverter
    fun toEachHikerJson(hikerList: HikeEntity) : String {
        return Gson().toJson(hikerList)
    }





}
