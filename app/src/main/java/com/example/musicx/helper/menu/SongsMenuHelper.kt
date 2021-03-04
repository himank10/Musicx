
package com.example.musicx.helper.menu

import androidx.fragment.app.FragmentActivity
import com.example.musicx.R
import com.example.musicx.dialogs.AddToPlaylistDialog
import com.example.musicx.dialogs.DeleteSongsDialog
import com.example.musicx.helper.MusicPlayerRemote
import com.example.musicx.model.Song
import com.example.musicx.repository.RealRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.get

object SongsMenuHelper : KoinComponent {
    fun handleMenuClick(
        activity: FragmentActivity,
        songs: List<Song>,
        menuItemId: Int
    ): Boolean {
        when (menuItemId) {
            R.id.action_play_next -> {
                MusicPlayerRemote.playNext(songs)
                return true
            }
            R.id.action_add_to_current_playing -> {
                MusicPlayerRemote.enqueue(songs)
                return true
            }
            R.id.action_add_to_playlist -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val playlists = get<RealRepository>().fetchPlaylists()
                    withContext(Dispatchers.Main) {
                        AddToPlaylistDialog.create(playlists, songs)
                            .show(activity.supportFragmentManager, "ADD_PLAYLIST")
                    }
                }
                return true
            }
            R.id.action_delete_from_device -> {
                DeleteSongsDialog.create(songs)
                    .show(activity.supportFragmentManager, "DELETE_SONGS")
                return true
            }
        }
        return false
    }
}
