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
    lateinit var symbol : String
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
       val amountValue = amount.toDouble()
       var formattedAmount = ""
       when(amountLength){
           1 -> {
               formattedAmount = (amountValue/1).toString()
               formattedAmount = formattedAmount.substring(0,1)
               symbol = "rs"
           }
           2 -> {
               formattedAmount = (amountValue/1).toString()
               formattedAmount = formattedAmount.substring(0,2)
               symbol = "rs"
           }
           3 -> {
               formattedAmount = (amountValue/1).toString()
               formattedAmount = formattedAmount.substring(0,3)
               symbol = "rs"
           }
           4->{
               formattedAmount = (amountValue/1000).toString()
               formattedAmount = formattedAmount.substring(0,3)
               symbol = "k"
           }
           5->{
               formattedAmount = (amountValue/1000).toString()
               formattedAmount = formattedAmount.substring(0,4)
               symbol = "k"
           }
           6->{
               //159800 = 1.598 lakh
               formattedAmount = (amountValue/100000).toString()
               formattedAmount = formattedAmount.substring(0,4)
               symbol = "lk"
           }
           7->{
               // 1059800 = 10.598
               formattedAmount = (amountValue/100000).toString()
               formattedAmount = formattedAmount.substring(0,5)
               symbol = "lk"
           }
           8->{
               // 10598000 = 10.598
               formattedAmount = (amountValue/10000000).toString()
               formattedAmount = formattedAmount.substring(0,5)
               symbol = "cr"
           }
           9->{
               // 105980000 = 10.598
               formattedAmount = (amountValue/10000000).toString()
               formattedAmount = formattedAmount.substring(0,5)
               symbol = "cr"
           }
       }
    return formattedAmount
    }


}