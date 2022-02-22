package com.ordinaryhuman.selfmadearoma.data.aromaOil

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "aromaOils")
data class AromaOil(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val aromaOilId: Int,
    val aromaOilName: String,
    val aromaOilImage: String,
    val scientificName: String,
    val familyName: String,
    val precautions: List<String>,
    val type: String
)