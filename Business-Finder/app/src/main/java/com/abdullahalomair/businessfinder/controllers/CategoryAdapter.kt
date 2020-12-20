package com.abdullahalomair.businessfinder.controllers

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.CategoryRecylerviewMainBinding
import com.abdullahalomair.businessfinder.model.yelpmodel.Categories
import kotlinx.coroutines.*

class CategoryAdapter(private val context: Context,
                      private val data:List<Pair<String, Int>>,
                      private val updateView: (category:String) -> Unit
                      )
    :RecyclerView.Adapter<CategoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val binding:CategoryRecylerviewMainBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.category_recylerview_main,
            parent,
            false
        )
        return CategoryHolder(binding,updateView)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(data[position].first, data[position].second)
        }



    override fun getItemCount(): Int = data.size


}