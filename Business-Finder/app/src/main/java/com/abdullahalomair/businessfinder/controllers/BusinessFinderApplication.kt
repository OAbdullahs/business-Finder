package com.abdullahalomair.businessfinder.controllers

import android.app.Application
import android.util.Log
import com.abdullahalomair.businessfinder.sharedpreference.EncryptSharedPreferences
import com.blongho.country_data.World

class BusinessFinderApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        World.init(this)
        EncryptSharedPreferences.initialize(this)
        BusinessRepository.initialize(this)
    }

}