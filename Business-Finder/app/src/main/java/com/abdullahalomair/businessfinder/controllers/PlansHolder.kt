package com.abdullahalomair.businessfinder.controllers

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.ListRecyclerviewBottomSheetBinding
import com.abdullahalomair.businessfinder.getWeatherAnimationName
import com.abdullahalomair.businessfinder.model.planmodel.PlanModel
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.ForeCastDay
import com.bumptech.glide.Glide
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


 private const val C_DEGREE = "℃"
class PlansHolder(private val binding:ListRecyclerviewBottomSheetBinding)
    :RecyclerView.ViewHolder(binding.root){

        fun bind(business:PlanModel){

            Glide.with(itemView)
                .load(business.businesses.imageUrl)
                .thumbnail(Glide.with(itemView).load(R.drawable.placeholder))
                .into(binding.businessImage)
            binding.businessTitle.text = business.businesses.name
            binding.businessType.text = business.businesses.categories.last().title
            binding.ratingBarPlan.rating = business.businesses.rating.toFloat()
            binding.colorView.setBackgroundColor(business.colorInt)
            currentWeather(business.weatherForeCast.forecast.foreCastDay)

            binding.dayTitle.text = business.planTitle
            val convertDueDate = SimpleDateFormat("EEE,dd hh:mm aa").format(business.dueDate)
            binding.dueDate.text = convertDueDate
            isBusinessOpen(business)




        }


    private fun isBusinessOpen(business:PlanModel){
            var finalText = "closed"
            try {
                val calender = Calendar.getInstance()
                calender.time = Date()
                val hours = business.businessDetails.hours[0].open[calender.get(Calendar.DAY_OF_WEEK)]

                val finalStart = SimpleDateFormat("hh:mm").format(Date()).replace(":","")
                val finalEnd = SimpleDateFormat("hh:mm").format(Date()).replace(":","")

                if (finalStart.toInt() > hours.start && finalEnd.toInt() < hours.end){
                    finalText = "Open"
                }
            binding.priceTagText.text = "$finalText . ${business.businessDetails.price}"
            } catch (e: Exception){}

    }

    private fun currentWeather(forecast: List<ForeCastDay>){
        var outOfDate = true
        val sdf =
            SimpleDateFormat("yyyy-MM-dd").format(Date())
        for (weatherForeCast in forecast){
                if (weatherForeCast.date == sdf){
                    outOfDate = false
                    binding.weatherStatus.text =
                        "${weatherForeCast.day.avgTemp_c}$C_DEGREE"
                    val getAnimationView = getWeatherAnimationName(weatherForeCast.day.condition.code)
                    binding.lottieAnimation.setAnimation(getAnimationView)
            }
        }
        if (outOfDate){
            binding.weatherStatus.text = "N/A"
            binding.lottieAnimation.setAnimation(R.raw.empty)
        }



    }
}