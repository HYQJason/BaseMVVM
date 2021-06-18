package com.hx.ice.util

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import android.graphics.drawable.Drawable
import android.graphics.drawable.ColorDrawable

import androidx.databinding.BindingConversion







object GlideAdapter {

    @BindingAdapter("android:src")
    @JvmStatic
    fun setSrc(view: ImageView, url: String) {
        Glide.with(view.context).load(url).into(view)
    }

    @BindingAdapter("android:src")
    @JvmStatic
    fun setSrc(view: ImageView, bitmap: Bitmap) {
        view.setImageBitmap(bitmap)
    }


    @BindingAdapter("android:src")
    @JvmStatic
    fun setSrc(view: ImageView, resId: Int) {
        view.setImageResource(resId)
    }


    @BindingAdapter("android:src", "app:placeHolder", "app:error")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String?, holderDrawable: Drawable, errorDrawable: Drawable) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(holderDrawable)
            .error(errorDrawable)
            .into(imageView)
    }

    @BindingConversion
    fun convertColorToDrawable(color: Int): ColorDrawable {
        return ColorDrawable(color)
    }
}