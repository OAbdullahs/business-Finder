package com.abdullahalomair.businessfinder.controllers


import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.databinding.CategoryRecylerviewMainBinding


class CategoryHolder(
    private val binding: CategoryRecylerviewMainBinding,
    private val updateView: (category:String) -> Unit
    )
    : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(name: String, quantity:Int){
            binding.apply {
                    categoryName.text = "$name ($quantity)"

            }
            itemView.setOnClickListener {
                updateView(name)
            }
            
        }
}