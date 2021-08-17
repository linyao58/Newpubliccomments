package com.example.newpubliccomments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_synopsis.*

class SynopsisActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_synopsis)

        var bundle = intent.extras
        var getpholos = bundle?.getString("gopholo").toString()
        var getsynoid = bundle?.getInt("data_id")

        Del_backsyno.setOnClickListener {
            val intent = Intent("com.example.newpubliccomment_Homepage.ACTION_START")
            intent.putExtra("gopholo",getpholos)
            startActivity(intent)
        }

        val dbHelper = MyDatabaseHelper(this, "Publiccomments.db",23)
        val db = dbHelper.writableDatabase
        val cursor = db.query("Synopsis",null,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do {
                val s_syno = cursor.getString(cursor.getColumnIndex("S_synopsis"))

                val s_syid = cursor.getInt(cursor.getColumnIndex("id"))
                if (s_syid == getsynoid){
                    sytext.text = s_syno
                }
            }while (cursor.moveToNext())
        }

        cursor.close()
    }
}