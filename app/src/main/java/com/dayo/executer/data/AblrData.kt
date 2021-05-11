package com.dayo.executer.data

import android.content.ComponentName
import android.content.Intent

data class AblrData(var sth: String, var stm: String, var eth: String, var etm: String, var locationInfo: String) {

    constructor() : this("", "", "", "", "") {

    }

    fun getFullTime(): String = "$sth:$stm ~ $eth:$etm"

    companion object {
        fun stringToAblrData(s: String): MutableList<AblrData> {
            val rtn = mutableListOf<AblrData>()
            val psdat = s.split(' ')
            for (i in psdat.indices step (5)) {
                rtn.add(AblrData(
                        sth = psdat[i].replace('_', ' '),
                        stm = psdat[i + 1].replace('_', ' '),
                        eth = psdat[i + 2].replace('_', ' '),
                        etm = psdat[i + 3].replace('_', ' '),
                        locationInfo = psdat[i + 4].replace('_', ' ')))
            }
            return rtn
        }
    }

    fun toIntent(): Intent {
        val cn = ComponentName("com.dayo.ablr", "com.dayo.ablr.AddNewActivity")
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.component = cn
        intent.putExtra("plc", locationInfo)
        intent.putExtra("sth", sth)
        intent.putExtra("stm", stm)
        intent.putExtra("eth", eth)
        intent.putExtra("etm", etm)
        intent.putExtra("id", DataManager.ablrID)
        intent.putExtra("pw", DataManager.ablrPW)
        return intent
    }
}

/*
 * Example data
 *
 * sth: start time(hour)
 * stm: start time(min)
 * eth: end time(hour)
 * etm: end time(min)
 * locationInfo: location
 *
 * 9:10~10:00 영어 서원화 어학실2 암것도_없음
 */