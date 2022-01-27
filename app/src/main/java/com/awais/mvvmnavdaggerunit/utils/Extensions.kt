package com.awais.mvvmnavdaggerunit.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.mvvmdagger2navigationunit.R

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri: String?, circularProgressDrawable: CircularProgressDrawable) {
    val options = RequestOptions().placeholder(circularProgressDrawable).error(R.mipmap.ic_launcher)
    Glide.with(context).setDefaultRequestOptions(options).load(uri).into(this)
}

@BindingAdapter("android.imageUrl")
fun loadImage(view: ImageView, url: String?) {
    view.loadImage(url, getProgressDrawable(view.context))
}

@BindingAdapter("android.paletteColor")
fun setBackgroundPaletteColor(view: View, url: String?) {
    Glide.with(view.context).asBitmap().load(url).into(object : CustomTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            Palette.from(resource).generate { palette ->
                val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                view.setBackgroundColor(intColor)
            }
        }

        override fun onLoadCleared(placeholder: Drawable?) {
        }
    })
}