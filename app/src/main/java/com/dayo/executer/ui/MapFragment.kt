package com.dayo.executer.ui

import android.content.ClipData
import android.os.Bundle
import android.graphics.drawable.Icon
import android.telephony.ims.ImsMmTelManager
import android.util.Log
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
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class MapFragment : Fragment() {
    lateinit var m : MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onResume() {
        super.onResume()
        openPanel()
        Log.d("asdf", "OpenedPanel")
        //TODO: AndroidSlidingUpPanel을 이용해서 검색버튼 누르면 검색창 띄우기
    }

    override fun onStop() {
        super.onStop()
        Log.d("asdf", "ClosedPanel")
    }

    fun openPanel() {
        m = (activity as MainActivity)
        var drawer: SlidingUpPanelLayout = m.findViewById(R.id.main_panel)
        drawer.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED)
    }
}