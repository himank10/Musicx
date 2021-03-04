
package com.example.musicx.fragments.player.adaptive

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.appthemehelper.util.ATHUtil
import com.example.appthemehelper.util.ToolbarContentTintHelper
import com.example.musicx.R
import com.example.musicx.extensions.surfaceColor
import com.example.musicx.extensions.textColorPrimary
import com.example.musicx.extensions.textColorSecondary
import com.example.musicx.fragments.base.AbsPlayerFragment
import com.example.musicx.fragments.player.PlayerAlbumCoverFragment
import com.example.musicx.helper.MusicPlayerRemote
import com.example.musicx.model.Song
import com.example.musicx.util.color.MediaNotificationProcessor
import kotlinx.android.synthetic.main.fragment_adaptive_player.*

class AdaptiveFragment : AbsPlayerFragment(R.layout.fragment_adaptive_player) {

    override fun playerToolbar(): Toolbar {
        return playerToolbar
    }

    private var lastColor: Int = 0
    private lateinit var playbackControlsFragment: AdaptivePlaybackControlsFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSubFragments()
        setUpPlayerToolbar()
    }

    private fun setUpSubFragments() {
        playbackControlsFragment =
            childFragmentManager.findFragmentById(R.id.playbackControlsFragment) as AdaptivePlaybackControlsFragment
        val playerAlbumCoverFragment =
            childFragmentManager.findFragmentById(R.id.playerAlbumCoverFragment) as PlayerAlbumCoverFragment
        playerAlbumCoverFragment.apply {
            removeSlideEffect()
            setCallbacks(this@AdaptiveFragment)
        }
    }

    private fun setUpPlayerToolbar() {
        playerToolbar.apply {
            inflateMenu(R.menu.menu_player)
            setNavigationOnClickListener { requireActivity().onBackPressed() }
            ToolbarContentTintHelper.colorizeToolbar(this, surfaceColor(), requireActivity())
            setTitleTextColor(textColorPrimary())
            setSubtitleTextColor(textColorSecondary())
            setOnMenuItemClickListener(this@AdaptiveFragment)
        }
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        updateIsFavorite()
        updateSong()
    }

    override fun onPlayingMetaChanged() {
        updateIsFavorite()
        updateSong()
    }

    private fun updateSong() {
        val song = MusicPlayerRemote.currentSong
        playerToolbar.apply {
            title = song.title
            subtitle = song.artistName
        }
    }

    override fun toggleFavorite(song: Song) {
        super.toggleFavorite(song)
        if (song.id == MusicPlayerRemote.currentSong.id) {
            updateIsFavorite()
        }
    }

    override fun onFavoriteToggled() {
        toggleFavorite(MusicPlayerRemote.currentSong)
    }

    override fun onColorChanged(color: MediaNotificationProcessor) {
        playbackControlsFragment.setColor(color)
        lastColor = color.primaryTextColor
        libraryViewModel.updateColor(color.primaryTextColor)
        ToolbarContentTintHelper.colorizeToolbar(
            playerToolbar,
            ATHUtil.resolveColor(requireContext(), R.attr.colorControlNormal),
            requireActivity()
        )
    }

    override fun onShow() {
    }

    override fun onHide() {
        onBackPressed()
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    override fun toolbarIconColor(): Int {
        return ATHUtil.resolveColor(requireContext(), R.attr.colorControlNormal)
    }

    override val paletteColor: Int
        get() = lastColor
}
