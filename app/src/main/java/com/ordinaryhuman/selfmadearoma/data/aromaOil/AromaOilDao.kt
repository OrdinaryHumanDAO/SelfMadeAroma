package com.ordinaryhuman.selfmadearoma.data.aromaOil

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AromaOilDao {
    @Query("SELECT * FROM aromaOils")
    fun getAromaOils(): List<AromaOil>

    @Query("SELECT * FROM aromaOils WHERE id = :AromaOilId")
    fun getAromaOil(AromaOilId: Int): AromaOil

    @Query("SELECT * FROM aromaOils WHERE aromaOilName LIKE '%' || :aromaOilName || '%'")
    fun findByAromaOilNameLike(aromaOilName: String): List<AromaOil>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<AromaOil>)
}