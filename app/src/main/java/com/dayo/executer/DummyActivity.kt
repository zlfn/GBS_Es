package com.dayo.executer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.webkit.*
import android.widget.Toast
import com.dayo.executer.data.DataManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class DummyActivity : AppCompatActivity() {
    companion object {
        var res = ""
    }
    var deleteResult = 1
    var rigResult = 1
    var reExec = true
    var isFinished = false

    //lateinit var mWebView: BackgroundWebView
    var elist = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dummy)


    }
}