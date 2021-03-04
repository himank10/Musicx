
package com.example.musicx.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.animation.GlideAnimation
import com.example.appthemehelper.util.ATHUtil
import com.example.musicx.R
import com.example.musicx.glide.palette.BitmapPaletteTarget
import com.example.musicx.glide.palette.BitmapPaletteWrapper
import com.example.musicx.util.ColorUtil

abstract class SingleColorTarget(view: ImageView) : BitmapPaletteTarget(view) {

    private val defaultFooterColor: Int
        get() = ATHUtil.resolveColor(view.context, R.attr.colorControlNormal)

    abstract fun onColorReady(color: Int)

    override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
        super.onLoadFailed(e, errorDrawable)
        onColorReady(defaultFooterColor)
    }

    override fun onResourceReady(
        resource: BitmapPaletteWrapper?,
        glideAnimation: GlideAnimation<in BitmapPaletteWrapper>?
    ) {
        super.onResourceReady(resource, glideAnimation)
        resource?.let {
            onColorReady(
                ColorUtil.getColor(
                    it.palette,
                    ATHUtil.resolveColor(view.context, R.attr.colorPrimary)
                )
            )
        }
    }
}
