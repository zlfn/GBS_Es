package com.dayo.executer.data

import android.util.Log

data class TimeTableData(val timeidx: String, val timeInfo: String, val subjectInfo: String, val teacherInfo: String, val roomInfo: String, val elseInfo: String) {
    companion object {
        /*
        fun stringToTimeTableData(s: String): MutableList<TimeTableData> {
            val rtn = mutableListOf<TimeTableData>()
            val psdat = s.split(' ')
            for (i in psdat.indices step (5)) {
                rtn.add(TimeTableData(
                        timeInfo = psdat[i].replace('_', ' '),
                        subjectInfo = psdat[i + 1].replace('_', ' '),
                        teacherInfo = psdat[i + 2].replace('_', ' '),
                        roomInfo = psdat[i + 3].replace('_', ' '),
                        elseInfo = psdat[i + 4].replace('_', ' ')))
            }
            return rtn
        }
         */
        fun stringToTimeTableData(s: String): MutableList<MutableList<TimeTableData>> {
            Log.d("asdf", s)
            val rtn = mutableListOf(mutableListOf<TimeTableData>())
            rtn.add(mutableListOf())
            val psdat = s.substring(1, s.length - 2)
            var idx = 1
            for(i in psdat.split('`')){
                rtn.add(mutableListOf())
                if(i.length < 2){
                    idx++
                    continue
                }
                for(time in i.split('^')) {
                    if (time.length < 2) break

                    val dat = time.split('|')
                    if (dat.size < 5) break
                    rtn[idx].add(
                        TimeTableData(
                            timeidx = dat[0],
                            timeInfo = dat[1],
                            subjectInfo = dat[2],
                            teacherInfo = dat[3],
                            roomInfo = dat[4],
                            elseInfo = dat[5]
                        )
                    )
                }
                idx++
            }
            return rtn
        }
    }
}

/*
 * Example data
 *
 * timeInfo: time
 * subjectInfo: subject
 * teacherInfo: name of teacher
 * roomInfo: location
 * elseInfo: other info(exam, homework, etc.)
 *
 * 9:10~10:00 영어 서원화 어학실2 암것도_없음
 */