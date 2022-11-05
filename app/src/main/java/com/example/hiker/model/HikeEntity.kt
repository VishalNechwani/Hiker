package com.example.hiker.model

import android.os.Parcel
import android.os.Parcelable
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
    @ColumnInfo(name = "regime_new") var inRegimeNew: String,
    @ColumnInfo(name = "hike_amount") var hikeAmount: String
    ): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.createTypedArrayList(Component.CREATOR)?:ArrayList(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {


    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(company_id)
        parcel.writeString(company_name)
        parcel.writeTypedList(component_arr)
        parcel.writeString(current_ctc)
        parcel.writeString(expected_ctc)
        parcel.writeString(inHandNew)
        parcel.writeString(inHandOld)
        parcel.writeString(inRegimeOld)
        parcel.writeString(inRegimeNew)
        parcel.writeString(hikeAmount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HikeEntity> {
        override fun createFromParcel(parcel: Parcel): HikeEntity {
            return HikeEntity(parcel)
        }

        override fun newArray(size: Int): Array<HikeEntity?> {
            return arrayOfNulls(size)
        }
    }


}
 