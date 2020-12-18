package com.abdullahalomair.businessfinder.controllers


import android.graphics.Bitmap
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.ImagesRecyclerviewDispalyBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.*


class ImagesHolder(private val activity: MainActivity,private val binding:ImagesRecyclerviewDispalyBinding)
    :RecyclerView.ViewHolder(binding.root) {
    private val scope = CoroutineScope(Dispatchers.IO)

        fun bind(url:String){
             Glide.with(itemView).load(url)
                .thumbnail(Glide.with(itemView)
                .load(R.drawable.placeholder))
                .into(binding.restaurantImage)

            binding.restaurantImage.setOnClickListener {
               val isComplete =  scope.launch {
                    val image:Bitmap = Glide.with(itemView).asBitmap().load(url).fitCenter().submit().get()
                    withContext(Dispatchers.Main) {
                        ShowFullSizeImage.newInstance(image)
                            .show(activity.supportFragmentManager, "")
                    }
                }.isCompleted

                if (isComplete){
                    scope.cancel()
                }

            }
        }
}