
package code.example.musicx.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.fragment.app.DialogFragment
import code.example.musicx.EXTRA_PLAYLIST
import code.example.musicx.R
import code.example.musicx.db.PlaylistEntity
import code.example.musicx.extensions.colorButtons
import code.example.musicx.extensions.extraNotNull
import code.example.musicx.extensions.materialDialog
import code.example.musicx.fragments.LibraryViewModel
import code.example.musicx.fragments.ReloadType
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DeletePlaylistDialog : DialogFragment() {

    private val libraryViewModel by sharedViewModel<LibraryViewModel>()

    companion object {

        fun create(playlist: PlaylistEntity): DeletePlaylistDialog {
            val list = mutableListOf<PlaylistEntity>()
            list.add(playlist)
            return create(list)
        }

        fun create(playlists: List<PlaylistEntity>): DeletePlaylistDialog {
            return DeletePlaylistDialog().apply {
                arguments = bundleOf(EXTRA_PLAYLIST to playlists)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val playlists = extraNotNull<List<PlaylistEntity>>(EXTRA_PLAYLIST).value
        val title: Int
        val message: CharSequence
        //noinspection ConstantConditions
        if (playlists.size > 1) {
            title = R.string.delete_playlists_title
            message = HtmlCompat.fromHtml(
                String.format(getString(R.string.delete_x_playlists), playlists.size),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        } else {
            title = R.string.delete_playlist_title
            message = HtmlCompat.fromHtml(
                String.format(getString(R.string.delete_playlist_x), playlists[0].playlistName),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }

        return materialDialog(title)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(R.string.action_delete) { _, _ ->
                libraryViewModel.deleteSongsFromPlaylist(playlists)
                libraryViewModel.deleteRoomPlaylist(playlists)
                libraryViewModel.forceReload(ReloadType.Playlists)
            }
            .create()
            .colorButtons()
    }
}
