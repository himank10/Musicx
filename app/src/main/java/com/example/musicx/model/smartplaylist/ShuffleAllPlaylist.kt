package com.example.musicx.model.smartplaylist


import com.example.musicx.App
import com.example.musicx.R
import com.example.musicx.model.Song
import kotlinx.android.parcel.Parcelize

@Parcelize
class ShuffleAllPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.action_shuffle_all),
    iconRes = R.drawable.ic_shuffle
) {
    override fun songs(): List<Song> {
        return songRepository.songs()
    }
}