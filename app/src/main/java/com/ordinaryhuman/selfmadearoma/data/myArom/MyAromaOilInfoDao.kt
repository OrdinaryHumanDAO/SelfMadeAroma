package com.ordinaryhuman.selfmadearoma.data.myArom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface MyAromaOilInfoDao {

    @Insert
    suspend fun insertMyAromaOilInfo(myAromaOilInfo: MyAromaOilInfo)

    @Update
    suspend fun updateMyAromaOilInfo(myAromaOilInfo: MyAromaOilInfo)

    @Delete
    suspend fun deleteMyAromaOilInfo(myAromaOilInfo: MyAromaOilInfo)
}