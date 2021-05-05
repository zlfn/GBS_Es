package com.dayo.executer

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import com.dayo.executer.data.DataManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AsckActivity : AppCompatActivity() {
    val executeList: MutableList<String> = emptyList<String>().toMutableList()
    var execute = false
    var npwd = ""
    var ndt = 0L
    var ndsel = 0L
    var nds = 0L

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asck)

        val webView = findViewById<WebView>(R.id.mainWebView)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.loadsImagesAutomatically = true
        webView.settings.setSupportZoom(false)
        webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                if(url?.startsWith("http:") == true && !DataManager.lowProtect) {
                    view?.stopLoading()
                    view?.goBack()
                    AlertDialog.Builder(this@AsckActivity)
                        .setTitle("오류")
                        .setMessage("보안 이슈로 인해 로딩이 중지되었습니다.\nERR_PROTECTION")
                        .setPositiveButton("확인") { _, _ -> }
                        .create().show()
                }
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                if(newProgress == 100 && view?.url!! == "https://hcs.eduro.go.kr/#/loginHome" && !executeList.contains(view.url!!)) {
                    executeList.add(view.url!!)
                    AlertDialog.Builder(this@AsckActivity)
                        .setTitle("초기 설정 필요")
                        .setMessage("최초 등록은 수동으로 진행해야 합니다\n등록 후 설정에서 초기설정을 진행해주세요!\n초기 설정 이후 자동화가 가능합니다. :D")
                        .setPositiveButton("OK") { _, _ -> }
                        .create().show()
                }

                if(newProgress == 100 && execute) {
                    if(!executeList.contains(view?.url!!)) {
                        executeList.add(view.url!!)
                        CoroutineScope(Dispatchers.Default).launch {
                            delay(ndt)
                            CoroutineScope(Dispatchers.Main).launch {
                                when (view.url!!) {
                                    "https://hcs.eduro.go.kr/#/loginWithUserInfo", "https://hcs.eduro.go.kr/#/relogin" -> {
                                        webView.loadUrl("javascript:document.getElementsByTagName(\"input\")[0].setRangeText(\"$npwd\")")

                                        delay(ndt)
                                        CoroutineScope(Dispatchers.Main).launch {
                                            webView.loadUrl("javascript:document.getElementById(\"btnConfirm\").click()")
                                        }

                                    }
                                    "https://hcs.eduro.go.kr/#/main" -> {
                                        webView.loadUrl("javascript:document.getElementsByTagName(\"a\")[1].click()")
                                    }
                                    "https://hcs.eduro.go.kr/#/survey" -> {
                                        delay(ndsel)
                                        CoroutineScope(Dispatchers.Main).launch {
                                            webView.loadUrl("javascript:document.getElementById(\"survey_q1a1\").click()")
                                            webView.loadUrl("javascript:document.getElementById(\"survey_q2a1\").click()")
                                            webView.loadUrl("javascript:document.getElementById(\"survey_q3a1\").click()")
                                        }
                                        delay(ndt)
                                        CoroutineScope(Dispatchers.Main).launch {
                                            webView.loadUrl("javascript:document.getElementById(\"btnConfirm\").click()")
                                        }
                                        execute = false

                                    }
                                }
                            }
                        }
                    }
                } //자동화 script
            }
        }
        webView.loadUrl("https://eduro.goe.go.kr/hcheck/index.jsp")

        CoroutineScope(Dispatchers.Default).launch {
            delay(DataManager.asckDs)
            CoroutineScope(Dispatchers.Main).launch {
                npwd = DataManager.asckPW
                ndt = DataManager.asckDt
                ndsel = DataManager.asckDsel
                nds = DataManager.asckDs
                executeList.clear()
                execute = true
                webView.loadUrl("https://eduro.goe.go.kr/hcheck/index.jsp")
            }
        }
    }

    override fun onBackPressed() {
        val wb = findViewById<WebView>(R.id.mainWebView)
        if(wb.canGoBack() && wb.url != "https://hcs.eduro.go.kr/#/main") wb.goBack()
        else super.onBackPressed()
    }
}