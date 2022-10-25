package com.example.hiker.adapter

import androidx.core.view.KeyEventDispatcher
import com.example.hiker.model.HikeEntity
import com.example.hiker.utils.Component

interface SalaryComponentCallBack {
    fun showDeleteIcon()
    fun HideDeleteIcon()
    fun deleteComponentButtonEnable()
    fun deleteComponentButtonDisEnable()
}