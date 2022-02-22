package com.ordinaryhuman.selfmadearoma.data.myArom

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MyAromaDao {

    @Transaction
    @Query("SELECT * FROM myAromas")
    fun getMyAromas(): Flow<List<MyAroma>>

    @Transaction
    @Query("SELECT * FROM myAromas WHERE id = :id")
    fun getMyAroma(id: Int): MyAromaWithMyAromaOilInfo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMyAroma(myAroma: MyAroma)

    @Update
    suspend fun updateMyAroma(myAroma: MyAroma)

    @Delete
    suspend fun deleteMyAroma(myAroma: MyAroma)

    @Query("DELETE FROM myAromas")
    suspend fun deleteAll()
}