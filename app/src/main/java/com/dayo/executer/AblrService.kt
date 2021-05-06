package com.dayo.executer

import android.R
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.dayo.executer.data.DataManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AblrService : Service() {

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }

    lateinit var mWebView: BackgroundWebView
    val elist = mutableListOf<String>()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, "default") //오레오 부터 channelId가 반드시 필요하다.

        //builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle("포그라운드 서비스")
        builder.setContentText("포그라운 서비스 실행중")

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        builder.setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //오레오 이상부터 이 코드가 동작한다.
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(
                NotificationChannel(
                    "default",
                    "기본 채널",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
        startForeground(1, builder.build())
        mWebView = BackgroundWebView(this)
        mWebView.webViewClient = object: WebViewClient() {}
        mWebView.webChromeClient = object: WebChromeClient() {
            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                Log.d("asdf", message!!)
                return if (message == "정상적으로 처리되었습니다.") {
                    result?.confirm()
                    CoroutineScope(Dispatchers.Default).launch {
                        CoroutineScope(Dispatchers.Main).launch {
                            view?.loadUrl("about:blank")
                        }
                        delay(1000)
                        CoroutineScope(Dispatchers.Main).launch {
                            //Finished
                        }
                    }
                    true
                } else {
                    super.onJsAlert(view, url, message, result)
                }
            }

            override fun onJsConfirm(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                Log.d("asdf", message!!)
                return if (message == "해당 내용을 신청하시겠습니까?") {
                    result?.confirm()
                    true
                } else {
                    super.onJsConfirm(view, url, message, result)
                }
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                if (view!!.url == null)
                    return

                if (newProgress == 100) {
                    if (elist.contains(view.url))
                        return
                    elist.add(view.url!!)
                    Log.d("asdf", view.url!!)
                    when (view.url) {
                        "http://isds.kr/sdm/source/LOGIN/login.php" ->
                            //view.loadUrl("javascript:(function () { document.getElementsByName(\"UserPW\")[0].value = \"${DataManager.pw}\";document.getElementsByName(\"UserID\")[0].value = \"${DataManager.id}\";document.getElementsByName(\"dormitory_code\")[0].value = \"gbs\";document.getElementsByTagName(\"button\")[0].click()})()");
                            view.loadUrl("javascript:(function () { document.getElementsByName(\"UserPW\")[0].value = \"${DataManager.ablrPW}\";document.getElementsByName(\"UserID\")[0].value = \"${DataManager.ablrID}\";document.getElementsByName(\"dormitory_code\")[0].value = \"gbs\";document.getElementsByTagName(\"button\")[0].click()})()");
                        "http://isds.kr/sdm/index.php" -> {
                            //setProgressDialog("설정 진입중...")
                            view.loadUrl("javascript:(function () { document.getElementsByTagName(\"button\")[0].click();document.getElementsByTagName(\"a\")[5].click();document.getElementsByTagName(\"a\")[6].click()})()");
                        }
                        "http://isds.kr/sdm/source/SSH/sh_apply_manage.php" -> {
                            view.loadUrl("javascript:document.getElementById(\"btnDataAdd\").click()");
                            CoroutineScope(Dispatchers.Default).launch {
                                delay(1500)
                                CoroutineScope(Dispatchers.Main).launch {
                                    //setProgressDialog("추가하는중...")
                                    //view.loadUrl("javascript:(function () { document.getElementById(\"popup_out_reason\").value = \"$plc\";document.getElementById(\"popup_out_start_time1\").value = \"$sth\";document.getElementById(\"popup_out_start_time2\").value = \"$stm\";document.getElementById(\"popup_out_end_time1\").value = \"$eth\";document.getElementById(\"popup_out_end_time2\").value = \"$etm\";document.getElementById(\"btnConfirmOut\").click()})()");
                                    view.loadUrl("javascript:(function () { document.getElementById(\"popup_out_reason\").value = \"note1\";document.getElementById(\"popup_out_start_time1\").value = \"13\";document.getElementById(\"popup_out_start_time2\").value = \"23\";document.getElementById(\"popup_out_end_time1\").value = \"23\";document.getElementById(\"popup_out_end_time2\").value = \"33\";document.getElementById(\"btnConfirmOut\").click()})()");
                                }
                            }
                        }
                    }
                }
            }
        }
        mWebView.settings.javaScriptEnabled = true
        mWebView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        val cookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookies { }
        cookieManager.flush()
        mWebView.loadUrl("http://isds.kr")
        return START_STICKY
    }

    class BackgroundWebView : WebView {
        constructor(context: Context?) : super(context!!)
        constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
        constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context!!,
            attrs,
            defStyleAttr
        )

        override fun onWindowVisibilityChanged(visibility: Int) {
            if (visibility != View.GONE) super.onWindowVisibilityChanged(View.VISIBLE)
        }
    }
}