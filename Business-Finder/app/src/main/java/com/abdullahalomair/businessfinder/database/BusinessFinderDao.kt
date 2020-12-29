package com.abdullahalomair.businessfinder.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.abdullahalomair.businessfinder.model.planmodel.PlanModel
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessesList

@Dao
interface BusinessFinderDao {

    @Query("SELECT * FROM BusinessesList")
    fun getBusinessList():LiveData<BusinessesList?>

    @Query("SELECT * FROM PlanModel")
    fun getPlanDetails():LiveData<MutableList<PlanModel>>

    @Query("SELECT * FROM BusinessDetails WHERE id=(:id)")
    suspend fun getBusinessDetails(id:String):BusinessDetails?

    @Query("SELECT * FROM WeatherForeCast WHERE businessId=(:id)")
    suspend fun getWeatherForeCast(id:String):WeatherForeCast?

    @Insert
    fun insertPlanDetail(planModel: PlanModel)

    @Insert
    fun insertBusinessList(businessesList: BusinessesList)

    @Insert
    fun insertBusinessDetails(businessDetails: BusinessDetails)

    @Insert
    fun insertWeatherForeCast(weatherForeCast: WeatherForeCast)

    @Query("DELETE FROM BusinessesList")
    fun deleteBusinessList()

    @Delete
    fun deletePlanDetail(planModel: PlanModel?)


    // Worker Manager Requests
    @Query("SELECT * FROM BusinessesList")
    fun getBusinessListPollWorker():BusinessesList?

    @Query("SELECT * FROM BusinessDetails")
    fun getBusinessDetailsPollWorker():List<BusinessDetails>?

    @Query("SELECT * FROM WeatherForeCast ")
    fun getWeatherForeCastPollWorker():List<WeatherForeCast>?

    @Update
    fun updateBusinessDetailsPollWorker(businessDetails: BusinessDetails)
    @Update
    fun updateWeatherForeCastPollWorker(weatherForeCast: WeatherForeCast)

    @Query("DELETE FROM BusinessDetails WHERE id=(:id)")
    fun deleteBusinessDetailsPollWorker(id: String)

    @Query("DELETE FROM WeatherForeCast WHERE businessId=(:id)")
    fun deleteWeatherForeCastPollWorker(id: String)




}