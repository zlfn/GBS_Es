package com.dayo.executer

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup



class lgs_MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lgs_activity_main)
        val backButton: Button = findViewById(R.id.button)
        backButton.setOnClickListener{
            finish()
        }

        var vifo = ""
        CoroutineScope(Dispatchers.Default).launch {
            val doc = Jsoup.connect("http://34.70.245.122/version.html").get()
            //Log.d("asdf", doc.html())
            vifo = doc.body().text()
            Log.d("asdf", vifo) //ablr asck ex
        }

        Toast.makeText(this, "버전 정보를 불러오고 있습니다.", Toast.LENGTH_SHORT).show()
        while(vifo == "") { Thread.sleep(1) }
        if(this.packageManager.getPackageInfo("com.dayo.executer", PackageManager.GET_ACTIVITIES).versionName != vifo.split(' ')[2]){
            Toast.makeText(this, "업데이트가 필요합니다.", Toast.LENGTH_LONG).show()
        }

        try {
            val strAppPackage = "com.dayo.asck"
            val pkg = this.packageManager.getPackageInfo(strAppPackage,  PackageManager.GET_ACTIVITIES);

            while(vifo == "") { Thread.sleep(1) }
            if(pkg.versionName!! != vifo.split(' ')[1].replace("b", "beta ")) {
                findViewById<TextView>(R.id.isAutoSelfCheckInstalled).text = "플러그인 업데이트가 필요합니다!"
                Log.d("asdf", pkg.versionName)
                Log.d("asdf", vifo.split(' ')[1].replace("b", "beta "))
            }
            else {
                findViewById<TextView>(R.id.isAutoSelfCheckInstalled).text = "플러그인이 설치되있습니다 :D"
                findViewById<TextView>(R.id.isAutoSelfCheckInstalled).setTextColor(Color.argb(0x50, 0, 0, 0xFF))

                findViewById<View>(R.id.autoSelfCheckMove).setOnClickListener {
                    val cn = ComponentName("com.dayo.asck", "com.dayo.asck.MainActivity")
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_LAUNCHER)
                    intent.component = cn
                    startActivity(intent)
                }
            }
        }
        catch (e: PackageManager.NameNotFoundException) {
            findViewById<View>(R.id.autoSelfCheckMove).setOnClickListener {
                AlertDialog.Builder(this)
                        .setTitle("플러그인 오류")
                        .setMessage("플러그인 설치가 필요합니다.\n설치하시겠습니까?")
                        .setPositiveButton("네") { _, _ ->
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.dayo.asck")))
                        }
                        .setNegativeButton("아니요") { _, _ -> }
                        .create().show()
            }
        }

        try {
            val strAppPackage = "com.dayo.ablr"
            val pkg = this.packageManager.getPackageInfo(strAppPackage,  PackageManager.GET_ACTIVITIES);

            while(vifo == "") { Thread.sleep(1) }
            if(pkg.versionName!! != vifo.split(' ')[0].replace("b", "beta ")) {
                findViewById<TextView>(R.id.isABLRPluginInstalled).text = "플러그인 업데이트가 필요합니다!"
                Log.d("asdf", pkg.versionName)
                Log.d("asdf", vifo.split(' ')[0].replace("b", "beta "))
            }
            else {
                findViewById<TextView>(R.id.isABLRPluginInstalled).text = "플러그인이 설치되있습니다 :D"
                findViewById<TextView>(R.id.isABLRPluginInstalled).setTextColor(Color.argb(0x50, 0, 0, 0xFF))

                findViewById<View>(R.id.ABLRMove).setOnClickListener {
                    val cn = ComponentName(strAppPackage, "$strAppPackage.MainActivity")
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_LAUNCHER)
                    intent.component = cn
                    startActivity(intent)
                }
            }
        }
        catch (e: PackageManager.NameNotFoundException) {
            findViewById<View>(R.id.ABLRMove).setOnClickListener {
                AlertDialog.Builder(this)
                        .setTitle("플러그인 오류")
                        .setMessage("플러그인 설치가 필요합니다.\n설치하시겠습니까?")
                        .setPositiveButton("네") { _, _ ->
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.dayo.ablr")))
                        }
                        .setNegativeButton("아니요") { _, _ -> }
                        .create().show()
            }
        }

    }
}