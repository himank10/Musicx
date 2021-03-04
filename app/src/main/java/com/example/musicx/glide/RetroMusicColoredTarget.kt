
package com.example.musicx.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.animation.GlideAnimation
import com.example.appthemehelper.util.ATHUtil
import com.example.musicx.App
import com.example.musicx.R
import com.example.musicx.glide.palette.BitmapPaletteTarget
import com.example.musicx.glide.palette.BitmapPaletteWrapper
import com.example.musicx.util.color.MediaNotificationProcessor

abstract class RetroMusicColoredTarget(view: ImageView) : BitmapPaletteTarget(view) {

    protected val defaultFooterColor: Int
        get() = ATHUtil.resolveColor(getView().context, R.attr.colorControlNormal)

    abstract fun onColorReady(colors: MediaNotificationProcessor)

    override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
        super.onLoadFailed(e, errorDrawable)
        val colors = MediaNotificationProcessor(App.getContext(), errorDrawable)
        onColorReady(colors)
    }

    override fun onResourceReady(
        resource: BitmapPaletteWrapper?,
        glideAnimation: GlideAnimation<in BitmapPaletteWrapper>?
    ) {
        super.onResourceReady(resource, glideAnimation)
        resource?.let { bitmapWrap ->
            MediaNotificationProcessor(App.getContext()).getPaletteAsync({
                onColorReady(it)
            }, bitmapWrap.bitmap)
        }
    }
}
