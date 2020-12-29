package com.abdullahalomair.businessfinder.database

import androidx.room.TypeConverter
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.ForeCastDay
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.Forecast
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import com.abdullahalomair.businessfinder.model.yelpmodel.Categories
import com.abdullahalomair.businessfinder.model.yelpmodel.Hours
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let {
            Date(it)
        }
    }
    @TypeConverter
    fun fromListForCast(listOfForeCastDay: MutableList<ForeCastDay>):String?{
        val type = object: TypeToken<MutableList<ForeCastDay>>() {}.type
        return Gson().toJson(listOfForeCastDay, type)
    }
    @TypeConverter
    fun toListForCast(listOfForeCastDay: String):MutableList<ForeCastDay>?{
        val type = object: TypeToken<MutableList<ForeCastDay>>() {}.type
        return Gson().fromJson(listOfForeCastDay, type)
    }
    @TypeConverter
    fun toListOfCategory(categories: String):MutableList<Categories>?{
        val type = object: TypeToken<MutableList<Categories>>() {}.type
        return Gson().fromJson(categories, type)
    }
    @TypeConverter
    fun fromListOfCategory(categories: MutableList<Categories>):String?{
        val type = object: TypeToken<MutableList<Categories>>() {}.type
        return Gson().toJson(categories, type)
    }
    @TypeConverter
    fun toListOfString(strings: String):MutableList<String>?{
        val type = object: TypeToken<MutableList<String>>() {}.type
        return Gson().fromJson(strings, type)
    }
    @TypeConverter
    fun fromListOfString(strings: MutableList<String>):String?{
        val type = object: TypeToken<MutableList<String>>() {}.type
        return Gson().toJson(strings, type)
    }
    @TypeConverter
    fun toListOfHours(hours: String):MutableList<Hours>?{
        val type = object: TypeToken<MutableList<Hours>>() {}.type
        return Gson().fromJson(hours, type)
    }
    @TypeConverter
    fun fromListOfHours(hours: MutableList<Hours>):String?{
        val type = object: TypeToken<MutableList<Hours>>() {}.type
        return Gson().toJson(hours)
    }
    @TypeConverter
    fun toListOfBusinesses(businesses: String):MutableList<Businesses>?{
        val type = object: TypeToken<MutableList<Businesses>>() {}.type
        return Gson().fromJson(businesses, type)
    }
    @TypeConverter
    fun fromListOfBusinesses(businesses: MutableList<Businesses>):String?{
        val type = object: TypeToken<MutableList<Businesses>>() {}.type
        return Gson().toJson(businesses,type)
    }

}