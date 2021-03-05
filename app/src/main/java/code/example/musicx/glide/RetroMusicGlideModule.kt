
package code.example.musicx.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.module.GlideModule
import code.example.musicx.glide.artistimage.ArtistImage
import code.example.musicx.glide.artistimage.Factory
import code.example.musicx.glide.audiocover.AudioFileCover
import code.example.musicx.glide.audiocover.AudioFileCoverLoader
import java.io.InputStream

class RetroMusicGlideModule : GlideModule {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
    }

    override fun registerComponents(context: Context, glide: Glide) {
        glide.register(
            AudioFileCover::class.java,
            InputStream::class.java,
            AudioFileCoverLoader.Factory()
        )
        glide.register(ArtistImage::class.java, InputStream::class.java, Factory(context))
    }
}
