
package com.example.musicx.fragments.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicx.db.PlaylistWithSongs
import com.example.musicx.db.SongEntity
import com.example.musicx.interfaces.IMusicServiceEventListener
import com.example.musicx.model.Song
import com.example.musicx.repository.RealRepository

class PlaylistDetailsViewModel(
    private val realRepository: RealRepository,
    private var playlist: PlaylistWithSongs
) : ViewModel(), IMusicServiceEventListener {

    private val playListSongs = MutableLiveData<List<Song>>()

    fun getSongs(): LiveData<List<SongEntity>> =
        realRepository.playlistSongs(playlist.playlistEntity.playListId)

    override fun onMediaStoreChanged() {}
    override fun onServiceConnected() {}
    override fun onServiceDisconnected() {}
    override fun onQueueChanged() {}
    override fun onPlayingMetaChanged() {}
    override fun onPlayStateChanged() {}
    override fun onRepeatModeChanged() {}
    override fun onShuffleModeChanged() {}
}
