package com.ordinaryhuman.selfmadearoma.data

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromString(stringListString: String): List<String> {
        return stringListString.split(",").map { it }
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(separator = ",")
    }

    // Long型をDate型に変換
    @TypeConverter
    fun fromDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    // Date型をLong型に変換
    @TypeConverter
    fun dateToLong(date: Date?): Long? {
        return date?.let { it.time }
    }
}