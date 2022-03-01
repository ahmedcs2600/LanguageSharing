package com.app.cache.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
  @TypeConverter
  fun fromTimestamp(value: String): List<String> {
    val type = object : TypeToken<List<String>>() {}.type
    return Gson().fromJson(value,type)
  }

  @TypeConverter
  fun dateToTimestamp(date: List<String>): String {
    return Gson().toJson(date)
  }
}