package com.abdullahalomair.businessfinder.controllers

import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.TimeDetailRecyclerviewBinding
import com.abdullahalomair.businessfinder.model.yelpmodel.Open
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class TimeDetailHolder(private val binding: TimeDetailRecyclerviewBinding)
    : RecyclerView.ViewHolder(binding.root) {


        fun bind(open: Open){
            binding.dayName.text = setDayName(open.day)
            try {
                val start = open.start.toString().chunked(2).joinToString(separator = ":")
                val sdfStart = SimpleDateFormat("H:mm").parse(start)
                val finalStart = SimpleDateFormat("hh:mm aa").format(sdfStart)
                binding.startTime.text = finalStart
            }catch (e:Exception){
                binding.startTime.text = itemView.context.getText(R.string.error_time_parc)
            }
            try {
                val end = open.end.toString().chunked(2).joinToString(separator = ":")
                val sdfEnd = SimpleDateFormat("H:mm").parse(end)
                val finalEnd = SimpleDateFormat("hh:mm aa").format(sdfEnd)
                binding.endTime.text = finalEnd
            }catch (e: Exception){
                binding.endTime.text = itemView.context.getText(R.string.error_time_parc)
            }

        }

    private fun setDayName(day: Int):String{

        return when(day){
            0 -> itemView.context.getString(R.string.saturday)
            1 -> itemView.context.getString(R.string.monday)
            2 -> itemView.context.getString(R.string.tuesday)
            3 -> itemView.context.getString(R.string.wednesday)
            4 -> itemView.context.getString(R.string.thursday)
            5 -> itemView.context.getString(R.string.friday)
            6 -> itemView.context.getString(R.string.saturday)
            else -> ""
        }
    }

}