package com.dayo.executer.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import androidx.core.view.get
import com.dayo.executer.R

class LostThingInfoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lost_thing_info, container, false)
    }

    override fun onStart() {
        super.onStart()

        view?.findViewById<Button>(R.id.testBtn2)?.setOnClickListener {
            Log.e("asdf", (view?.findViewById<TableLayout>(R.id.timeTable)!![0] as HomeFragment.TimeTableRow).getData().toString())
        }
    }
}