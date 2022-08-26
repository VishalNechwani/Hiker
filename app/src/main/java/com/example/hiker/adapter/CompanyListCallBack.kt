package com.example.hiker.adapter

import com.example.hiker.model.HikeEntity

interface CompanyListCallBack {
    fun showDeleteIcon()
    fun HideDeleteIcon()
    fun deleteHiker(deleteHikes : ArrayList<HikeEntity>)
}