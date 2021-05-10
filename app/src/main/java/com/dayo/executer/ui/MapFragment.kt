package com.dayo.executer.ui

import android.content.ClipData
import android.os.Bundle
import android.graphics.drawable.Icon
import android.telephony.ims.ImsMmTelManager
import android.view.*
import android.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dayo.executer.R
import com.dayo.executer.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MapFragment : Fragment() {
    lateinit var m : MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onStart() {
        super.onStart()
        m = (activity as MainActivity)
        var navView: BottomNavigationView = m.findViewById(R.id.nav_view)
        var menunav: Menu = navView.getMenu()
        var mapitem: MenuItem = menunav.findItem(R.id.navigation_map)

        mapitem.setIcon(R.drawable.ic_baseline_search_24)
        //TODO: AndroidSlidingUpPanel을 이용해서 검색버튼 누르면 검색창 띄우기
    }

    override fun onStop() {
        super.onStop()
        m = (activity as MainActivity)
        var navView: BottomNavigationView = m.findViewById(R.id.nav_view)
        var menunav: Menu = navView.getMenu()
        var mapitem: MenuItem = menunav.findItem(R.id.navigation_map)

        mapitem.setIcon(R.drawable.ic_baseline_map_24)
    }
}