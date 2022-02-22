package com.ordinaryhuman.selfmadearoma.data.myArom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myAromaOilsInfo")
data class MyAromaOilInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var myAromaCreatorName: String,
    var aromaOilName: String,
    var aromaOilAmount: Int,
)