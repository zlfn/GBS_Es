package com.dayo.executer.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.core.content.ContextCompat.startForegroundService
import com.dayo.executer.*
import com.dayo.executer.data.AblrData
import com.dayo.executer.data.DataManager
import com.dayo.executer.data.TimeTableData


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
        val applyAblrBtn = view?.findViewById<Button>(R.id.applyAblrBtn)

        timeTable?.removeAllViews()
        ablrTable?.removeAllViews()

        sv?.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (oldScrollY - 10 > scrollY)
                nav.visibility = View.INVISIBLE
            else if (oldScrollY + 10 < scrollY)
                nav.visibility = View.VISIBLE
        }

        //Wait for INIT vifo
        while (m.vifo == "")
            Thread.sleep(1)

        if (m.packageManager.getPackageInfo("com.dayo.executer", PackageManager.GET_ACTIVITIES).versionName != m.vifo.split(' ')[2])
            Toast.makeText(activity, "업데이트가 필요합니다.", Toast.LENGTH_LONG).show()

        asckBtn?.setOnClickListener {
            startActivity(Intent(m, AsckActivity::class.java))
        }

        timeTable?.removeAllViews()
        for(i in DataManager.timeTableData)
            timeTable?.addView(TimeTableRow(m, i))

        ablrTable?.removeAllViews()
        for(i in DataManager.todayAblrTableData)
            ablrTable?.addView(AblrTableRow(m, i))

        applyAblrBtn?.setOnClickListener {
            val intent = Intent(activity, AblrService::class.java)
            startForegroundService(activity as MainActivity, intent)
            //startActivity(Intent(activity, EditAblrActivity::class.java).putExtra("edt", 1))
            //startActivity(Intent(activity, EditAblrActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()

        initUI()
    }

    class AblrTableRow(context: Context, ablrData: AblrData): TableRow(context) {
        var timeInfo: TextView = TextView(context)
        var subjectInfo: TextView = TextView(context)

        private fun addView() {
            super.removeAllViews()
            super.addView(timeInfo)
            super.addView(subjectInfo)
        }

        init {
            timeInfo.text = ablrData.getFullTime()
            subjectInfo.text = ablrData.locationInfo
            addView()
        }
    }

    class TimeTableRow(context: Context, timeTableData: TimeTableData): TableRow(context) {
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
