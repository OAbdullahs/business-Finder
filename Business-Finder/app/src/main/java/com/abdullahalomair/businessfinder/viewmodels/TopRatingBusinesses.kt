package com.abdullahalomair.businessfinder.viewmodels

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import com.abdullahalomair.businessfinder.controllers.BusinessRepository
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails

class TopRatingBusinesses: BaseObservable() {

    private val repository = BusinessRepository.get()

    fun getBusinessDetail(businessId:String): LiveData<BusinessDetails> {
        return repository.getBusinessDetail(businessId)
    }
    var businessName:String? = null
        set(value) {
            field = value
            notifyChange()
        }
    var ratingBusiness:Float? = null
        set(value) {
            field = value
            notifyChange()
        }
    var businessType:String? = null
        set(value) {
            field = value
            notifyChange()
        }
    var isBusinessOpen:String? = null
        set(value) {
            field = value
            notifyChange()
        }
}