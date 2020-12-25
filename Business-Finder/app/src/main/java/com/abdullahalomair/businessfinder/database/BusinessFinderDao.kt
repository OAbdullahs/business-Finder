package com.abdullahalomair.businessfinder.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessesList

@Dao
interface BusinessFinderDao {

    @Query("SELECT * FROM BusinessesList")
    fun getBusinessList():LiveData<BusinessesList>?

    @Query("SELECT * FROM BusinessDetails WHERE id=(:id)")
    suspend fun getBusinessDetails(id:String):BusinessDetails?

    @Query("SELECT * FROM WeatherModel WHERE businessId=(:id)")
    suspend fun getWeatherModel(id:String):WeatherModel?

    @Query("SELECT * FROM WeatherForeCast WHERE businessId=(:id)")
    fun getWeatherForeCast(id:String):LiveData<WeatherForeCast>?

    @Insert
    fun insertBusinessList(businessesList: BusinessesList)

    @Insert
    fun insertBusinessDetails(businessDetails: BusinessDetails)

    @Insert
    fun insertWeatherModel(weatherModel: WeatherModel)

    @Insert
    fun insertWeatherForeCast(weatherForeCast: WeatherForeCast)

    @Query("DELETE FROM BusinessesList")
    fun deleteBusinessList()

    @Query("DELETE FROM BusinessDetails")
    fun deleteBusinessDetails()

    @Query("DELETE FROM WeatherModel")
    fun deleteWeatherModel()

    @Query("DELETE FROM WeatherForeCast")
    fun deleteWeatherForeCast()




}