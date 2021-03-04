
package com.example.musicx.fragments.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import com.example.appthemehelper.util.ATHUtil
import com.example.appthemehelper.util.ColorUtil
import com.example.appthemehelper.util.VersionUtils
import com.example.musicx.R
import com.example.musicx.activities.MainActivity
import com.example.musicx.fragments.LibraryViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class AbsMainActivityFragment(@LayoutRes layout: Int) : AbsMusicServiceFragment(layout) {
    val libraryViewModel: LibraryViewModel by sharedViewModel()

    val mainActivity: MainActivity
        get() = activity as MainActivity

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        mainActivity.setNavigationbarColorAuto()
        mainActivity.setLightNavigationBar(true)
        mainActivity.setTaskDescriptionColorAuto()
        mainActivity.hideStatusBar()
    }

    private fun setStatusBarColor(view: View, color: Int) {
        val statusBar = view.findViewById<View>(R.id.status_bar)
        if (statusBar != null) {
            if (VersionUtils.hasMarshmallow()) {
                statusBar.setBackgroundColor(color)
                mainActivity.setLightStatusbarAuto(color)
            } else {
                statusBar.setBackgroundColor(color)
            }
        }
    }

    fun setStatusBarColorAuto(view: View) {
        val colorPrimary = ATHUtil.resolveColor(requireContext(), R.attr.colorSurface)
        // we don't want to use statusbar color because we are doing the color darkening on our own to support KitKat
        if (VersionUtils.hasMarshmallow()) {
            setStatusBarColor(view, colorPrimary)
        } else {
            setStatusBarColor(view, ColorUtil.darkenColor(colorPrimary))
        }
    }
}
