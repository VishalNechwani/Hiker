package com.example.hiker.model

import androidx.room.*
import com.example.hiker.utils.Component

@Entity(tableName = "hiker")
data class HikeEntity(
    @PrimaryKey(autoGenerate = true) val company_id: Int = 0,
    @ColumnInfo(name = "company_name")
    val company_name: String,
    @ColumnInfo(name = "component_array")
    val component_arr: List<Component>,
    @ColumnInfo(name = "current_ctc") var current_ctc: String,
    @ColumnInfo(name = "expected_ctc") var expected_ctc: String,
    @ColumnInfo(name = "in_hand_new") var inHandNew: String,
    @ColumnInfo(name = "in_hand_old") var inHandOld: String,
    @ColumnInfo(name = "regime_old") var inRegimeOld: String,
    @ColumnInfo(name = "regime_new") var inRegimeNew: String
    )
 