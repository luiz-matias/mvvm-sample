package com.luizmatias.mvvmsample

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso


class DataBindingUtils {

    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, imageUrl: String) {
        Picasso.get().load(imageUrl).placeholder(android.R.drawable.ic_dialog_info).error(android.R.drawable.ic_delete).into(view)
    }

}