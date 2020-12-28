package com.abdullahalomair.businessfinder.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.RawRes
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView

object Utils {
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

        @JvmStatic
        @BindingAdapter("android:background")
        fun setBackgroundForTextView(textView: TextView, @ColorRes color:Int?){
            if (color == 0) return
            if (color != null) {
                textView.setBackgroundResource(color)
            }else{
                textView.background = null
            }
        }

}