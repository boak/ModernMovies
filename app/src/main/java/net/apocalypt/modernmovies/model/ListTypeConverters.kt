package net.apocalypt.modernmovies.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.lang.reflect.Type

class ListTypeConverters {

    @TypeConverter
    fun fromString(value: String): List<Int>? {
        val listType = object : TypeToken<List<String>>() {

        }.type
        return Gson().fromJson<List<Int>>(value, listType)
    }

    @TypeConverter
    fun fromArrayListInt(list: List<Int>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

}
