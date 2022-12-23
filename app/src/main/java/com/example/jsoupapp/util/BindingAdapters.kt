package com.example.jsoupapp.util

import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import com.example.jsoupapp.models.News
import com.google.android.material.imageview.ShapeableImageView

class BindingAdapters {

    companion object {

        @BindingAdapter("app:load")
        @JvmStatic
        fun loadImage(view: ShapeableImageView, url: UiState<String>?) {
            view.load(url?.data) {
                crossfade(true)
                crossfade(200)
            }
        }

        @BindingAdapter("app:loadIV")
        @JvmStatic
        fun loadIv(view: ImageView, url: String) {
            view.load(url) {
                crossfade(true)
                crossfade(200)
            }
        }



        @BindingAdapter("app:isVisiblee")
        @JvmStatic
        fun isVisiblee(progressBar: ProgressBar, state : UiState<List<News>>?) {
            progressBar.isVisible = state is UiState.Loading
        }

    }
}