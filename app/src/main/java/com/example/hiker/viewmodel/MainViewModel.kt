package com.example.hiker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hiker.model.HikeEntity
import com.example.hiker.model.HikeRepository


class MainViewModel(val hikeRepository: HikeRepository) : ViewModel(){

    private var allHikes : LiveData<List<HikeEntity>>
    init {
        allHikes = hikeRepository.getAllHikes()
        true
    }


    fun getHikes() : LiveData<List<HikeEntity>>{
        return allHikes
    }






}