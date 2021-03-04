package com.example.musicx.model.smartplaylist


import com.example.musicx.App
import com.example.musicx.R
import com.example.musicx.model.Song
import kotlinx.android.parcel.Parcelize
import org.koin.core.KoinComponent

@Parcelize
class HistoryPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.history),
    iconRes = R.drawable.ic_history
), KoinComponent {

    override fun songs(): List<Song> {
        return topPlayedRepository.recentlyPlayedTracks()
    }
}