package com.example.hiker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hiker.model.HikeEntity
import com.example.hiker.model.HikeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel(val hikeRepository: HikeRepository) : ViewModel(){

    private var allHikes : LiveData<List<HikeEntity>>
    var isCompanyNameProper = false
    var isComponentAdd = false

    init {
        allHikes = hikeRepository.getAllHikes()
    }

    fun getHikes() : LiveData<List<HikeEntity>>{
        return allHikes
    }

    fun savingData(hikeEntity:HikeEntity):Long{
          return hikeRepository.addHike(hikeEntity)
    }

    fun deleteHiker(deleteHikes: ArrayList<HikeEntity>){
     //database operation to delete
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                hikeRepository.deleteHikes(deleteHikes)
            }
        }

    }
}