package com.abdullahalomair.businessfinder.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.RawRes
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView

class Utils {
    companion object{
        @JvmStatic
        @BindingAdapter("lottie_rawRes")
        fun setAnimation(view: LottieAnimationView,@RawRes rawId:Int){
            if (rawId == 0) return
            view.setAnimation(rawId)
        }
        @JvmStatic
        @BindingAdapter("load_image")
        fun setImageViewResource( imageView: ImageView, drawable: Drawable?){
            if (drawable == null) return
            imageView.setImageDrawable(drawable)
        }
    }

}