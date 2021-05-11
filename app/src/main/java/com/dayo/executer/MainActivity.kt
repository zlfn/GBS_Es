package com.dayo.executer

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ScrollView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dayo.executer.data.DataManager
import com.dayo.executer.ui.*
import com.google.android.material.navigation.NavigationView
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {
    val fragmenthome: Fragment = com.dayo.executer.ui.HomeFragment()
    val fragmentweelky:Fragment = com.dayo.executer.ui.WeeklyFragment()
    val fragmentlostthing: Fragment = com.dayo.executer.ui.LostThingInfoFragment()
    val fragmentsetting: Fragment = com.dayo.executer.ui.SettingsFragment()
    val fragmentmap: Fragment = com.dayo.executer.ui.MapFragment()
    var active : Fragment = fragmenthome

    var vifo = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DataManager.loadSettings()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_weekly, R.id.navigation_lost_thing, R.id.navigation_setting, R.id.navigation_map))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setOnNavigationItemSelectedListener (mnavviewitemselectedListener)
        //navView.setupWithNavController(navController)

        CoroutineScope(Dispatchers.Default).launch {
            val doc = Jsoup.connect("http://34.70.245.122/version.html").get()
            vifo = doc.body().text() //ablr asck ex
        }

        Toast.makeText(this, "버전 정보를 불러오고 있습니다.", Toast.LENGTH_SHORT).show()
    }

    val mnavviewitemselectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item->
        when(item.itemId) {
            R.id.navigation_home -> {
                var navView: BottomNavigationView = findViewById(R.id.nav_view)
                var menunav: Menu = navView.menu
                var mapitem: MenuItem = menunav.findItem(R.id.navigation_map)

                mapitem.setIcon(R.drawable.ic_baseline_map_24)
                mapitem.title = "지도"

                changeFragment(fragmenthome)
                true
            }
            R.id.navigation_weekly -> {
                var navView: BottomNavigationView = findViewById(R.id.nav_view)
                var menunav: Menu = navView.menu
                var mapitem: MenuItem = menunav.findItem(R.id.navigation_map)

                mapitem.setIcon(R.drawable.ic_baseline_map_24)
                mapitem.title = "지도"

                changeFragment(fragmentweelky)
                true
            }
            R.id.navigation_lost_thing -> {
                var navView: BottomNavigationView = findViewById(R.id.nav_view)
                var menunav: Menu = navView.menu
                var mapitem: MenuItem = menunav.findItem(R.id.navigation_map)

                mapitem.setIcon(R.drawable.ic_baseline_map_24)
                mapitem.title = "지도"

                changeFragment(fragmentlostthing)
                true
            }
            R.id.navigation_setting -> {
                var navView: BottomNavigationView = findViewById(R.id.nav_view)
                var menunav: Menu = navView.menu
                var mapitem: MenuItem = menunav.findItem(R.id.navigation_map)

                mapitem.setIcon(R.drawable.ic_baseline_map_24)
                mapitem.title = "지도"

                changeFragment(fragmentsetting)
                true
            }
            R.id.navigation_map -> {
                var navView: BottomNavigationView = findViewById(R.id.nav_view)
                var menunav: Menu = navView.menu
                var mapitem: MenuItem = menunav.findItem(R.id.navigation_map)

                mapitem.setIcon(R.drawable.ic_baseline_search_24)
                mapitem.title = "검색"

                changeFragment(fragmentmap)
                true
            }
            else -> {
                false
            }
        }
    }

    fun changeFragment(fragment: Fragment) {
        if(fragment!=active) {
            supportFragmentManager
                .beginTransaction()
                .remove(active)
                .replace(R.id.nav_host_fragment, fragment)
                .commit()
            active = fragment
        }
    }
}