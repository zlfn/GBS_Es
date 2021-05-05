package com.dayo.executer.data

import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import com.dayo.executer.App

class DataManager {
    companion object{
        private val sharedPref = App.appContext!!.getSharedPreferences("settings", MODE_PRIVATE)
        var ablrID = ""
        var ablrPW = ""
        var asckPW = ""

        fun saveSettings() {
            sharedPref.edit {
                putString("ablrID", ablrID)
                putString("ablrPW", ablrPW)
                putString("asckPW", asckPW)
                apply()
            }
        }

        fun loadSettings() {
            ablrID = sharedPref.getString("ablrID", "")!!
            ablrPW = sharedPref.getString("ablrPW", "")!!
            asckPW = sharedPref.getString("asckPW", "")!!
        }
    }
}