
package code.example.musicx.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import code.example.musicx.R
import code.example.musicx.extensions.colorButtons
import code.example.musicx.extensions.materialDialog
import code.example.musicx.fragments.LibraryViewModel

import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ImportPlaylistDialog : DialogFragment() {
    private val libraryViewModel by sharedViewModel<LibraryViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return materialDialog(R.string.import_playlist)
            .setMessage(R.string.import_playlist_message)
            .setPositiveButton(R.string.import_label) { _, _ ->
                libraryViewModel.importPlaylists()
            }
            .create()
            .colorButtons()
    }
}
