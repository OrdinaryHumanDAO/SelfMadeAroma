package com.ordinaryhuman.selfmadearoma.data.myArom

import javax.inject.Inject

class MyAromaRepository @Inject constructor (private val dao: MyAromaDao) {

    fun getMyAromas() = dao.getMyAromas()
    fun getMyAroma(myAromaId: Int) = dao.getMyAroma(myAromaId)
    suspend fun insertMyAroma(myAroma: MyAroma) = dao.insertMyAroma(myAroma)
    suspend fun updateMyAroma(myAroma: MyAroma) = dao.updateMyAroma(myAroma)
    suspend fun deleteMyAroma(myAroma: MyAroma) = dao.deleteMyAroma(myAroma)
    suspend fun deleteAll() = dao.deleteAll()
}