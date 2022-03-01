package com.example.newpubliccomments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.newpubliccomments.tool.StatusBar
import kotlinx.android.synthetic.main.activity_password.*

class PasswordActivity : AppCompatActivity() {

    var getpholo = ""

    var objid = ""

    var posword = ""



    /*private fun queryObjects() {
        var getpholos = intent.getStringExtra("pholos").toString()
        var textmima = edtmima.text.toString()
        var bmobQuery: BmobQuery<Publicuser> = BmobQuery()
        bmobQuery.findObjects(object : FindListener<Publicuser>() {
            override fun done(posts: MutableList<Publicuser>?, ex: BmobException?) {

                if (ex == null) {
                    Toast.makeText(mContext, "查询成功", Toast.LENGTH_LONG).show()
                    if (posts != null) {
                        for (post: Publicuser in posts) {

                            if (post.P_username == getpholos){

                                posword = post.P_password.toString()

                                if (textmima == posword){
                                    val intent = Intent("com.example.newpubliccomment_Homepage.ACTION_START")
                                    intent.putExtra("gopholo",getpholos)
                                    startActivity(intent)
                                }

                            }
                        }
                    }
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    private fun updateOne(objid : String) {

        val gameScore = GameScore()
        gameScore.isPay = true
        gameScore.update(objid, object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Log.i("bmob", "更新成功")
                } else {
                    Log.i("bmob", "更新失败：" + e.message + "," + e.errorCode)
                }
            }
        })
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        //Bmob.initialize(this,Constant.BMOB_APP_ID)

        //        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
        StatusBar().statusBarTextColor(this, true)

        var getpholos = intent.getStringExtra("pholos").toString()


        /*var bundle = intent.extras
        getpholo = bundle?.getString("pholos").toString()
        objid = bundle?.getString("obj").toString()
        Log.e("xianshi",objid)*/

        //getpholo = intent.getStringExtra("pholos").toString()
        val xianshipholo : TextView = findViewById(R.id.textpholos)
        xianshipholo.text = getpholos
        img_backs.setOnClickListener {
            val intent = Intent(this, PholoActivity::class.java)
            startActivity(intent)
        }
        but_nexttow.setOnClickListener {
            //updateOne(objid)
            //queryObjects()


            val dbHelper = MyDatabaseHelper(this, "Publiccomments.db",23)
            val db = dbHelper.writableDatabase
            val cursor = db.query("User",null,null,null,null,null,null)
            if(cursor.moveToFirst()){
                do {
                    val L_pholo = cursor.getString(cursor.getColumnIndex("userpholo"))
                    val L_password = cursor.getString(cursor.getColumnIndex("userpassword"))

                    var lgetpholos = intent.getStringExtra("pholos").toString()
                    var textmima = edtmima.text.toString()

                    if (L_pholo == lgetpholos && L_password == textmima){
                        Log.e("显示",L_password)
                        val intent = Intent("com.example.newpubliccomment_Homepage.ACTION_START")
                        intent.putExtra("gopholo",getpholos)
                        startActivity(intent)
                    }
                }while (cursor.moveToNext())
            }

            cursor.close()
        }

        yanzhengmas.setOnClickListener {
            val intent = Intent(this,VerificationCodeActivity::class.java)
            intent.putExtra("pholos",getpholo)
            startActivity(intent)
        }

    }
}