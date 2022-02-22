package com.ordinaryhuman.selfmadearoma.data.myArom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myAromas")
data class MyAroma(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var myAromaName: String,
    var amount: Int,
)
