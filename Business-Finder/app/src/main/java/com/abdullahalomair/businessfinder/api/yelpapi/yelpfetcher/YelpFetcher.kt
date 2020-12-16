package com.abdullahalomair.businessfinder.api.yelpapi.yelpfetcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abdullahalomair.businessfinder.api.yelpapi.YelpApi
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessesList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class YelpFetcher {

    private val yelpApi: YelpApi
    init {
        val retrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://api.yelp.com/v3/businesses/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        yelpApi = retrofit.create(YelpApi::class.java)

    }

     fun  getBusinessesByLocation(location:String): LiveData<BusinessesList> {
        val call: Call<BusinessesList> = yelpApi.getBusinessesByLocation(location)
        val responseLiveData: MutableLiveData<BusinessesList> = MutableLiveData()
        call.enqueue(object : Callback<BusinessesList>{
            override fun onResponse(
                call: Call<BusinessesList>,
                response: Response<BusinessesList>
            ) {
                responseLiveData.value =  response.body() as BusinessesList
            }

            override fun onFailure(call: Call<BusinessesList>, t: Throwable) {
                throw t
            }
        })
        return responseLiveData
    }
}