package com.abdullahalomair.businessfinder.controllers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.ImagesRecyclerviewDispalyBinding

class ImagesAdapter(private val context: Context,
                    private val images:List<String>)
    : RecyclerView.Adapter<ImagesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesHolder {
        val binding: ImagesRecyclerviewDispalyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.images_recyclerview_dispaly,
            parent,
            false
        )
        return ImagesHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagesHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int  = images.size
}