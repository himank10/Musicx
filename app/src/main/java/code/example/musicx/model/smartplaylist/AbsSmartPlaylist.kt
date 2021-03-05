package code.example.musicx.model.smartplaylist

import androidx.annotation.DrawableRes
import code.example.musicx.R
import code.example.musicx.model.AbsCustomPlaylist


abstract class AbsSmartPlaylist(
    name: String,
    @DrawableRes val iconRes: Int = R.drawable.ic_queue_music
) : AbsCustomPlaylist(
    id = PlaylistIdGenerator(name, iconRes),
    name = name
)