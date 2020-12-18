package com.abdullahalomair.businessfinder.controllers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.DisplayFullImageSizeBinding
import com.bumptech.glide.Glide


private const val IMAGE = "IMAGE"
class ShowFullSizeImage: DialogFragment() {
    private lateinit var binding: DisplayFullImageSizeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = DataBindingUtil.inflate(
             layoutInflater,
             R.layout.display_full_image_size,
             container,
             false
         )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = arguments?.getParcelable<Bitmap>(IMAGE)
        if (image != null) {
            binding.fullSizedImage.setImageBitmap(image)
        }
    }

    companion object{
        fun newInstance(bitmap: Bitmap): ShowFullSizeImage {
           val args = Bundle()
            args.putParcelable(IMAGE, bitmap)
            return ShowFullSizeImage().apply {
                arguments = args
            }
        }
    }

}