package com.dayo.executer.ui

import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.dayo.executer.R
import com.dayo.executer.data.DataManager

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val classPreferences = findPreference<ListPreference>("class")!!

        val ablrIDPreference = findPreference<EditTextPreference>("ablrID")!!
        val ablrPWPreference = findPreference<EditTextPreference>("ablrPW")!!

        val asckPWPreference = findPreference<EditTextPreference>("asckPW")!!

        classPreferences.setEntries(R.array.class_list)
        classPreferences.setEntryValues(R.array.class_list)
        classPreferences.value = DataManager.classInfo

        ablrIDPreference.text = DataManager.ablrID
        ablrPWPreference.text = DataManager.ablrPW

        asckPWPreference.text = DataManager.asckPW

        classPreferences.setOnPreferenceChangeListener { _, newValue ->
            DataManager.classInfo = newValue.toString()
            true
        }

        ablrIDPreference.setOnPreferenceChangeListener { _, newValue ->
            DataManager.ablrID = newValue.toString()
            true
        }

        ablrPWPreference.setOnPreferenceChangeListener { _, newValue ->
            DataManager.ablrPW = newValue.toString()
            true
        }

        asckPWPreference.setOnPreferenceChangeListener { _, newValue ->
            DataManager.asckPW = newValue.toString()
            true
        }

        asckPWPreference.setOnBindEditTextListener {
            it.inputType = InputType.TYPE_CLASS_NUMBER
            val fArray = arrayOfNulls<InputFilter>(1)
            fArray[0] = InputFilter.LengthFilter(4)
            it.filters = fArray
        }
    }

    override fun onStop() {
        super.onStop()
        DataManager.saveSettings()
    }
}