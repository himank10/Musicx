
package com.example.musicx.fragments.home

import android.app.ActivityOptions
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MenuItem.SHOW_AS_ACTION_IF_ROOM
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.appthemehelper.common.ATHToolbarActivity
import com.example.appthemehelper.util.ToolbarContentTintHelper
import com.example.musicx.*
import com.example.musicx.adapter.HomeAdapter
import com.example.musicx.dialogs.CreatePlaylistDialog
import com.example.musicx.dialogs.ImportPlaylistDialog
import com.example.musicx.fragments.base.AbsMainActivityFragment
import com.example.musicx.glide.ProfileBannerGlideRequest
import com.example.musicx.glide.UserProfileGlideRequest
import com.example.musicx.util.NavigationUtil
import com.example.musicx.util.PreferenceUtil
import kotlinx.android.synthetic.main.abs_playlists.*
import kotlinx.android.synthetic.main.fragment_banner_home.*
import kotlinx.android.synthetic.main.home_content.*

class HomeFragment :
    AbsMainActivityFragment(if (PreferenceUtil.isHomeBanner) R.layout.fragment_banner_home else R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.setBottomBarVisibility(View.VISIBLE)
        mainActivity.setSupportActionBar(toolbar)
        mainActivity.supportActionBar?.title = null
        setStatusBarColorAuto(view)
        bannerImage?.setOnClickListener {
            val options = ActivityOptions.makeSceneTransitionAnimation(
                mainActivity,
                userImage,
                getString(R.string.transition_user_image)
            )
            NavigationUtil.goToUserInfo(requireActivity(), options)
        }

        lastAdded.setOnClickListener {
            findNavController().navigate(
                R.id.detailListFragment,
                bundleOf(EXTRA_PLAYLIST_TYPE to LAST_ADDED_PLAYLIST)
            )
        }

        topPlayed.setOnClickListener {
            findNavController().navigate(
                R.id.detailListFragment,
                bundleOf(EXTRA_PLAYLIST_TYPE to TOP_PLAYED_PLAYLIST)
            )
        }

        actionShuffle.setOnClickListener {
            libraryViewModel.shuffleSongs()
        }

        history.setOnClickListener {
            findNavController().navigate(
                R.id.detailListFragment,
                bundleOf(EXTRA_PLAYLIST_TYPE to HISTORY_PLAYLIST)
            )
        }

        userImage.setOnClickListener {
            val options = ActivityOptions.makeSceneTransitionAnimation(
                mainActivity,
                userImage,
                getString(R.string.transition_user_image)
            )
            NavigationUtil.goToUserInfo(requireActivity(), options)
        }
        titleWelcome?.text = String.format("%s", PreferenceUtil.userName)

        val homeAdapter = HomeAdapter(mainActivity)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(mainActivity)
            adapter = homeAdapter
        }

        libraryViewModel.getHome().observe(viewLifecycleOwner, Observer {
            homeAdapter.swapData(it)
        })

        loadProfile()
        setupTitle()
    }

    private fun setupTitle() {
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(
                R.id.searchFragment,
                null,
                navOptions
            )
        }
        appNameText.text = getString(R.string.app_name)
    }

    private fun loadProfile() {
        bannerImage?.let {
            ProfileBannerGlideRequest.Builder.from(
                Glide.with(requireContext()),
                ProfileBannerGlideRequest.getBannerModel()
            ).build().into(it)
        }
        UserProfileGlideRequest.Builder.from(
            Glide.with(requireActivity()),
            UserProfileGlideRequest.getUserModel()
        ).build().into(userImage)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
        menu.removeItem(R.id.action_grid_size)
        menu.removeItem(R.id.action_layout_type)
        menu.removeItem(R.id.action_sort_order)
        menu.findItem(R.id.action_settings).setShowAsAction(SHOW_AS_ACTION_IF_ROOM)
        ToolbarContentTintHelper.handleOnCreateOptionsMenu(
            requireContext(),
            toolbar,
            menu,
            ATHToolbarActivity.getToolbarBackgroundColor(toolbar)
        )
    }

    companion object {

        const val TAG: String = "BannerHomeFragment"

        @JvmStatic
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> findNavController().navigate(
                R.id.settingsActivity,
                null,
                navOptions
            )
            R.id.action_import_playlist -> ImportPlaylistDialog().show(
                childFragmentManager,
                "ImportPlaylist"
            )
            R.id.action_add_to_playlist -> CreatePlaylistDialog.create(emptyList()).show(
                childFragmentManager,
                "ShowCreatePlaylistDialog"
            )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        ToolbarContentTintHelper.handleOnPrepareOptionsMenu(requireActivity(), toolbar)
    }
}
