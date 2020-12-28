package com.abdullahalomair.businessfinder.controllers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.abdullahalomair.businessfinder.api.weatherapi.weatherfetcher.WeatherFetcher
import com.abdullahalomair.businessfinder.api.yelpapi.yelpfetcher.YelpFetcher
import com.abdullahalomair.businessfinder.database.BusinessFinderDataBase
import com.abdullahalomair.businessfinder.model.planmodel.PlanModel
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessesList
import java.util.concurrent.Executors

private const val DATABASE_NAME = "BusinessFinder-database"
class BusinessRepository private constructor(private val context: Context){
    private val database : BusinessFinderDataBase = Room.databaseBuilder(
        context.applicationContext,
        BusinessFinderDataBase::class.java,
        DATABASE_NAME
    ).build()
    private val dataBaseDao = database.getBusinessFinderDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun hasNetwork(): Boolean {
        var isConnected = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    //Get data from database
    fun getBusinessListLocal():LiveData<BusinessesList>?{
        return dataBaseDao.getBusinessList()
    }
    suspend fun getBusinessDetailLocal(businessId: String): BusinessDetails?{
        return dataBaseDao.getBusinessDetails(businessId)
    }
    suspend fun getWeatherForecastLocal(businessId: String): WeatherForeCast? {
        return dataBaseDao.getWeatherForeCast(businessId)
    }

    fun getPlansList():LiveData<MutableList<PlanModel>>{
        return dataBaseDao.getPlanDetails()
    }
    fun deletePlanDetail(planModel: PlanModel){
        executor.execute {
            dataBaseDao.deletePlanDetail(planModel)
        }
    }
    fun insertPlanDetail(planModel: PlanModel){
        executor.execute {
            dataBaseDao.insertPlanDetail(planModel)
        }
    }

    //Insert Data to Database
    fun insertBusinessListLocal(businessesList: BusinessesList){
        executor.execute {
            dataBaseDao.insertBusinessList(businessesList)
        }
    }
    fun insertBusinessDetailsLocal(businessDetails: BusinessDetails){
        executor.execute {
            dataBaseDao.insertBusinessDetails(businessDetails)
        }
    }
    fun insertWeatherForeCastLocal(weatherForeCast: WeatherForeCast){
        executor.execute {
            dataBaseDao.insertWeatherForeCast(weatherForeCast)
        }
    }

    //Delete from Database
    fun deleteBusinessListLocal(){
        executor.execute {
            dataBaseDao.deleteBusinessList()
        }
    }
    fun deleteBusinessDetailsLocal(){
        executor.execute {
            dataBaseDao.deleteBusinessDetails()
        }
    }
    fun deleteWeatherForeCastLocal(){
        executor.execute {
            dataBaseDao.deleteWeatherForeCast()
        }
    }



    fun getBusinessList(location:String):LiveData<BusinessesList>{
        return YelpFetcher().getBusinessesByLocation(location)
    }
    suspend fun getBusinessDetail(businessId: String): BusinessDetails{
        return YelpFetcher().getBusinessDetails(businessId)
    }

    suspend fun  getWeatherForecast(location: String): WeatherForeCast {
        return WeatherFetcher().getWeatherForeCast(location)
    }

    companion object{
            private var INSTANCE: BusinessRepository? = null
            fun initialize(context: Context) {
                if (INSTANCE == null) {
                    INSTANCE = BusinessRepository(context)
                }
            }
            fun get(): BusinessRepository {
                return INSTANCE ?:
                throw IllegalStateException("BusinessRepository must be initialized")
            }

    }
}