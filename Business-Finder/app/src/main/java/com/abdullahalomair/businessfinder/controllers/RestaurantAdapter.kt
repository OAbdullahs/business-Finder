package com.abdullahalomair.businessfinder.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.BusinessRecyclerviewBinding
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import kotlinx.coroutines.*

class RestaurantAdapter(private val businesses: List<Businesses>,
                         getWeatherDetail: MutableList<WeatherForeCast>,
                         getBusinessDetail: List<BusinessDetails>,
                        )
    :RecyclerView.Adapter<RestaurantHolder>() {
    private var weatherDetail = getWeatherDetail
    set(value){
        notifyDataSetChanged()
        field = value
    }
    private var businessModel = getBusinessDetail
        set(value) {
            notifyDataSetChanged()
            field = value
        }
private val scope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
        val binding: BusinessRecyclerviewBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.business_recyclerview,
                parent,
                false
        )
        return RestaurantHolder(binding)
    }



    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
        scope.launch {
        val businessDetail = businessModel.find{ list -> list.id == businesses[position].id}
        val weatherDetail = weatherDetail.find { list -> list.businessId == businesses[position].id }
            withContext(Dispatchers.Main){
                holder.bind(businesses[position],
                    businessDetail ?: BusinessDetails(),
                    weatherDetail?: WeatherForeCast()
                )
            }
        }

    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        scope.cancel()
    }

    override fun getItemCount(): Int = businesses.size

}