package code.example.musicx.interfaces

import android.view.View
import code.example.musicx.db.PlaylistWithSongs


interface IPlaylistClickListener {
    fun onPlaylistClick(playlistWithSongs: PlaylistWithSongs, view: View)
}