package com.example.hiker.adapter

import androidx.core.view.KeyEventDispatcher
import com.example.hiker.model.HikeEntity
import com.example.hiker.utils.Component

interface SalaryComponentCallBack {
    fun deleteComponentButtonEnable()
    fun deleteComponentButtonDisEnable()
    fun deleteRedundantComponent(parameter: ArrayList<Component>)
    fun componentTotal(componentTotal : Int)
}