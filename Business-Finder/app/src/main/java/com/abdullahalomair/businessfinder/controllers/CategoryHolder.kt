package com.abdullahalomair.businessfinder.controllers


import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.databinding.CategoryRecylerviewMainBinding
import com.abdullahalomair.businessfinder.viewmodels.CategoryRecyclerViewMain

class CategoryHolder(private val context: Context,
    private val binding: CategoryRecylerviewMainBinding)
    : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.viewModel = CategoryRecyclerViewMain(context)
        }
        fun bind(name: String, quantity:Int){
            binding.apply {
                    viewModel!!.categorySize = quantity
                    viewModel!!.categoryName = name
            }
            
        }
}