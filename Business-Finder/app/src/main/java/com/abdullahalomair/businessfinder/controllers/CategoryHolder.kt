package com.abdullahalomair.businessfinder.controllers


import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.room.PrimaryKey
import com.abdullahalomair.businessfinder.databinding.CategoryRecylerviewMainBinding
import com.abdullahalomair.businessfinder.viewmodels.MainFragmentViewModel


class CategoryHolder(
    private val binding: CategoryRecylerviewMainBinding,
    private val updateView: (category:String) -> Unit
    )
    : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, quantity:Int){
            binding.apply {
                    categoryName.text = "$name ($quantity)"

            }
            itemView.setOnClickListener {
                updateView(name)
            }
            
        }
}