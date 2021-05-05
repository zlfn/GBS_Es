package com.dayo.executer.data

import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import com.dayo.executer.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class DataManager {
    companion object{
        var timeTableData = mutableListOf<TimeTableData>()
        var todayAblrTableData = mutableListOf<AblrData>()

        private val sharedPref = App.appContext!!.getSharedPreferences("settings", MODE_PRIVATE)
        var ablrID = ""
        var ablrPW = ""
        var asckPW = ""
        var classInfo = ""

        fun saveSettings() {
            sharedPref.edit {
                putString("ablrID", ablrID)
                putString("ablrPW", ablrPW)
                putString("asckPW", asckPW)
                putString("classInfo", classInfo)
                apply()
            }
        }

        fun loadSettings() {
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
                DataManager.timeTableData.add(i)
            var ablrData = "18 50 19 40 학습실 19 50 20 40 학습실 20 50 21 30 학습실 21 40 23 59 동아리_활동" // => Format
            for(i in AblrData.stringToAblrData(ablrData))
                DataManager.todayAblrTableData.add(i)

            ablrID = sharedPref.getString("ablrID", "")!!
            ablrPW = sharedPref.getString("ablrPW", "")!!
            asckPW = sharedPref.getString("asckPW", "")!!
            classInfo = sharedPref.getString("classInfo", "1-1")!!
        }
    }
}