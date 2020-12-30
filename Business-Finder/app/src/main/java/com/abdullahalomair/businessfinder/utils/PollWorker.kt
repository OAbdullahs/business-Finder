package com.abdullahalomair.businessfinder.utils

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.abdullahalomair.businessfinder.controllers.BusinessRepository
import kotlinx.coroutines.runBlocking

private const val TAG = "PollWorker"
class PollWorker(context: Context,
                 workerParams: WorkerParameters): Worker(context,workerParams) {

    override fun doWork(): Result {
        val applicationContext = applicationContext
        BusinessRepository.initialize(applicationContext)
        val repository = BusinessRepository.get()
        if (repository.hasNetwork()){


        val photoIdFromList: MutableList<String> = mutableListOf()
        val photoIdBusinessDetail:MutableList<String> = mutableListOf()
        val photoIdWeather:MutableList<String> = mutableListOf()

        val businessList = repository.getBusinessListPollWorker()
        try {
            if (businessList != null) {
                for (business in businessList.businesses) {
                    photoIdFromList.add(business.id)
                    runBlocking {
                        //Update BusinessDetails every day
                        try {
                                val newBusinessDetail = repository.getBusinessDetail(business.id)
                                val getCurrentBusinessDetail =
                                    repository.getBusinessDetailLocal(business.id)
                                if (getCurrentBusinessDetail != null) {
                                    newBusinessDetail.id_uuid = getCurrentBusinessDetail.id_uuid
                                    repository.updateBusinessDetailsPollWorker(newBusinessDetail)
                                } else {
                                    repository.insertBusinessDetailsLocal(newBusinessDetail)
                                }
                                //Update WeatherForecast every day
                                val location =
                                    "${business.coordinates.latitude},${business.coordinates.longitude}"
                                val newWeatherData = repository.getWeatherForecast(location)
                                val localWeatherData =
                                    repository.getWeatherForecastLocal(business.id)
                                if (localWeatherData != null) {
                                    newWeatherData.id = localWeatherData.id
                                    newWeatherData.businessId = localWeatherData.businessId
                                    repository.updateWeatherForeCastPollWorker(newWeatherData)
                                } else {
                                    repository.insertWeatherForeCastLocal(newWeatherData)
                                }
                        }catch (e:Exception){
                            return@runBlocking Result.retry()
                        }
                    }
                }
                //delete empty values
                val businessDetails = repository.getBusinessDetailsPollWorker()
                if (businessDetails != null) {
                    for (list in businessDetails) {
                        if (list.id.isNullOrEmpty()){
                            repository.deleteBusinessDetailsPollWorker(list.id)
                        }else{
                            photoIdBusinessDetail.add(list.id)
                        }

                    }
                }
                val weatherList = repository.getWeatherForeCastPollWorker()
                if (weatherList != null) {
                    for (list in weatherList) {
                        if (list.businessId.isNullOrEmpty()){
                            repository.deleteWeatherForeCastPollWorker(list.businessId)
                        }else{
                            photoIdWeather.add(list.businessId)
                        }

                    }
                }

            }

            if (photoIdBusinessDetail.isNotEmpty() &&
                photoIdWeather.isNotEmpty() &&
                photoIdFromList.isNotEmpty()){
               for (originalId in photoIdWeather){
                   val findBusinessDetail =
                       photoIdFromList.find { it ==  originalId}
                   if (findBusinessDetail == null){
                       repository.deleteBusinessDetailsPollWorker(originalId)
                   }
               }
                for (ids in photoIdWeather){
                    val findWeatherForCast = photoIdWeather.find { it == ids }
                    if (findWeatherForCast == null){
                        repository.deleteWeatherForeCastPollWorker(ids)
                    }
                }


            }
        }catch (e:Exception){
           return Result.failure()
        }
        }
        return Result.success()

    }
}