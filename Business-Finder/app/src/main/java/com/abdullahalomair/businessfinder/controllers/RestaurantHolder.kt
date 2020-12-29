package com.abdullahalomair.businessfinder.controllers


import android.widget.Toast
import androidx.recyclerview.widget.*
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.callbacks.CallBacks

import com.abdullahalomair.businessfinder.databinding.BusinessRecyclerviewBinding
import com.abdullahalomair.businessfinder.model.navigator.Navigator

import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import com.bumptech.glide.Glide
import java.lang.Exception


class RestaurantHolder(
    private val binding: BusinessRecyclerviewBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private var callback: CallBacks? = null

    init {
        callback = itemView.context as CallBacks

    }

    fun bind(allData: Businesses)
    {
        Glide.with(itemView)
            .load(allData.imageUrl)
            .thumbnail(Glide.with(itemView).load(R.drawable.placeholder))
            .into(binding.restaurantMainImage)


        itemView.setOnClickListener {
            try {
                callback?.applicationNavigator(
                    Navigator.BUSINESS_DETAILS,
                    businesses = allData
                )
            }catch (e:Exception){
                Toast.makeText(itemView.context,
                    itemView.context.getText(R.string.navigation_error),
                    Toast.LENGTH_SHORT).show()
            }
        }

        binding.apply {
            businessNameText.apply {
                text = allData.name
                background = null
            }
            businessTypeText.apply {
                text = allData.categories.last().title
                background = null
            }
            ratingBar.rating = allData.rating.toFloat()


        }


    }




}


