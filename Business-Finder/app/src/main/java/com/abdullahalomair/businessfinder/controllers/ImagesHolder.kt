package com.abdullahalomair.businessfinder.controllers


import android.graphics.Bitmap
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.ImagesRecyclerviewDispalyBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.*


class ImagesHolder(private val binding:ImagesRecyclerviewDispalyBinding)
    :RecyclerView.ViewHolder(binding.root) {

        fun bind(url:String){
             Glide.with(itemView).load(url)
                .thumbnail(Glide.with(itemView)
                .load(R.drawable.placeholder))
                .into(binding.restaurantImage)

        }
}