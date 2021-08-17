package com.example.newpubliccomments

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_xiadan_main.*
import kotlinx.android.synthetic.main.activity_zhifu_main.*

class ZhifuMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zhifu_main)

        var bundle = intent.extras
        var getpholos = bundle?.getString("gopholo").toString()
        var shangjiaids = bundle?.getString("shangjiaid").toString()
        var shangpings = bundle?.getString("shangping").toString()

        Del_backzhifu.setOnClickListener {
            var bundlex = Bundle()
            bundlex.putString("gopholo",getpholos)
            bundlex.putString("shangjiaid",shangjiaids)
            bundlex.putString("shangping",shangpings)
            val intent = Intent(this,XiadanMainActivity::class.java)
            intent.putExtras(bundlex)
            startActivity(intent)
        }

        val dbHelper = MyDatabaseHelper(this, "Publiccomments.db",23)
        val db = dbHelper.writableDatabase
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
                        zhifumoney.text = c_money
                        zhifushangping.text = c_name
                    }
                }
            }while (cursors.moveToNext())
        }

        cursors.close()

        quedingzhifu.setOnClickListener {
            val dbHelper = MyDatabaseHelper(this, "Publiccomments.db",23)
            val db = dbHelper.writableDatabase

            var getpholoxia = getpholos
            var getshang = shangpings
            val values12 = ContentValues()
            values12.put("O_state","已支付")
            db.update("Orders", values12, "C_id = ?", arrayOf(getshang))

            AlertDialog.Builder(this).apply {
                setTitle("This is Dialog")
                setMessage("支付成功，是否返回首页？")
                setCancelable(false)
                setPositiveButton("确定") { dialog, which ->
                    val intent = Intent("com.example.newpubliccomment_Homepage.ACTION_START")
                    intent.putExtra("gopholo",getpholoxia)
                    startActivity(intent)

                }
                setNegativeButton("取消") { dialog, which ->
                }
                show()
            }
        }
    }
}