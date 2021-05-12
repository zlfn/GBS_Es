package com.dayo.executer.data

import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.core.content.edit
import com.dayo.executer.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.*

class DataManager {
    companion object{
        var timeTableData = mutableListOf<TimeTableData>()
        var todayAblrTableData = mutableListOf<AblrData>()
        var tmpAblrData = mutableListOf<AblrData>()

        private val sharedPref = App.appContext!!.getSharedPreferences("settings", MODE_PRIVATE)
        var ablrID = ""
        var ablrPW = ""
        var asckPW = ""
        var classInfo = ""

        var asckDt = 0L
        var asckDsel = 0L
        var asckDs = 0L
        var asckUseAdvOpt = false

        var noTempDataInHomeFragment = false

        var lowProtect = false

        var dayOfWeek = -1

        fun saveSettings() {
            sharedPref.edit {
                putString("ablr$dayOfWeek", AblrData.ablrDataToString(todayAblrTableData))
                Log.d("asdf", "$dayOfWeek ${AblrData.ablrDataToString(todayAblrTableData)}")
                putString("ablrID", ablrID)
                putString("ablrPW", ablrPW)
                putString("asckPW", asckPW)
                putString("classInfo", classInfo)
                putLong("asckDt", asckDt)
                putLong("asckDsel", asckDsel)
                putLong("asckDs", asckDs)
                putBoolean("asckUseAdvOpt", asckUseAdvOpt)
                putBoolean("lowProtect", lowProtect)
                putBoolean("noTempDataInHomeFragment", noTempDataInHomeFragment)
                apply()
            }
        }

        fun loadSettings() {
            todayAblrTableData = mutableListOf()
            timeTableData = mutableListOf()
            var tableData = ""
            CoroutineScope(Dispatchers.Default).launch {
                //val doc = Jsoup.connect("http://34.70.245.122/timetable/${classInfo.replace('-', '0')}/${SimpleDateFormat("yyyy-MM-dd").format(Date())}.html").get()
                val doc = Jsoup.connect("http://34.70.245.122/timetable/101/2021-05-04.html").get()
                tableData = doc.body().text()
            }
            while (tableData == "") {
                Thread.sleep(1)
            }
            for(i in TimeTableData.stringToTimeTableData(tableData))
                timeTableData.add(i)
            /*
            var ablrData = "18 50 19 40 note1 19 50 20 40 note1 20 50 21 30 note1 21 40 23 59 s15" // => Format
            for(i in AblrData.stringToAblrData(ablrData))
                todayAblrTableData.add(i)
            */
            dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            var ablrData = sharedPref.getString("ablr$dayOfWeek", "")!!
            for(i in AblrData.stringToAblrData(ablrData))
                todayAblrTableData.add(i)
            ablrID = sharedPref.getString("ablrID", "")!!
            ablrPW = sharedPref.getString("ablrPW", "")!!
            asckPW = sharedPref.getString("asckPW", "")!!
            classInfo = sharedPref.getString("classInfo", "1-1")!!
            asckDt = sharedPref.getLong("asckDt", 10L)
            asckDsel = sharedPref.getLong("asckDsel", 1500L)
            asckDs = sharedPref.getLong("asckDs", 1000L)
            asckUseAdvOpt = sharedPref.getBoolean("asckUseAdvOpt", false)
            lowProtect = sharedPref.getBoolean("lowProtect", false)
            noTempDataInHomeFragment = sharedPref.getBoolean("noTempDataInHomeFragment", false)
            tmpAblrData = mutableListOf()
            tmpAblrData.addAll(todayAblrTableData)
        }
    }
}