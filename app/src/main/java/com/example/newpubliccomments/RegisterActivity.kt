package com.example.newpubliccomments

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_fanhui.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        userzuces.setOnClickListener {
            val dbHelper = MyDatabaseHelper(this, "Publiccomments.db",23)
            val db = dbHelper.writableDatabase
            var r_pholo = R_pholo.text.toString()
            var r_pass = R_pass.text.toString()

            val values1 = ContentValues().apply {
                put("userpholo",r_pholo)
                put("userpassword",r_pass)
            }
            if(r_pholo.length == 11){
                db.insert("User",null,values1)
                AlertDialog.Builder(this).apply {
                    setTitle("This is Dialog")
                    setMessage("注册成功，是否跳转到登录？")
                    setCancelable(false)
                    setPositiveButton("确定") { dialog, which ->
                        val intent = Intent(context, LoginActivity::class.java)
                        startActivity(intent)

                    }
                    setNegativeButton("取消") { dialog, which ->
                    }
                    show()
                }
            }else{
                Toast.makeText(this, "注册失败,请输入正确的手机号", Toast.LENGTH_SHORT).show()
            }

        }
    }
}