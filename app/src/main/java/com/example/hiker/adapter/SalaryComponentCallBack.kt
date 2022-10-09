package com.example.hiker.adapter

import com.example.hiker.model.HikeEntity

interface SalaryComponentCallBack {
    fun showDeleteIcon()
    fun HideDeleteIcon()
    fun deleteHiker(deleteHikes : ArrayList<HikeEntity>)
}