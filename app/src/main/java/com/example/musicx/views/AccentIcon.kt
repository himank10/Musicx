package com.example.musicx.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.example.musicx.extensions.accentColor

class AccentIcon @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : AppCompatImageView(context, attrs, defStyleAttr) {
    init {
        imageTintList = ColorStateList.valueOf(accentColor())
    }
}