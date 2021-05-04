package com.dayo.executer.data

data class TimeTableData(val timeInfo: String, val subjectInfo: String, val teacherInfo: String, val roomInfo: String, val elseInfo: String) {
    companion object{
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