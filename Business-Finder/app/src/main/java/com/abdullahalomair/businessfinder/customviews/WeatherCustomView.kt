package com.abdullahalomair.businessfinder.customviews

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import java.util.*

class WeatherCustomView(context: Context,attrs: AttributeSet? = null)
    : View(context,attrs) {

        val timeArray = listOf(2,3,4,5)
        val degreeValue = listOf(22,15,18)

    init {


    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        
    }

    private fun getHeightValue(data: List<Int>):Int{
        return Collections.max(data)
    }
}