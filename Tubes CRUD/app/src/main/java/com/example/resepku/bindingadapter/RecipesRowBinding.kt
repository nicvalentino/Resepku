package com.example.resepku.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.resepku.R
import com.squareup.picasso.Picasso
import org.jsoup.Jsoup

class RecipesRowBinding {
    companion object {

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView, minutes: Int) {
            textView.text = minutes.toString()
        }

        @BindingAdapter("setDescription")
        @JvmStatic
        fun setDescription(textView: TextView, htmlDescription: String?) {
            textView.text = Jsoup.parse(htmlDescription.toString()).text()
        }

        @BindingAdapter("applyImage")
        @JvmStatic
        fun applyImage(imageView: ImageView, source: String?) {
//            Glide.with(this)
//                .load(source)
//                .into(imageView)
            Picasso.get()
                .load(source)
                .into(imageView)
        }

        @BindingAdapter("applyGreenColor")
        @JvmStatic
        fun applyGreenColor(view: View, state: Boolean) {
            if (state) {
                when (view) {
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }
            }
        }

    }
}