package com.cankarademir.cankarademir_odev7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    private lateinit var db: DB
    private lateinit var txtTitle: TextView
    private lateinit var txtNdetail: TextView
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        txtTitle = findViewById(R.id.txtDtitle)
        txtNdetail= findViewById(R.id.detailTxtDesc)
        btnDelete = findViewById(R.id.detailBtnDelete)
        db = DB(this)

        val idData = intent.getStringExtra("nId")
        val titleData = intent.getStringExtra("nTitle")
        val descData = intent.getStringExtra("nDetail")

        txtTitle.setText(titleData)
        txtNdetail.setText(descData)

        btnDelete.setOnClickListener {
            db.deleteNote(idData!!.toInt())
            val intentToHomePage = Intent(this, MainActivity::class.java)
            startActivity(intentToHomePage)
        }
    }
}