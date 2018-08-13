package com.luizmatias.mvvmsample

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        Picasso.get().load(imageUrl).placeholder(android.R.drawable.ic_dialog_info).error(android.R.drawable.ic_delete).into(view)
    }
}