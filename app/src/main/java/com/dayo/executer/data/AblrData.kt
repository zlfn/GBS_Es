package com.dayo.executer.data

data class AblrData(val sth: String, val stm: String, val eth: String, val etm: String, val locationInfo: String) {
    fun getFullTime(): String = "$sth:$stm ~ $eth:$etm"
}