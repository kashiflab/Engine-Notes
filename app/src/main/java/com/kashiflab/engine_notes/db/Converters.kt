package com.kashiflab.engine_notes.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun saveIntList(list: List<Int>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun getIntList(list: String): List<Int> {
        return Gson().fromJson(
            list,
            object : TypeToken<List<Int>>() {}.type
        )
    }
}