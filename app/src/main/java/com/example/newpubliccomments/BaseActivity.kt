package com.example.newpubliccomments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.rong.imkit.RongIM

open class BaseActivity : AppCompatActivity() {
    var mContext : Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        val appKey = "82hegw5u8ewyx"
        RongIM.init(this.applicationContext, appKey)

    }

//    Toast方法
    fun showToast(context: Context,content: String){
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
    }

}