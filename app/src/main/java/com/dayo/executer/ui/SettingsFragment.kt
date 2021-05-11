package com.dayo.executer.ui

import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dayo.executer.R
import com.dayo.executer.data.DataManager

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(Color.parseColor("#303030"))
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val classPreferences = findPreference<ListPreference>("class")!!

        val ablrIDPreference = findPreference<EditTextPreference>("ablrID")!!
        val ablrPWPreference = findPreference<EditTextPreference>("ablrPW")!!

        val asckPWPreference = findPreference<EditTextPreference>("asckPW")!!
        val asckUseAdvOptPreference = findPreference<SwitchPreference>("asckAdv")!!
        val asckDtPreference = findPreference<EditTextPreference>("dt")!!
        val asckDselPreference = findPreference<EditTextPreference>("dsel")!!
        val asckDsPreference = findPreference<EditTextPreference>("ds")!!

        classPreferences.setEntries(R.array.class_list)
        classPreferences.setEntryValues(R.array.class_list)
        classPreferences.value = DataManager.classInfo

        ablrIDPreference.text = DataManager.ablrID
        ablrPWPreference.text = DataManager.ablrPW

        asckPWPreference.text = DataManager.asckPW
        asckUseAdvOptPreference.isChecked = DataManager.asckUseAdvOpt
        asckDtPreference.text = DataManager.asckDt.toString()
        asckDselPreference.text = DataManager.asckDsel.toString()
        asckDsPreference.text = DataManager.asckDs.toString()

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

        asckUseAdvOptPreference.setOnPreferenceChangeListener { _, newValue ->
            DataManager.asckUseAdvOpt = newValue as Boolean
            true
        }

        asckDtPreference.setOnPreferenceChangeListener { _, newValue ->
            DataManager.asckDt = newValue.toString().toLong()
            true
        }

        asckDtPreference.setOnBindEditTextListener {
            it.inputType = InputType.TYPE_CLASS_NUMBER
            val fArray = arrayOfNulls<InputFilter>(1)
            fArray[0] = InputFilter.LengthFilter(4)
            it.filters = fArray
        }

        asckDselPreference.setOnPreferenceChangeListener { _, newValue ->
            DataManager.asckDsel = newValue.toString().toLong()
            true
        }

        asckDselPreference.setOnBindEditTextListener {
            it.inputType = InputType.TYPE_CLASS_NUMBER
            val fArray = arrayOfNulls<InputFilter>(1)
            fArray[0] = InputFilter.LengthFilter(4)
            it.filters = fArray
        }

        asckDsPreference.setOnPreferenceChangeListener { _, newValue ->
            DataManager.asckDs = newValue.toString().toLong()
            true
        }

        asckDsPreference.setOnBindEditTextListener {
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