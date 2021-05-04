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
import com.dayo.executer.MainActivity
import com.dayo.executer.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.util.*


class HomeFragment : Fragment() {

    lateinit var m: MainActivity
    lateinit var nav: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun initUI() {
        m = (activity as MainActivity)
        nav = m.findViewById(R.id.nav_view)
        val sv = view?.findViewById<ScrollView>(R.id.scv)
        sv?.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (oldScrollY - 10 > scrollY)
                nav.visibility = View.INVISIBLE
            else if (oldScrollY + 10 < scrollY)
                nav.visibility = View.VISIBLE
        }

        var vifo = ""
        CoroutineScope(Dispatchers.Default).launch {
            val doc = Jsoup.connect("http://34.70.245.122/version.html").get()
            vifo = doc.body().text() //ablr asck ex
        }
        while (vifo == "") {
            Thread.sleep(1)
        }
        if ((activity as MainActivity).packageManager.getPackageInfo("com.dayo.executer", PackageManager.GET_ACTIVITIES).versionName != vifo.split(' ')[2]) {
            Toast.makeText(activity, "업데이트가 필요합니다.", Toast.LENGTH_LONG).show()
        }

        val asckBtn = view?.findViewById<Button>(R.id.sckBtn)
        try {
            val strAppPackage = "com.dayo.asck"
            val pkg = (activity as MainActivity).packageManager.getPackageInfo(strAppPackage, PackageManager.GET_ACTIVITIES);

            while (vifo == "") {
                Thread.sleep(1)
            }
            if (pkg.versionName!! != vifo.split(' ')[1].replace("b", "beta ")) {
                asckBtn?.text = "자가진단 하러가기(플러그인 업데이트 필요)"
                Log.d("asdf", pkg.versionName)
                Log.d("asdf", vifo.split(' ')[1].replace("b", "beta "))
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
        Log.d("asdf", "http://34.70.245.122/timetable/101/${SimpleDateFormat("yyyy-MM-dd").format(Date())}.html")

        var tableData = "9:10~10:00 영어 서원화 암것도_없음 10:10~11:00 국어 전은선 수행_없음"
        /*
        CoroutineScope(Dispatchers.Default).launch {
            val doc = Jsoup.connect("http://34.70.245.122/timetable/101/${SimpleDateFormat("yyyy-MM-dd").format(Date())}.html").get()
            Log.d("asdf", doc.html())
            tableData = doc.body().text() //time sub t something<br>
        }
        while (tableData == "") {
            Thread.sleep(1)
        }
         */
        val tableParsedData = tableData.split(' ')
        val timeTable = view?.findViewById<TableLayout>(R.id.timeTable)
        timeTable?.removeAllViews()
        for (i in tableParsedData.indices step (4)) {
            val x = TimeTableRow(activity?.applicationContext!!,
                    tableParsedData[i].replace('_', ' '),
                    tableParsedData[i + 1].replace('_', ' '),
                    tableParsedData[i + 2].replace('_', ' '),
                    tableParsedData[i + 3].replace('_', ' '))
            timeTable?.addView(x.getRow())
        }

        var ablrData = "18 50 19 40 학습실 19 50 20 40 학습실 20 50 21 30 학습실 21 40 23 59 동아리_활동"
        /*
        CoroutineScope(Dispatchers.Default).launch {
            val doc = Jsoup.connect("http://34.70.245.122/timetable/101/${SimpleDateFormat("yyyy-MM-dd").format(Date())}.html").get()
            Log.d("asdf", doc.html())
            tableData = doc.body().text() //time sub t something<br>
        }
        while (tableData == "") {
            Thread.sleep(1)
        }
         */
        val ablrParsedData = ablrData.split(' ')
        val ablrTable = view?.findViewById<TableLayout>(R.id.ablrTable)
        ablrTable?.removeAllViews()
        for (i in ablrParsedData.indices step (5)) {
            val x = AblrTableRow(activity?.applicationContext!!,
                    ablrParsedData[i].replace('_', ' '),
                    ablrParsedData[i + 1].replace('_', ' '),
                    ablrParsedData[i + 2].replace('_', ' '),
                    ablrParsedData[i + 3].replace('_', ' '),
                    ablrParsedData[i + 4].replace('_', ' '))
            ablrTable?.addView(x.getRow())
        }
    }

    override fun onStart() {
        super.onStart()

        initUI()
    }

    class AblrTableRow(context: Context, sth: String, stm: String, eth: String, etm: String, tii: String): TableRow(context){
        var timeInfo: TextView = TextView(context)
        var subjectInfo: TextView = TextView(context)
        private lateinit var tableRow: TableRow

        private fun addView() {
            tableRow = TableRow(context)
            tableRow.addView(timeInfo)
            tableRow.addView(subjectInfo)
        }

        fun getRow(): TableRow{
            addView()
            return tableRow
        }

        init{
            timeInfo.text = "$sth:$stm ~ $eth:$etm"
            subjectInfo.text = tii
        }
    }

    class TimeTableRow(context: Context, tii: String, subi: String, ti: String, ei: String) : TableRow(context) {
        var timeInfo: TextView = TextView(context)
        var subjectInfo: TextView = TextView(context)
        var tInfo: TextView = TextView(context)
        var elseInfo: TextView = TextView(context)
        private lateinit var tableRow: TableRow

        private fun addView() {
            tableRow = TableRow(context)
            tableRow.addView(timeInfo)
            tableRow.addView(subjectInfo)
            tableRow.addView(tInfo)
            tableRow.addView(elseInfo)
        }

        fun getRow(): TableRow {
            addView()
            return tableRow
        }

        init {
            timeInfo.text = tii
            subjectInfo.text = subi
            tInfo.text = ti
            elseInfo.text = ei
        }
    }
}
