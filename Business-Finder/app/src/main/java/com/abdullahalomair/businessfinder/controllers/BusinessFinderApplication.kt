package com.abdullahalomair.businessfinder.controllers

import android.app.Application
import android.util.Log
import com.abdullahalomair.businessfinder.sharedpreference.EncryptSharedPreferences

class BusinessFinderApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        val sharedPreferenceCounter = EncryptSharedPreferences.getNumberCounter(this)
        if (sharedPreferenceCounter == -1){
            EncryptSharedPreferences.resetNumberCounter(this)
        }
        BusinessRepository.initialize(this)
    }

}