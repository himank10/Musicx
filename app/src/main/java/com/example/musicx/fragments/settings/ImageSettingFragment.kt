
package com.example.musicx.fragments.settings

import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import com.example.musicx.AUTO_DOWNLOAD_IMAGES_POLICY
import com.example.musicx.R


class ImageSettingFragment : AbsSettingsFragment() {
    override fun invalidateSettings() {
        val autoDownloadImagesPolicy: Preference = findPreference(AUTO_DOWNLOAD_IMAGES_POLICY)!!
        setSummary(autoDownloadImagesPolicy)
        autoDownloadImagesPolicy.setOnPreferenceChangeListener { _, o ->
            setSummary(autoDownloadImagesPolicy, o)
            true
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_images)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preference: Preference? = findPreference(AUTO_DOWNLOAD_IMAGES_POLICY)
        preference?.let { setSummary(it) }
    }
}
