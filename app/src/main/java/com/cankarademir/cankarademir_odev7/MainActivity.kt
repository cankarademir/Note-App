package com.cankarademir.cankarademir_odev7

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cankarademir.cankarademir_odev7.adapter.NoteAdapter
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var db: DB
    lateinit var notetitle:EditText
    lateinit var notedetail:EditText
    lateinit var btnDate: ImageButton
    lateinit var btnSave: Button
    lateinit var noteList: RecyclerView
    var selectDate = ""
    var adapter: NoteAdapter? = null

    override fun onStart() {
        super.onStart()
        getNote()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSave = findViewById(R.id.btnSave)
        btnDate = findViewById(R.id.btnDate)
        notetitle = findViewById(R.id.notetitle)
        notedetail = findViewById(R.id.notedetail)
        noteList= findViewById(R.id.noteList)
        db = DB(this)

        noteList.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter()
        noteList.adapter = adapter

        val calender = Calendar.getInstance()
        btnDate.setOnClickListener(View.OnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                    var ay = "${i2+1}"
                    if ( i2+1 < 10 ) {
                        ay = "0${i2+1}"
                    }
                    selectDate = "$i3.$ay.$i"
                },
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH),
            )
            datePickerDialog.show()
        })

        btnSave.setOnClickListener {
            val n_title= notetitle.text.toString()
            val n_detail= notedetail.text.toString()
            if ( selectDate != "" && n_title!= "" && n_detail != "" ) {
                var status = db.addNote(n_title, n_detail, selectDate)
                selectDate = ""
            }else {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_LONG).show()
            }
            getNote()
        }
        adapter?.setOnClickItem {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("nId", it.nid.toString())
            intent.putExtra("nTitle", it.title)
            intent.putExtra("nDetail", it.detail)
            startActivity(intent)
        }
    }

    private fun getNote() {
        val status = db.allNote()
        adapter?.addItems(status)
    }
}