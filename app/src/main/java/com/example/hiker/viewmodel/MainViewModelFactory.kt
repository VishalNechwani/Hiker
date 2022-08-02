package com.example.hiker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hiker.model.HikeRepository


class MainViewModelFactory(val hikeRepository: HikeRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(hikeRepository) as T
    }

}