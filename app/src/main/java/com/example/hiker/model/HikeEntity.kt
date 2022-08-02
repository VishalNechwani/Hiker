package com.example.hiker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hiker.utils.Component

@Entity(tableName = "hiker")
data class HikeEntity(
    @PrimaryKey(autoGenerate = true) val company_id: Int,
    @ColumnInfo(name = "component_array") var component_arr: Array<Component>
   )
 