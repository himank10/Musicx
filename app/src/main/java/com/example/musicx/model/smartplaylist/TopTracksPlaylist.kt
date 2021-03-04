package com.example.musicx.model.smartplaylist


import com.example.musicx.App
import com.example.musicx.R
import com.example.musicx.model.Song
import kotlinx.android.parcel.Parcelize

@Parcelize
class TopTracksPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.my_top_tracks),
    iconRes = R.drawable.ic_trending_up
) {
    override fun songs(): List<Song> {
        return topPlayedRepository.topTracks()
    }
}