package com.example.newpubliccomments

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.example.newpubliccomments.tool.StatusBar
//import cn.bmob.v3.Bmob
//import cn.bmob.v3.BmobQuery
//import cn.bmob.v3.BmobSMS
//import cn.bmob.v3.BmobUser
//import cn.bmob.v3.exception.BmobException
//import cn.bmob.v3.listener.*
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
//        Bmob.initialize(this,Constant.BMOB_APP_ID)
        setContentView(R.layout.activity_login)

//        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
        StatusBar().statusBarTextColor(this, false)

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