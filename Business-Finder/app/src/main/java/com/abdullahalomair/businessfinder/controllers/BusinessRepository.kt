package com.abdullahalomair.businessfinder.controllers

import android.content.Context
import androidx.lifecycle.LiveData
import com.abdullahalomair.businessfinder.api.yelpapi.yelpfetcher.YelpFetcher
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessesList

class BusinessRepository private constructor(context: Context){


    fun getBusinessList(location:String):LiveData<BusinessesList>{
        return YelpFetcher().getBusinessesByLocation(location)
    }
    fun getBusinessDetail(businessId: String): LiveData<BusinessDetails>{
        return YelpFetcher().getBusinessDetails(businessId)
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