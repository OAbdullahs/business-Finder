package com.abdullahalomair.businessfinder.database

import androidx.room.TypeConverter
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.ForeCastDay
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.Forecast
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import com.abdullahalomair.businessfinder.model.yelpmodel.Categories
import com.abdullahalomair.businessfinder.model.yelpmodel.Hours
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
    fun fromListForCast(listOfForeCastDay: List<ForeCastDay>):String?{
        return Gson().toJson(listOfForeCastDay)
    }
    @TypeConverter
    fun toListForCast(listOfForeCastDay: String):List<ForeCastDay>?{
        return Gson().fromJson(listOfForeCastDay, listOf<ForeCastDay>()::class.java)
    }
    @TypeConverter
    fun toListOfCategory(categories: String):List<Categories>?{
        return Gson().fromJson(categories, listOf<Categories>()::class.java)
    }
    @TypeConverter
    fun fromListOfCategory(categories: List<Categories>):String?{
        return Gson().toJson(categories)
    }
    @TypeConverter
    fun toListOfString(strings: String):List<String>?{
        return Gson().fromJson(strings, listOf<String>()::class.java)
    }
    @TypeConverter
    fun fromListOfString(strings: List<String>):String?{
        return Gson().toJson(strings)
    }
    @TypeConverter
    fun toListOfHours(hours: String):List<Hours>?{
        return Gson().fromJson(hours, listOf<Hours>()::class.java)
    }
    @TypeConverter
    fun fromListOfHours(hours: List<Hours>):String?{
        return Gson().toJson(hours)
    }
    @TypeConverter
    fun toListOfBusinesses(businesses: String):List<Businesses>?{
        return Gson().fromJson(businesses, listOf<Businesses>()::class.java)
    }
    @TypeConverter
    fun fromListOfBusinesses(businesses: List<Businesses>):String?{
        return Gson().toJson(businesses)
    }

}