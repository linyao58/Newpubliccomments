package com.example.newpubliccomments

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_order_main.*
import kotlinx.android.synthetic.main.activity_xiadan_main.*

class XiadanMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xiadan_main)

        var bundle = intent.extras
        var getpholos = bundle?.getString("gopholo").toString()
        var shangjiaids = bundle?.getString("shangjiaid").toString()
        var shangpings = bundle?.getString("shangping").toString()

        Del_backxiadan.setOnClickListener {
            var bundlex = Bundle()
            bundlex.putString("gopholo",getpholos)
            bundlex.putString("shangjiaid",shangjiaids)
            bundlex.putString("shangping",shangpings)
            val intent = Intent(this,OrderMainActivity::class.java)
            intent.putExtras(bundlex)
            startActivity(intent)
        }

        xiapholo.text = getpholos

        val dbHelper = MyDatabaseHelper(this, "Publiccomments.db",23)
        val db = dbHelper.writableDatabase
        val cursor = db.query("Business",null,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do {
                val b_name = cursor.getString(cursor.getColumnIndex("B_name"))

                val b_id = cursor.getInt(cursor.getColumnIndex("id")).toString()
                if (b_id == shangjiaids){
                    xiashang.text = b_name
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
                        xiadanshang.text = c_name
                        xiamoney.text = c_money
                        xiazhongjia.text = c_money
                        fumoney.text = c_money
                    }
                }
            }while (cursors.moveToNext())
        }

        cursors.close()

        shangchuandingdan.setOnClickListener {



            var getpholoxia = getpholos
            var shangjiaidxia = shangjiaids.toInt()
            var shangpingxia = shangpings.toInt()

            var bundless = Bundle()
            bundless.putString("gopholo",getpholos)
            bundless.putString("shangjiaid",shangjiaids)
            bundless.putString("shangping",shangpings)

            val dbHelper = MyDatabaseHelper(this, "Publiccomments.db",23)
            val db = dbHelper.writableDatabase
            val values1 = ContentValues().apply {
                put("B_id",shangjiaidxia)
                put("C_id",shangpingxia)
                put("O_pholo",getpholoxia)
                put("O_state","未支付")
            }
            db.insert("Orders",null,values1)

            val intent = Intent(this,ZhifuMainActivity::class.java)
            intent.putExtras(bundless)
            startActivity(intent)
        }

    }
}