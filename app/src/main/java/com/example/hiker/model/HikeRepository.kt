package com.example.hiker.model

import androidx.lifecycle.LiveData

class HikeRepository(val dao: HikeDao) {



    fun getAllHikes(): LiveData<List<HikeEntity>> {
        return dao.getAll()
    }

    //insert hike details to room
    fun addHike(hike: HikeEntity) : Long {
        return dao.addHike(hike)
    }

    //delete hike entity from room
    suspend fun deleteHikes(deleteHikes: ArrayList<HikeEntity>) : Long {
        return dao.deleteHike(deleteHikes)
    }


}