package com.example.newpubliccomments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import android.widget.Toast
//import cn.bmob.v3.Bmob
//import cn.bmob.v3.BmobQuery
//import cn.bmob.v3.BmobSMS
//import cn.bmob.v3.BmobUser
//import cn.bmob.v3.exception.BmobException
//import cn.bmob.v3.listener.*
import kotlinx.android.synthetic.main.activity_verification_code.*

class VerificationCodeActivity : BaseActivity() {

    var getpholos = ""
    var yanzhens = ""

    /**
     * bmob 查询数据列表
     */
    /*private fun queryObjects() {
        var pholo = getpholos

        var bmobQuery: BmobQuery<Publicuser> = BmobQuery()
        bmobQuery.findObjects(object : FindListener<Publicuser>() {
            override fun done(posts: MutableList<Publicuser>?, ex: BmobException?) {

                if (ex == null) {
                    Toast.makeText(mContext, "查询成功", Toast.LENGTH_LONG).show()
                    if (posts != null) {
                        for (post: Publicuser in posts) {
                            if (post.P_username == pholo){

                                signUp()
                            }
                        }
                    }else{
                        var public = Publicuser()
                        public.P_username = pholo
                        public.P_password = "123456000"
                        public.save(object : SaveListener<String>() {
                            override fun done(objectId: String?, ex: BmobException?) {
                                if (ex == null) {
                                    Toast.makeText(mContext, "新增数据成功：$objectId", Toast.LENGTH_LONG).show()
                                } else {
                                    Log.e("CREATE", "新增数据失败：" + ex.message)
                                }
                            }
                        })
                    }
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()

                    ex.message?.let { Log.e("xianshi", it) }
                }
            }


        })
    }*/



    private fun signUp() {
        var pholo = getpholos
        var code = edtyan.text.toString()
//        BmobSMS.verifySmsCode(pholo,code,object : UpdateListener(){
//            override fun done(ex: BmobException?) {
//                if (ex == null) {
//                    Toast.makeText(mContext, "验证成功", Toast.LENGTH_LONG).show()
//                    var intent = Intent(this@VerificationCodeActivity,Homepage::class.java)
//                    intent.putExtra("gopholo",pholo)
//                    startActivity(intent)
//
//
//                } else {
//                    Toast.makeText(mContext, "验证失败：$ex.message", Toast.LENGTH_LONG).show()
//                }
//            }
//        })
    }

    private fun sendCode() {

        /**
         * bmob发送验证码
         */
//        BmobSMS.requestSMSCode(getpholos, "", object : QueryListener<Int>() {
//            override fun done(smsId: Int?, ex: BmobException?) {
//                if (ex == null) {
//                    Toast.makeText(mContext, "发送成功：$smsId", Toast.LENGTH_LONG).show()
//                } else {
//                    Toast.makeText(mContext, "发送成失败：$ex.message", Toast.LENGTH_LONG).show()
//                }
//            }
//        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_code)
//        Bmob.initialize(this,Constant.BMOB_APP_ID)

        getpholos = intent.getStringExtra("pholos").toString()
        val xianshipholos : TextView = findViewById(R.id.textpholoss)
        xianshipholos.text = getpholos
        yanzhens = edtyan.text.toString()
        img_backss.setOnClickListener {
            val intent = Intent(this, PholoActivity::class.java)
            startActivity(intent)
        }

        huoquyan.setOnClickListener {
            sendCode()
        }

        but_nexttows.setOnClickListener {
            signUp()
        }

        mimasss.setOnClickListener {
            val intent = Intent(this,PasswordActivity::class.java)
            intent.putExtra("pholos",getpholos)
            startActivity(intent)
        }

    }
}