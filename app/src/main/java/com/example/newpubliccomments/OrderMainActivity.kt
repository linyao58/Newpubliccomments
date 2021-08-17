package com.example.newpubliccomments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_order_main.*

class OrderMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_main)
        var bundle = intent.extras
        var getpholos = bundle?.getString("gopholo").toString()
        var shangjiaids = bundle?.getString("shangjiaid").toString()
        var shangpings = bundle?.getString("shangping").toString()

        var bundles = Bundle()
        bundles.putString("gopholo",getpholos)
        bundles.putString("data_id",shangjiaids)

        Del_backshang.setOnClickListener {
            val intent = Intent(this,BusinessMainActivity::class.java)
            intent.putExtras(bundles)
            startActivity(intent)
        }

        val dbHelper = MyDatabaseHelper(this, "Publiccomments.db",23)
        val db = dbHelper.writableDatabase
        val cursor = db.query("Business",null,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do {
                val b_name = cursor.getString(cursor.getColumnIndex("B_name"))

                val b_id = cursor.getInt(cursor.getColumnIndex("id")).toString()
                if (b_id == shangjiaids){
                    xiangjiaxinxi.text = b_name
                }
            }while (cursor.moveToNext())
        }

        cursor.close()

        val cursors = db.query("Commodity",null,null,null,null,null,null)
        if(cursors.moveToFirst()){
            do {
                val c_name = cursors.getString(cursors.getColumnIndex("C_name"))
                val c_money = cursors.getString(cursors.getColumnIndex("C_profile"))
                val b_id = cursors.getInt(cursors.getColumnIndex("B_id")).toString()
                val C_id = cursors.getInt(cursors.getColumnIndex("id")).toString()
                if (b_id == shangjiaids){
                    if (C_id == shangpings){

                        Log.e("显示",c_name)
                        shangpingxingxi.text = c_name
                        jiage.text = c_money
                    }
                }
            }while (cursors.moveToNext())
        }

        cursors.close()

        xiadan.setOnClickListener {
            var bundless = Bundle()
            bundless.putString("gopholo",getpholos)
            bundless.putString("shangjiaid",shangjiaids)
            bundless.putString("shangping",shangpings)

            val intent = Intent(this,XiadanMainActivity::class.java)
            intent.putExtras(bundless)
            startActivity(intent)
        }

    }


}