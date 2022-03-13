package com.example.newpubliccomments

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.example.newpubliccomments.tool.StatusBar
//import cn.bmob.v3.Bmob
//import cn.bmob.v3.BmobQuery
//import cn.bmob.v3.exception.BmobException
//import cn.bmob.v3.listener.FindListener
import java.util.*

class MainActivity : BaseActivity() {
    var ph = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//      使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)

        StatusBar().statusBarTextColor(this, true)

//        Bmob.initialize(this,Constant.BMOB_APP_ID)

        //queryObjects()

        var timer = Timer()
        class MyTimerTask(): TimerTask() {
            override fun run() {
                //跳转到登录页面
                if (ph != ""){
                    val intent = Intent(this@MainActivity,Homepage::class.java)
                    intent.putExtra("gopholo",ph)
                    startActivity(intent)
                }else{
                    val intent = Intent(this@MainActivity,Homepage::class.java)
                    intent.putExtra("gopholo",ph)
                    startActivity(intent)

                    //跳转到创建数据库的页面
                    /*val intent = Intent(this@MainActivity,SystemActivity::class.java)
                    startActivity(intent)*/
                }

            }
        }
        //该页面延迟5秒
        var timerTask = MyTimerTask()
        timer.schedule(timerTask,2000)
    }

    /**
     * bmob 查询数据列表
     */
   /* private fun queryObjects() {

        var bmobQuery: BmobQuery<GameScore> = BmobQuery()
        bmobQuery.findObjects(object : FindListener<GameScore>() {
            override fun done(posts: MutableList<GameScore>?, ex: BmobException?) {

                if (ex == null) {
                    Toast.makeText(mContext, "查询成功", Toast.LENGTH_LONG).show()
                    if (posts != null) {
                        for (post: GameScore in posts) {
                            if (post.isPay == true){

                                ph = post.playerName.toString()
                                Log.e("xianshi",ph)
                                break

                            }
                        }
                    }
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                    ex.message?.let { Log.e("xianshi", it) }
                }
            }
        })
    }*/


}