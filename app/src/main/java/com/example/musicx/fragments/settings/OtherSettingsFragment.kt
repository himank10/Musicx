
package com.example.musicx.fragments.settings

import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import com.example.appthemehelper.common.prefs.supportv7.ATEListPreference
import com.example.musicx.LANGUAGE_NAME
import com.example.musicx.LAST_ADDED_CUTOFF
import com.example.musicx.R
import com.example.musicx.fragments.LibraryViewModel
import com.example.musicx.fragments.ReloadType

import org.koin.androidx.viewmodel.ext.android.sharedViewModel



class OtherSettingsFragment : AbsSettingsFragment() {
    private val libraryViewModel by sharedViewModel<LibraryViewModel>()

    override fun invalidateSettings() {
        val languagePreference: ATEListPreference? = findPreference(LANGUAGE_NAME)
        languagePreference?.setOnPreferenceChangeListener { _, _ ->
            requireActivity().recreate()
            return@setOnPreferenceChangeListener true
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_advanced)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preference: Preference? = findPreference(LAST_ADDED_CUTOFF)
        preference?.setOnPreferenceChangeListener { lastAdded, newValue ->
            setSummary(lastAdded, newValue)
            libraryViewModel.forceReload(ReloadType.HomeSections)
            true
        }
        val languagePreference: Preference? = findPreference(LANGUAGE_NAME)
        languagePreference?.setOnPreferenceChangeListener { prefs, newValue ->
            setSummary(prefs, newValue)
            true
        }
    }
}
