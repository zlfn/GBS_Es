package com.dayo.executer

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ScrollView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dayo.executer.data.DataManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.net.ConnectException

class MainActivity : AppCompatActivity() {

    var vifo = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DataManager.loadSettings()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_weekly,
                R.id.navigation_lost_thing,
                R.id.navigation_setting,
                R.id.navigation_map
            )
        )
      
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener(mOnNavigationItemSelectedListener)
        navView.setupWithNavController(navController)

        try {
            CoroutineScope(Dispatchers.Default).launch {
                val doc = Jsoup.connect("http://34.70.245.122/version.html").get()
                vifo = doc.body().text() //ablr asck ex
            }
        }
        catch(e: ConnectException){
            Toast.makeText(this, "Failed to collect version info", Toast.LENGTH_LONG).show()
        }

        Toast.makeText(this, "버전 정보를 불러오고 있습니다.", Toast.LENGTH_SHORT).show()
    }

    private val mOnNavigationItemSelectedListener = NavController.OnDestinationChangedListener { controller, destination, arguments ->

        when (destination.id) {
            R.id.navigation_map -> {
                val navView: BottomNavigationView = findViewById(R.id.nav_view)
                val menunav: Menu = navView.menu
                val mapitem: MenuItem = menunav.findItem(R.id.navigation_map)

                mapitem.setIcon(R.drawable.ic_baseline_search_24)
                mapitem.title = "검색"
            }
            R.id.navigation_home -> {
                val navView: BottomNavigationView = findViewById(R.id.nav_view)
                val menunav: Menu = navView.menu
                val mapitem: MenuItem = menunav.findItem(R.id.navigation_map)

                mapitem.setIcon(R.drawable.ic_baseline_map_24)
                mapitem.title = "지도"
            }
            R.id.navigation_lost_thing -> {
                val navView: BottomNavigationView = findViewById(R.id.nav_view)
                val menunav: Menu = navView.menu
                val mapitem: MenuItem = menunav.findItem(R.id.navigation_map)

                mapitem.setIcon(R.drawable.ic_baseline_map_24)
                mapitem.title = "지도"
            }
            R.id.navigation_setting -> {
                val navView: BottomNavigationView = findViewById(R.id.nav_view)
                val menunav: Menu = navView.menu
                val mapitem: MenuItem = menunav.findItem(R.id.navigation_map)

                mapitem.setIcon(R.drawable.ic_baseline_map_24)
                mapitem.title = "지도"
            }
            R.id.navigation_weekly -> {
                val navView: BottomNavigationView = findViewById(R.id.nav_view)
                val menunav: Menu = navView.menu
                val mapitem: MenuItem = menunav.findItem(R.id.navigation_map)

                mapitem.setIcon(R.drawable.ic_baseline_map_24)
                mapitem.title = "지도"
            }
        }
        false
    }

    fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }
}