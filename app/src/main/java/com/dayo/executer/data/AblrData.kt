package com.dayo.executer.data

data class AblrData(val sth: String, val stm: String, val eth: String, val etm: String, val locationInfo: String) {
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