package com.abdullahalomair.businessfinder.viewmodels

import android.content.Context

import android.widget.Toast
import androidx.databinding.BaseObservable



class CategoryRecyclerViewMain( private val  context: Context): BaseObservable() {


    var categoryName:String? = null
        set(value) {
                field = "$value ($categorySize)"
                notifyChange()
        }

    var categorySize: Int = 0



    fun onBottomClick() {
        Toast.makeText(context,categoryName,Toast.LENGTH_LONG).show()
    }


}