package com.abdullahalomair.businessfinder.utils

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
    }
}