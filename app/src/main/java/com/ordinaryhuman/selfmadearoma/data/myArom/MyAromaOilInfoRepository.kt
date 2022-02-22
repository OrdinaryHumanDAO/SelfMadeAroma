package com.ordinaryhuman.selfmadearoma.data.myArom

import javax.inject.Inject

class MyAromaOilInfoRepository @Inject constructor (private val dao: MyAromaOilInfoDao) {

    suspend fun insertMyAromaOilInfo(myAromaOilInfo: MyAromaOilInfo){
        dao.insertMyAromaOilInfo(myAromaOilInfo)
    }

    suspend fun updateMyAromaOilInfo(myAromaOilInfo: MyAromaOilInfo){
        dao.updateMyAromaOilInfo(myAromaOilInfo)
    }
    suspend fun deleteMyAromaOilInfo(myAromaOilInfo: MyAromaOilInfo){
        dao.deleteMyAromaOilInfo(myAromaOilInfo)
    }
}