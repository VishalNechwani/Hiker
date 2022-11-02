package com.example.hiker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hiker.model.HikeEntity
import com.example.hiker.model.HikeRepository
import com.example.hiker.utils.Component
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel(val hikeRepository: HikeRepository) : ViewModel(){

    private var allHikes : LiveData<List<HikeEntity>>
    var isCompanyNameProper = false
    var isComponentAdd = false
    lateinit var symbol : String
    var addComponentRedundentList = ArrayList<Component>()
    init {
        allHikes = hikeRepository.getAllHikes()
    }

    fun getHikes() : LiveData<List<HikeEntity>>{
        return allHikes
    }

    fun savingData(hikeEntity:HikeEntity):Long{
          return hikeRepository.addHike(hikeEntity)
    }

    fun deleteHiker(deleteHikes: ArrayList<Int>){
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
        formattedAmount = (amountValue/1000).toString()
        when(amountLength){
           1 -> {
               //1 = 0.001
               formattedAmount = formattedAmount.substring(0,5)
           }
           2 -> {
               //10 = 0.01
               formattedAmount = formattedAmount.substring(0,4)
           }
           3 -> {
               //100 = 0.1
               formattedAmount = formattedAmount.substring(0,3)
           }
           4->{
               //5960 = 5.96 =
               formattedAmount = formattedAmount.substring(0,4)
           }
           5->{
               //59600 = 59.6
               formattedAmount = formattedAmount.substring(0,4)
           }
           6->{
               //159800 = 159.8
               formattedAmount = formattedAmount.substring(0,5)
           }
           7->{
               // 1059800 = 1059.8k
               formattedAmount = formattedAmount.substring(0,5)
           }
           8->{
               // 10598000 = 10598.000
               formattedAmount = formattedAmount.substring(0,5)
           }
           9->{
               // 105980000 = 105980.000
               formattedAmount = formattedAmount.substring(0,6)
           }
       }
    return formattedAmount
    }

    fun hikePercentage(currentCtc:Int,hikeAmount:Int):String{
        val diff = hikeAmount - currentCtc
        val hikePercentage = (diff / currentCtc) * 100
        return hikePercentage.toString()
    }


}