package com.dayo.executer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.webkit.*
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
    var rmdat = 5
    var reExec = true

    //lateinit var mWebView: BackgroundWebView
    val elist = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dummy)

        val webView = findViewById<WebView>(R.id.dummyWebView)
        webView.settings.javaScriptEnabled = true
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        val cookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookies { }
        cookieManager.flush()
        webView.addJavascriptInterface(AblrService.JSI(this), "jsi")
        webView.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d("asdf", view?.url.toString())
                if (view == null)
                    return
                if (view.url == null)
                    return

                Log.d("asdf", view.url!!)
                if (elist.contains(view.url))
                    return
                if(!reExec) return
                elist.add(view.url!!)
                when (view.url) {
                    "http://isds.kr/sdm/source/LOGIN/login.php" ->
                        view.loadUrl("javascript:(function () { document.getElementsByName(\"UserPW\")[0].value = \"${DataManager.ablrPW}\";document.getElementsByName(\"UserID\")[0].value = \"${DataManager.ablrID}\";document.getElementsByName(\"dormitory_code\")[0].value = \"gbs\";document.getElementsByTagName(\"button\")[0].click()})()");
                    "http://isds.kr/sdm/index.php" ->  view.loadUrl("http://isds.kr/sdm/source/SSH/sh_approve_manage.php")
                    "http://isds.kr/sdm/source/SSH/sh_approve_manage.php" -> {
                        reExec = false
                        view.loadUrl("javascript:window.jsi.getHtml(document.getElementsByTagName('html')[0].innerHTML);");
                        CoroutineScope(Dispatchers.Default).launch {
                            while (AblrService.res == "") {
                            Thread.sleep(1)
                           }
                            var doc = Jsoup.parse(AblrService.res)
                            var ele = doc.getElementsByTag("tr")
                            while(ele.size < 1) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    view.loadUrl("javascript:window.jsi.getHtml(document.getElementsByTagName('html')[0].innerHTML);");
                                    doc = Jsoup.parse(AblrService.res)
                                    ele = doc.getElementsByTag("tr")
                                }
                                Thread.sleep(100)
                            }
                            val usqLst = mutableListOf<String>()
                            val rLst = mutableListOf<String>()
                            for(x in ele) {
                                Log.d("asdf", x.attr("data-user_seq"))
                                Log.d("asdf", x.attr("data-r_seq"))
                                usqLst.add(x.attr("data-user_seq"))
                                rLst.add(x.attr("data-r_seq"))
                            }
                            for(x in usqLst.indices){
                                CoroutineScope(Dispatchers.Main).launch {
                                    view.loadUrl(
                                        "javascript:(function () { \$(\"#h_dormitory_code\").val(\"gbs\");\$(\"#h_user_id\").val(\"${DataManager.ablrID}\");" +
                                                "\$(\"#h_user_seq\").val(\"${usqLst[x]}\");\$(\"#h_r_seq\").val(\"${rLst[x]}\");loadDetailData();loadTableData2();})()"
                                    )
                                    deleteResult = 0
                                    CoroutineScope(Dispatchers.Default).launch {
                                        Thread.sleep(100)
                                        CoroutineScope(Dispatchers.Main).launch {
                                            view.loadUrl("javascript:document.getElementById(\"btnDelete\").click()")
                                        }
                                    }
                                }
                                Thread.sleep(1000)
                                while(deleteResult == 0)Thread.sleep(100)
                            }
                            Log.d("asdf", "FINISHED")
                        }
                    }
                }
            }
        }
        webView.webChromeClient = object: WebChromeClient() {
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                Log.d("asdf", message!!)
                return if(message == "정상적으로 처리되었습니다."){
                    result?.confirm()
                    deleteResult = 1
                    //Toast.makeText(this@CancelActivity, "완료되었습니다!", Toast.LENGTH_LONG).show()
                    true
                }
                else if(message == "상세 정보가 없습니다."){
                    result?.confirm()
                    deleteResult = -1
                    //Toast.makeText(App.context(), "삭제에 실패했습니다.", Toast.LENGTH_LONG).show()
                    true
                }
                else if(message=="undefined") {
                    result?.confirm()
                    deleteResult = -2
                    true
                }
                else {
                    super.onJsAlert(view, url, message, result)
                }
            }
        }
        webView.loadUrl("http://isds.kr")
    }
}