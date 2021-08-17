package com.example.newpubliccomments

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_system.*

class SystemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system)
        val dbHelper = MyDatabaseHelper(this, "Publiccomments.db",23)
        chuangjian.setOnClickListener {
            dbHelper.writableDatabase
        }
        shangchuan.setOnClickListener {
            val db = dbHelper.writableDatabase
            var b_business = B_business.text.toString()
            var b_prolife = B_prolife.text.toString()
            var b_shen = B_shen.text.toString()
            val values1 = ContentValues().apply {
                put("B_name",b_business)
                put("B_profile",b_prolife)
                put("B_shenhe",b_shen)

            }
            db.insert("Business",null,values1)
        }
        scshangping.setOnClickListener {
            val db = dbHelper.writableDatabase
            var c_commodity = C_commodity.text.toString()
            var c_profiles = C_profiles.text.toString()
            var c_ids = B_ids.text.toString()
            var int_c_ids = c_ids.toInt()
            var c_shen = C_shen.text.toString()
            val values2 = ContentValues().apply {
                put("C_name",c_commodity)
                put("C_profile",c_profiles)
                put("B_id",int_c_ids)
                put("C_shenhe",c_shen)
            }
            db.insert("Commodity",null,values2)
        }
    }
}