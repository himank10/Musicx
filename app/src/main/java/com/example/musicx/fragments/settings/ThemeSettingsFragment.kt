
package com.example.musicx.fragments.settings

import android.os.Build
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.TwoStatePreference
import com.afollestad.materialdialogs.color.ColorChooserDialog
import com.example.appthemehelper.ThemeStore
import com.example.appthemehelper.common.prefs.supportv7.ATEColorPreference
import com.example.appthemehelper.common.prefs.supportv7.ATESwitchPreference
import com.example.appthemehelper.util.ColorUtil
import com.example.appthemehelper.util.VersionUtils
import com.example.musicx.*
import com.example.musicx.appshortcuts.DynamicShortcutManager
import com.example.musicx.util.PreferenceUtil

class ThemeSettingsFragment : AbsSettingsFragment() {
    override fun invalidateSettings() {
        val generalTheme: Preference? = findPreference(GENERAL_THEME)
        generalTheme?.let {
            setSummary(it)
            it.setOnPreferenceChangeListener { _, newValue ->
                val theme = newValue as String
                setSummary(it, newValue)
                ThemeStore.markChanged(requireContext())

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                    requireActivity().setTheme(PreferenceUtil.themeResFromPrefValue(theme))
                    DynamicShortcutManager(requireContext()).updateDynamicShortcuts()
                }
                requireActivity().recreate()
                true
            }
        }

        val accentColorPref: ATEColorPreference? = findPreference(ACCENT_COLOR)
        val accentColor = ThemeStore.accentColor(requireContext())
        accentColorPref?.setColor(accentColor, ColorUtil.darkenColor(accentColor))
        accentColorPref?.setOnPreferenceClickListener {
            ColorChooserDialog.Builder(requireContext(), R.string.accent_color)
                .accentMode(true)
                .allowUserColorInput(true)
                .allowUserColorInputAlpha(false)
                .preselect(accentColor)
                .show(requireActivity())
            return@setOnPreferenceClickListener true
        }
        val blackTheme: ATESwitchPreference? = findPreference(BLACK_THEME)
        blackTheme?.setOnPreferenceChangeListener { _, _ ->
            if (!App.isProVersion()) {
                showProToastAndNavigate("Just Black theme")
                return@setOnPreferenceChangeListener false
            }
            ThemeStore.markChanged(requireContext())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                requireActivity().setTheme(PreferenceUtil.themeResFromPrefValue("black"))
                DynamicShortcutManager(requireContext()).updateDynamicShortcuts()
            }
            requireActivity().recreate()
            true
        }

        val desaturatedColor: ATESwitchPreference? = findPreference(DESATURATED_COLOR)
        desaturatedColor?.setOnPreferenceChangeListener { _, value ->
            val desaturated = value as Boolean
            ThemeStore.prefs(requireContext())
                .edit()
                .putBoolean("desaturated_color", desaturated)
                .apply()
            PreferenceUtil.isDesaturatedColor = desaturated
            requireActivity().recreate()
            true
        }

        val colorAppShortcuts: TwoStatePreference? = findPreference(SHOULD_COLOR_APP_SHORTCUTS)
        if (!VersionUtils.hasNougatMR()) {
            colorAppShortcuts?.isVisible = false
        } else {
            colorAppShortcuts?.isChecked = PreferenceUtil.isColoredAppShortcuts
            colorAppShortcuts?.setOnPreferenceChangeListener { _, newValue ->
                PreferenceUtil.isColoredAppShortcuts = newValue as Boolean
                DynamicShortcutManager(requireContext()).updateDynamicShortcuts()
                true
            }
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_general)
    }
}
