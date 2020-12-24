package com.abdullahalomair.businessfinder.database

import androidx.room.TypeConverter
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.ForeCastDay
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.Forecast
import com.google.gson.Gson
import java.util.*


class BusinessFinderTypeConverter {

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }
    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun fromForeCast(list: Forecast):String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromListOfString(list: List<String>):String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun toListOfString(list: String):List<String>{
        return Gson().fromJson(list, listOf<String>()::class.java)
    }
    @TypeConverter
    fun toForeCast(list: String):Forecast{
        return Gson().fromJson(list, Forecast::class.java)
    }

}