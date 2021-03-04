
package com.example.musicx.fragments.genres

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appthemehelper.util.ATHUtil
import com.example.musicx.R
import com.example.musicx.adapter.song.SongAdapter
import com.example.musicx.extensions.dipToPix
import com.example.musicx.extensions.hide
import com.example.musicx.fragments.base.AbsMainActivityFragment
import com.example.musicx.helper.menu.GenreMenuHelper
import com.example.musicx.model.Genre
import com.example.musicx.model.Song
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_playlist_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

class GenreDetailsFragment : AbsMainActivityFragment(R.layout.fragment_playlist_detail) {
    private val arguments by navArgs<GenreDetailsFragmentArgs>()
    private val detailsViewModel: GenreDetailsViewModel by viewModel {
        parametersOf(arguments.extraGenre)
    }
    private lateinit var genre: Genre
    private lateinit var songAdapter: SongAdapter
    private fun setUpTransitions() {
        val transform = MaterialContainerTransform()
        transform.setAllContainerColors(ATHUtil.resolveColor(requireContext(), R.attr.colorSurface))
        sharedElementEnterTransition = transform
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpTransitions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        mainActivity.setBottomBarVisibility(View.GONE)
        mainActivity.addMusicServiceEventListener(detailsViewModel)
        mainActivity.setSupportActionBar(toolbar)
        ViewCompat.setTransitionName(container, "genre")
        genre = arguments.extraGenre
        toolbar?.title = arguments.extraGenre.name
        setupRecyclerView()
        detailsViewModel.getSongs().observe(viewLifecycleOwner, {
            songs(it)
        })

    }

    private fun setupRecyclerView() {
        songAdapter = SongAdapter(requireActivity(), ArrayList(), R.layout.item_list, null)
        recyclerView.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = songAdapter
        }
        songAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkIsEmpty()
            }
        })
    }

    fun songs(songs: List<Song>) {
        progressIndicator.hide()
        if (songs.isNotEmpty()) songAdapter.swapDataSet(songs)
        else songAdapter.swapDataSet(emptyList())
    }

    private fun getEmojiByUnicode(unicode: Int): String {
        return String(Character.toChars(unicode))
    }

    private fun checkIsEmpty() {
        checkForPadding()
        emptyEmoji.text = getEmojiByUnicode(0x1F631)
        empty?.visibility = if (songAdapter.itemCount == 0) View.VISIBLE else View.GONE
    }

    private fun checkForPadding() {
        val height = dipToPix(52f).toInt()
        recyclerView.setPadding(0, 0, 0, height)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_genre_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return GenreMenuHelper.handleMenuClick(requireActivity(), genre, item)
    }
}
