package com.abdullahalomair.businessfinder.viewmodels

import androidx.annotation.RawRes
import androidx.databinding.BaseObservable

class WeatherDetailViewModel:BaseObservable() {


    var cityName:String? = null
        set(value) {
            field = value
            notifyChange()
        }
    var currentTime:String? = null
        set(value) {
            field = value
            notifyChange()
        }
    @RawRes var weatherAnimation:Int? = null
        set(value) {
            field = value
            notifyChange()
        }
    var weatherStatusText:String? = null
        set(value) {
        field = value
        notifyChange()
    }
    var weatherDegree:String? = null
        set(value) {
            field = value
            notifyChange()
        }
    var maxTemp:String? = null
    set(value) {
        field = value
        notifyChange()
    }
    var minTemp:String? = null
        set(value) {
            field = value
            notifyChange()
        }
}