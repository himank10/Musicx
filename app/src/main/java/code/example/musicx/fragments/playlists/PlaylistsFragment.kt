
package code.example.musicx.fragments.playlists

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.SubMenu
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.MenuCompat
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import code.example.appthemehelper.util.ToolbarContentTintHelper
import code.example.musicx.EXTRA_PLAYLIST
import code.example.musicx.R
import code.example.musicx.adapter.playlist.PlaylistAdapter
import code.example.musicx.db.PlaylistWithSongs
import code.example.musicx.fragments.ReloadType
import code.example.musicx.fragments.base.AbsRecyclerViewCustomGridSizeFragment
import code.example.musicx.helper.SortOrder
import code.example.musicx.interfaces.IPlaylistClickListener
import code.example.musicx.util.PreferenceUtil

import kotlinx.android.synthetic.main.fragment_library.*

class PlaylistsFragment :
    AbsRecyclerViewCustomGridSizeFragment<PlaylistAdapter, GridLayoutManager>(),
    IPlaylistClickListener {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        libraryViewModel.getPlaylists().observe(viewLifecycleOwner, {
            if (it.isNotEmpty())
                adapter?.swapDataSet(it)
            else
                adapter?.swapDataSet(listOf())
        })
    }

    override val emptyMessage: Int
        get() = R.string.no_playlists

    override fun createLayoutManager(): GridLayoutManager {
        return GridLayoutManager(requireContext(), getGridSize())
    }

    override fun createAdapter(): PlaylistAdapter {
        return PlaylistAdapter(
            requireActivity(),
            ArrayList(),
            itemLayoutRes(),
            null,
            this
        )
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        ToolbarContentTintHelper.handleOnPrepareOptionsMenu(requireActivity(), toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.removeItem(R.id.action_grid_size)
        menu.removeItem(R.id.action_layout_type)
        menu.add(0, R.id.action_add_to_playlist, 0, R.string.new_playlist_title)
        menu.add(0, R.id.action_import_playlist, 0, R.string.import_playlist)
        menu.findItem(R.id.action_settings).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        setUpSortOrderMenu(menu.findItem(R.id.action_sort_order).subMenu)
        MenuCompat.setGroupDividerEnabled(menu, true);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (handleSortOrderMenuItem(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setUpSortOrderMenu(subMenu: SubMenu) {
        val order: String? = getSortOrder()
        subMenu.clear()
        createId(
            subMenu,
            R.id.action_song_sort_order_asc,
            R.string.sort_order_a_z,
            order == SortOrder.PlaylistSortOrder.PLAYLIST_A_Z
        )
        createId(
            subMenu,
            R.id.action_song_sort_order_desc,
            R.string.sort_order_z_a,
            order == SortOrder.PlaylistSortOrder.PLAYLIST_Z_A
        )
        createId(
            subMenu,
            R.id.action_playlist_sort_order,
            R.string.sort_order_num_songs,
            order == SortOrder.PlaylistSortOrder.PLAYLIST_SONG_COUNT
        )
        createId(
            subMenu,
            R.id.action_playlist_sort_order_desc,
            R.string.sort_order_num_songs_desc,
            order == SortOrder.PlaylistSortOrder.PLAYLIST_SONG_COUNT_DESC
        )
        subMenu.setGroupCheckable(0, true, true)
    }

    private fun handleSortOrderMenuItem(item: MenuItem): Boolean {
        val sortOrder: String = when (item.itemId) {
            R.id.action_song_sort_order_asc -> SortOrder.PlaylistSortOrder.PLAYLIST_A_Z
            R.id.action_song_sort_order_desc -> SortOrder.PlaylistSortOrder.PLAYLIST_Z_A
            R.id.action_playlist_sort_order -> SortOrder.PlaylistSortOrder.PLAYLIST_SONG_COUNT
            R.id.action_playlist_sort_order_desc -> SortOrder.PlaylistSortOrder.PLAYLIST_SONG_COUNT_DESC
            else -> PreferenceUtil.playlistSortOrder
        }
        if (sortOrder != PreferenceUtil.playlistSortOrder) {
            item.isChecked = true
            setAndSaveSortOrder(sortOrder)
            return true
        }
        return false
    }

    private fun createId(menu: SubMenu, id: Int, title: Int, checked: Boolean) {
        menu.add(0, id, 0, title).isChecked = checked
    }


    override fun setGridSize(gridSize: Int) {
        TODO("Not yet implemented")
    }

    override fun setSortOrder(sortOrder: String) {
        libraryViewModel.forceReload(ReloadType.Playlists)
    }

    override fun loadSortOrder(): String {
        return PreferenceUtil.playlistSortOrder
    }

    override fun saveSortOrder(sortOrder: String) {
        PreferenceUtil.playlistSortOrder = sortOrder
    }

    override fun loadGridSize(): Int {
        return 1
    }

    override fun saveGridSize(gridColumns: Int) {
        //Add grid save
    }

    override fun loadGridSizeLand(): Int {
        return 2
    }

    override fun saveGridSizeLand(gridColumns: Int) {
        //Add land grid save
    }

    override fun loadLayoutRes(): Int {
        return R.layout.item_list
    }

    override fun saveLayoutRes(layoutRes: Int) {
        //Save layout
    }

    override fun onPlaylistClick(playlistWithSongs: PlaylistWithSongs, view: View) {
        findNavController().navigate(
            R.id.playlistDetailsFragment,
            bundleOf(EXTRA_PLAYLIST to playlistWithSongs),
            null,
            FragmentNavigatorExtras(view to "playlist")
        )
    }
}
