package com.dayo.executer.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.dayo.executer.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}