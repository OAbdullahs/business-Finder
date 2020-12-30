package com.abdullahalomair.businessfinder.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.TimeDetailRecyclerviewBinding
import com.abdullahalomair.businessfinder.model.yelpmodel.Open

class TimeDetailAdapter(private val hours: List<Open>):
    RecyclerView.Adapter<TimeDetailHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeDetailHolder {
        val binding: TimeDetailRecyclerviewBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.time_detail_recyclerview,
                parent,
                false
            )
        return TimeDetailHolder(binding)

    }

    override fun onBindViewHolder(holder: TimeDetailHolder, position: Int) {
        holder.bind(hours[position])
    }

    override fun getItemCount(): Int = hours.size
}