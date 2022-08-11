package com.example.hiker.model

import androidx.room.*
import com.example.hiker.utils.Component

@Entity(tableName = "hiker")
data class HikeEntity(
    @PrimaryKey(autoGenerate = true) val company_id: Int,
    @ColumnInfo(name = "component_array")
    val component_arr: List<Component>,
    @ColumnInfo(name = "in_hand") var inHand: String
   )
 