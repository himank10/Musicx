
package com.example.musicx.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.example.musicx.*
import com.example.musicx.adapter.album.AlbumAdapter
import com.example.musicx.adapter.artist.ArtistAdapter
import com.example.musicx.adapter.song.SongAdapter
import com.example.musicx.db.toSong
import com.example.musicx.extensions.dipToPix
import com.example.musicx.extensions.hide
import com.example.musicx.fragments.base.AbsMainActivityFragment
import com.example.musicx.interfaces.IAlbumClickListener
import com.example.musicx.interfaces.IArtistClickListener
import com.example.musicx.model.Album
import com.example.musicx.model.Artist
import com.example.musicx.util.RetroUtil

import kotlinx.android.synthetic.main.fragment_playlist_detail.*

class DetailListFragment : AbsMainActivityFragment(R.layout.fragment_playlist_detail),
    IArtistClickListener, IAlbumClickListener {
    private val args by navArgs<DetailListFragmentArgs>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity.setBottomBarVisibility(View.GONE)
        mainActivity.setSupportActionBar(toolbar)
        progressIndicator.hide()
        when (args.type) {
            TOP_ARTISTS -> {
                loadArtists(R.string.top_artists, TOP_ARTISTS)
            }
            RECENT_ARTISTS -> {
                loadArtists(R.string.recent_artists, RECENT_ARTISTS)
            }
            TOP_ALBUMS -> {
                loadAlbums(R.string.top_albums, TOP_ALBUMS)
            }
            RECENT_ALBUMS -> {
                loadAlbums(R.string.recent_albums, RECENT_ALBUMS)
            }
            FAVOURITES -> loadFavorite()
            HISTORY_PLAYLIST -> loadHistory()
            LAST_ADDED_PLAYLIST -> lastAddedSongs()
            TOP_PLAYED_PLAYLIST -> topPlayed()
        }

        recyclerView.adapter?.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                val height = dipToPix(52f)
                recyclerView.setPadding(0, 0, 0, height.toInt())
            }
        })
    }

    private fun lastAddedSongs() {
        toolbar.setTitle(R.string.last_added)
        val songAdapter = SongAdapter (
            requireActivity(),
            mutableListOf(),
            R.layout.item_list, null
        )
        recyclerView.apply {
            adapter = songAdapter
            layoutManager = linearLayoutManager()
        }
        libraryViewModel.recentSongs().observe(viewLifecycleOwner, { songs ->
            songAdapter.swapDataSet(songs)
        })
    }

    private fun topPlayed() {
        toolbar.setTitle(R.string.my_top_tracks)
        val songAdapter = SongAdapter(
            requireActivity(),
            mutableListOf(),
            R.layout.item_list, null
        )
        recyclerView.apply {
            adapter = songAdapter
            layoutManager = linearLayoutManager()
        }
        libraryViewModel.playCountSongs().observe(viewLifecycleOwner, { songs ->
            songAdapter.swapDataSet(songs)
        })
    }

    private fun loadHistory() {
        toolbar.setTitle(R.string.history)

        val songAdapter = SongAdapter(
            requireActivity(),
            mutableListOf(),
            R.layout.item_list, null
        )
        recyclerView.apply {
            adapter = songAdapter
            layoutManager = linearLayoutManager()
        }
        libraryViewModel.observableHistorySongs().observe(viewLifecycleOwner, {
            songAdapter.swapDataSet(it)
        })
    }

    private fun loadFavorite() {
        toolbar.setTitle(R.string.favorites)
        val songAdapter = SongAdapter(
            requireActivity(),
            mutableListOf(),
            R.layout.item_list, null
        )
        recyclerView.apply {
            adapter = songAdapter
            layoutManager = linearLayoutManager()
        }
        libraryViewModel.favorites().observe(viewLifecycleOwner, { songEntities ->
            val songs = songEntities.map { songEntity -> songEntity.toSong() }
            songAdapter.swapDataSet(songs)
        })
    }

    private fun loadArtists(title: Int, type: Int) {
        toolbar.setTitle(title)
        libraryViewModel.artists(type).observe(viewLifecycleOwner, { artists ->
            recyclerView.apply {
                adapter = artistAdapter(artists)
                layoutManager = gridLayoutManager()
            }
        })
    }

    private fun loadAlbums(title: Int, type: Int) {
        toolbar.setTitle(title)
        libraryViewModel.albums(type).observe(viewLifecycleOwner, { albums ->
            recyclerView.apply {
                adapter = albumAdapter(albums)
                layoutManager = gridLayoutManager()
            }
        })
    }

    private fun artistAdapter(artists: List<Artist>): ArtistAdapter = ArtistAdapter(
        requireActivity(),
        artists,
        R.layout.item_grid_circle,
        null, this@DetailListFragment
    )

    private fun albumAdapter(albums: List<Album>): AlbumAdapter = AlbumAdapter(
        requireActivity(),
        albums,
        R.layout.item_grid,
        null, this@DetailListFragment
    )

    private fun linearLayoutManager(): LinearLayoutManager =
        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    private fun gridLayoutManager(): GridLayoutManager =
        GridLayoutManager(requireContext(), gridCount(), GridLayoutManager.VERTICAL, false)

    private fun gridCount(): Int {
        if (RetroUtil.isTablet()) {
            return if (RetroUtil.isLandscape()) 6 else 4
        }
        return 2
    }

    override fun onArtist(artistId: Long, view: View) {
        findNavController().navigate(
            R.id.artistDetailsFragment,
            bundleOf(EXTRA_ARTIST_ID to artistId),
            null,
            FragmentNavigatorExtras(view to "artist")
        )
    }

    override fun onAlbumClick(albumId: Long, view: View) {
        findNavController().navigate(
            R.id.albumDetailsFragment,
            bundleOf(EXTRA_ALBUM_ID to albumId),
            null,
            FragmentNavigatorExtras(
                view to "album"
            )
        )
    }
}
