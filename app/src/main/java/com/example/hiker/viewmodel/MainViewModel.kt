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

    fun currencyConversion(amount:String):String{
       val amountLength =  amount.length
       val amountValue = amount.toInt()
       var formattedAmount = ""
       when(amountLength){
           1,2,3 -> {

           }
           4->{
               formattedAmount = (amountValue/100).toString()
               formattedAmount = formattedAmount.substring(0,4)
               formattedAmount + "HD"
           }
           5->{
               formattedAmount = (amountValue/1000).toString()
               formattedAmount = formattedAmount.substring(0,4)
               formattedAmount + "TH"
           }
           6->{

           }
           7->{

           }
           8->{

           }
           9->{

           }
       }
    return formattedAmount
    }


}