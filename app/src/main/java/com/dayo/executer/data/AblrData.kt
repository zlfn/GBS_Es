package com.dayo.executer.data

import android.content.ComponentName
import android.content.Intent
import java.lang.IndexOutOfBoundsException

data class AblrData(var sth: String, var stm: String, var eth: String, var etm: String, var locationInfo: String) {

    constructor() : this("", "", "", "", "") {

    }

    fun getFullTime(): String = "$sth:$stm ~ $eth:$etm"

    companion object {
        fun stringToAblrData(s: String): MutableList<AblrData> {
            val rtn = mutableListOf<AblrData>()
            val psdat = s.split(' ')
            try {
                for (i in psdat.indices step (5)) {
                    rtn.add(
                        AblrData(
                            sth = psdat[i].replace('_', ' '),
                            stm = psdat[i + 1].replace('_', ' '),
                            eth = psdat[i + 2].replace('_', ' '),
                            etm = psdat[i + 3].replace('_', ' '),
                            locationInfo = psdat[i + 4].replace('_', ' ')
                        )
                    )
                }
            }
            catch(e: IndexOutOfBoundsException){

            }
            return rtn
        }

        fun ablrDataToString(dat: List<AblrData>): String {
            var rtn = ""
            for(i in dat) {
                rtn += i.toString()
            }
            rtn.dropLast(1)
            return rtn
        }
    }

    override fun toString(): String {
        return "$sth $stm $eth $etm $locationInfo "
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
 */