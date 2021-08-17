package com.example.newpubliccomments

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_set.*

class setActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)


        var set_pholo = intent.getStringExtra("setpholo").toString()
        set_back.setOnClickListener {
            val intent = Intent("com.example.newpubliccomment_Homepage.ACTION_START")
            intent.putExtra("gopholo",set_pholo)
            startActivity(intent)
        }

        if (set_pholo == ""){
            set_degnlu.setOnClickListener {
                Toast.makeText(this,"账号未登录，请进行登录", Toast.LENGTH_SHORT).show()
            }
        }else{
            set_degnlu.setOnClickListener {
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}