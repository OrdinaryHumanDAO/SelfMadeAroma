package com.example.selfmadearoma.data.myArom

import androidx.room.Embedded
import androidx.room.Relation

data class MyAromaWithMyAromaOilInfo(
    @Embedded val myAroma: MyAroma,
    @Relation(
        parentColumn = "myAromaName",
        entityColumn = "myAromaCreatorName"
    )
    val myAromaOilInfo: MutableList<MyAromaOilInfo>
)
