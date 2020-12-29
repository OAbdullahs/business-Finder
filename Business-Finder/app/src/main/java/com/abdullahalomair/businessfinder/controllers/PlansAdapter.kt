package com.abdullahalomair.businessfinder.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.ListRecyclerviewBottomSheetBinding
import com.abdullahalomair.businessfinder.model.planmodel.PlanModel

class PlansAdapter(private val business:List<PlanModel>)
    :RecyclerView.Adapter<PlansHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlansHolder {
        val binding: ListRecyclerviewBottomSheetBinding
        = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_recyclerview_bottom_sheet,
            parent,
        false)
        return PlansHolder(binding)
    }

    override fun onBindViewHolder(holder: PlansHolder, position: Int) {
        holder.bind(business[position])
    }

    override fun getItemCount(): Int = business.size
}