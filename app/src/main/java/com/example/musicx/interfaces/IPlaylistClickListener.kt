package com.example.musicx.interfaces

import android.view.View
import com.example.musicx.db.PlaylistWithSongs


interface IPlaylistClickListener {
    fun onPlaylistClick(playlistWithSongs: PlaylistWithSongs, view: View)
}