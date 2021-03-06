package com.openclassrooms.realestatemanager.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.openclassrooms.realestatemanager.R

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
fun ImageView.loadImage(imageUrl: String, fallbackImage: String?, glide: RequestManager){
    glide.load(imageUrl).apply(RequestOptions.centerCropTransform())
            .error(glide.load(fallbackImage)
                    .apply(RequestOptions.centerCropTransform())
                    .error(glide.load(R.drawable.refresh_icon)
                            .apply(RequestOptions.centerCropTransform())))
            .into(this)
}