package code.example.appthemehelper.util

import android.R
import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import code.example.appthemehelper.ThemeStore
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

object NavigationViewUtil {

    fun setItemIconColors(navigationView: NavigationView, @ColorInt normalColor: Int, @ColorInt selectedColor: Int) {
        val iconSl = ColorStateList(
            arrayOf(
                intArrayOf(-R.attr.state_checked),
                intArrayOf(R.attr.state_checked)
            ), intArrayOf(normalColor, selectedColor)
        )
        navigationView.itemIconTintList = iconSl
        val drawable = navigationView.itemBackground
        navigationView.itemBackground = TintHelper.createTintedDrawable(
            drawable,
            ColorUtil.withAlpha(ThemeStore.accentColor(navigationView.context), 0.2f)
        )
    }

    fun setItemTextColors(navigationView: NavigationView, @ColorInt normalColor: Int, @ColorInt selectedColor: Int) {
        val textSl = ColorStateList(
            arrayOf(intArrayOf(-R.attr.state_checked), intArrayOf(R.attr.state_checked)),
            intArrayOf(normalColor, selectedColor)
        )
        navigationView.itemTextColor = textSl
    }

    fun setItemIconColors(bottomNavigationView: BottomNavigationView, @ColorInt normalColor: Int, @ColorInt selectedColor: Int) {
        val iconSl = ColorStateList(
            arrayOf(intArrayOf(-R.attr.state_checked), intArrayOf(R.attr.state_checked)),
            intArrayOf(normalColor, selectedColor)
        )
        bottomNavigationView.itemIconTintList = iconSl
    }

    fun setItemTextColors(bottomNavigationView: BottomNavigationView, @ColorInt normalColor: Int, @ColorInt selectedColor: Int) {
        val textSl = ColorStateList(
            arrayOf(intArrayOf(-R.attr.state_checked), intArrayOf(R.attr.state_checked)),
            intArrayOf(normalColor, selectedColor)
        )
        bottomNavigationView.itemTextColor = textSl
    }
}