package com.example.newpubliccomments

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.*
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : BaseActivity(), View.OnClickListener {
    //全局的objectId
    var objid = ""


    override fun onClick(v: View?) {
        var id = v!!.id
        when (id) {


        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Bmob.initialize(this,Constant.BMOB_APP_ID)
        setContentView(R.layout.activity_login)


        login_fanhui.setOnClickListener {
            val intent = Intent("com.example.newpubliccomment_Homepage.ACTION_START")
            startActivity(intent)
        }

        denglu.setOnClickListener {
            val intent = Intent(this,PholoActivity::class.java)
            startActivity(intent)
        }

        userzuce.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}