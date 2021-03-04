package com.example.musicx.model.smartplaylist

import androidx.annotation.DrawableRes
import com.example.musicx.R
import com.example.musicx.model.AbsCustomPlaylist


abstract class AbsSmartPlaylist(
    name: String,
    @DrawableRes val iconRes: Int = R.drawable.ic_queue_music
) : AbsCustomPlaylist(
    id = PlaylistIdGenerator(name, iconRes),
    name = name
)