package com.dayo.executer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.dayo.executer.data.AblrData
import com.dayo.executer.data.DataManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EditAblrActivity : AppCompatActivity() {

    var nEdtDat = -1
    var nAblrData = AblrData()
    var info = "main"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_ablr)

        val spinner = findViewById<Spinner>(R.id.spinner)
        val locItem = resources.getStringArray(R.array.place_list)
        val locDatItem = resources.getStringArray(R.array.place_data_list)
        val sthEditText = findViewById<EditText>(R.id.sth)
        val stmEditText = findViewById<EditText>(R.id.stm)
        val ethEditText = findViewById<EditText>(R.id.eth)
        val etmEditText = findViewById<EditText>(R.id.etm)

        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, locItem)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                nAblrData.locationInfo = locDatItem[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Nothing
            }
        }
        if(intent.extras != null && intent.extras!!["edt"] != null && intent.extras!!["dataInfo"] != "tmp") {
            nEdtDat = intent.extras!!["edt"] as Int
            nAblrData = DataManager.todayAblrTableData[nEdtDat]
            spinner.setSelection(locDatItem.indexOf(nAblrData.locationInfo))
            sthEditText.setText(nAblrData.sth)
            stmEditText.setText(nAblrData.stm)
            ethEditText.setText(nAblrData.eth)
            etmEditText.setText(nAblrData.etm)
        }
        else if(intent.extras != null && intent.extras!!["edt"] != null && intent.extras!!["dataInfo"] == "tmp") {
            nEdtDat = intent.extras!!["edt"] as Int
            info = "tmp"
            nAblrData = DataManager.tmpAblrData[nEdtDat]
            spinner.setSelection(locDatItem.indexOf(nAblrData.locationInfo))
            sthEditText.setText(nAblrData.sth)
            stmEditText.setText(nAblrData.stm)
            ethEditText.setText(nAblrData.eth)
            etmEditText.setText(nAblrData.etm)
        }
        else if(intent.extras != null && intent.extras!!["edt"] == null && intent.extras!!["dataInfo"] == "tmp") {
            info = "tmp"
        }

        findViewById<FloatingActionButton>(R.id.saveButton).setOnClickListener {
            nAblrData.sth = sthEditText.text.toString()
            nAblrData.stm = stmEditText.text.toString()
            nAblrData.eth = ethEditText.text.toString()
            nAblrData.etm = etmEditText.text.toString()

            if(nEdtDat == -1) {
                if (info == "main")
                    DataManager.todayAblrTableData.add(nAblrData)
                else if (info == "tmp")
                    DataManager.tmpAblrData.add(nAblrData)
            }
            else if(info == "main") DataManager.todayAblrTableData[nEdtDat] = nAblrData
            else DataManager.tmpAblrData[nEdtDat] = nAblrData
            DataManager.saveSettings()
            finish()
        }
    }
}