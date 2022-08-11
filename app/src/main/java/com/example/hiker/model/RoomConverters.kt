package com.example.hiker.model

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.hiker.utils.Component
import com.example.hiker.utils.JsonParser
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

}
