package com.abdullahalomair.businessfinder.api.yelpapi

import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessesList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val API_KEY = "kp4oGZSIUI5s1UF5SCY8NTuknfG9jWRIeIo-M-rEumVmuyYHOECN5oteE7Xu87dcLpIuuHfUKcCvPBEHwWubutjgH89YMbhEG2ORRlDKYjRDZymDqfdjc-My-LXZX3Yx"
interface YelpApi {

    @Headers("Authorization: Bearer $API_KEY")
    @GET("search?")
    fun getBusinessesByLocation(
        @Query("location") location:String): Call<BusinessesList>
}