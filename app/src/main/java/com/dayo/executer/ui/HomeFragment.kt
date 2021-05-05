package com.dayo.executer.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.core.view.size
import com.dayo.executer.App
import com.dayo.executer.MainActivity
import com.dayo.executer.R
import com.dayo.executer.data.AblrData
import com.dayo.executer.data.DataManager
import com.dayo.executer.data.TimeTableData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.util.*


class HomeFragment : Fragment() {

    lateinit var m: MainActivity
    lateinit var nav: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun initUI() {
        m = (activity as MainActivity)
        nav = m.findViewById(R.id.nav_view)

        val sv = view?.findViewById<ScrollView>(R.id.scv)
        val timeTable = view?.findViewById<TableLayout>(R.id.timeTable)
        val ablrTable = view?.findViewById<TableLayout>(R.id.ablrTable)
        val asckBtn = view?.findViewById<Button>(R.id.sckBtn)

        timeTable?.removeAllViews()
        ablrTable?.removeAllViews()

        sv?.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (oldScrollY - 10 > scrollY)
                nav.visibility = View.INVISIBLE
            else if (oldScrollY + 10 < scrollY)
                nav.visibility = View.VISIBLE
        }
        //Wait for INIT vifo
        while (m.vifo == "") {
            Thread.sleep(1)
        }
        if (m.packageManager.getPackageInfo("com.dayo.executer", PackageManager.GET_ACTIVITIES).versionName != m.vifo.split(' ')[2]) {
            Toast.makeText(activity, "업데이트가 필요합니다.", Toast.LENGTH_LONG).show()
        }

        try {
            val strAppPackage = "com.dayo.asck"
            val pkg = (activity as MainActivity).packageManager.getPackageInfo(strAppPackage, PackageManager.GET_ACTIVITIES)

            while (m.vifo == "") {
                Thread.sleep(1)
            }
            if (pkg.versionName!! != m.vifo.split(' ')[1].replace("b", "beta ")) {
                asckBtn?.text = "자가진단 하러가기(플러그인 업데이트 필요)"
            }
            asckBtn?.setOnClickListener {
                val cn = ComponentName("com.dayo.asck", "com.dayo.asck.MainActivity")
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_LAUNCHER)
                intent.component = cn
                startActivity(intent)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            asckBtn?.text = "플러그인 설치가 필요합니다."
            asckBtn?.isEnabled = false
        }

        for(i in DataManager.timeTableData)
            timeTable?.addView(TimeTableRow(m, i))

        for(i in DataManager.todayAblrTableData)
            ablrTable?.addView(AblrTableRow(m, i))
    }

    override fun onStart() {
        super.onStart()

        initUI()
    }

    class AblrTableRow(context: Context, private val ablrData: AblrData): TableRow(context) {
        var timeInfo: TextView = TextView(context)
        var subjectInfo: TextView = TextView(context)

        private fun addView() {
            super.removeAllViews()
            super.addView(timeInfo)
            super.addView(subjectInfo)
        }

        fun getRow(): AblrTableRow {
            addView()
            return this
        }

        fun getData(): AblrData = ablrData

        init {
            timeInfo.text = ablrData.getFullTime()
            subjectInfo.text = ablrData.locationInfo
            addView()
        }
    }

    class TimeTableRow(context: Context, private val timeTableData: TimeTableData): TableRow(context) {
        var timeInfo: TextView = TextView(context)
        var subjectInfo: TextView = TextView(context)
        var tInfo: TextView = TextView(context)
        var elseInfo: TextView = TextView(context)
        var roomInfo: TextView = TextView(context)

        private fun addView() {
            super.removeAllViews()
            super.addView(timeInfo)
            super.addView(subjectInfo)
            super.addView(tInfo)
            super.addView(roomInfo)
            super.addView(elseInfo)
        }

        fun getRow(): TimeTableRow {
            addView()
            return this
        }

        fun getData(): TimeTableData = timeTableData

        init {
            timeInfo.text = timeTableData.timeInfo
            subjectInfo.text = timeTableData.subjectInfo
            tInfo.text = timeTableData.teacherInfo
            elseInfo.text = timeTableData.elseInfo
            roomInfo.text = timeTableData.roomInfo
            addView()
        }
    }
}
