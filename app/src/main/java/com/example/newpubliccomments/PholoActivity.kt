package com.example.newpubliccomments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.newpubliccomments.tool.StatusBar
//import cn.bmob.v3.Bmob
//import cn.bmob.v3.BmobQuery
//import cn.bmob.v3.exception.BmobException
//import cn.bmob.v3.listener.FindListener
import kotlinx.android.synthetic.main.activity_password.*
import kotlinx.android.synthetic.main.activity_pholo.*

class PholoActivity : AppCompatActivity() {
    var pholo = ""
    var objid = ""

    /**
     * bmob 查询数据列表
     */
    /*private fun queryObjects() {
        pholo = Pholo.text.toString()
        var bmobQuery: BmobQuery<GameScore> = BmobQuery()
        bmobQuery.findObjects(object : FindListener<GameScore>() {
            override fun done(posts: MutableList<GameScore>?, ex: BmobException?) {

                if (ex == null) {
                    Toast.makeText(mContext, "查询成功", Toast.LENGTH_LONG).show()
                    if (posts != null) {
                        for (post: GameScore in posts) {

                            if (pholo == post.playerName ){

                                objid = post.objectId.toString()
                                Log.e("xianshi",objid)
                                break
                            }
                        }
                    }
                } else {
                    Toast.makeText(mContext, ex.Messages, Toast.LENGTH_LONG).show()
                }
            }
        })
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pholo)
//        Bmob.initialize(this,Constant.BMOB_APP_ID)

        //        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
        StatusBar().statusBarTextColor(this, true)

        remember1.isChecked = false
        but_next.isEnabled = false
        remember1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                but_next.isEnabled = true
                //queryObjects()
            } else {
                but_next.isEnabled = false
            }
        }

        img_back.setOnClickListener {
            onBackPressed()
        }

        but_next.setOnClickListener {
           pholo = Pholo.text.toString()
            /*var bundle = Bundle()
            bundle.putString("pholos",pholo)
            bundle.putString("obj",objid)*/
            if(pholo.length == 11){
                val intent = Intent(this,PasswordActivity::class.java)
                intent.putExtra("pholos",pholo)
                //intent.putExtras(bundle)
                startActivity(intent)
            }

            if(pholo.length != 11 ){

                Pholo.setError("手机号长度有误，请输入11个字符")
            }


        }




    }


}