package com.example.newpubliccomments.tool

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.newpubliccomments.BaseActivity

class StatusBar : AppCompatActivity() {

    fun statusBarColor(activity: Activity){

        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        activity.window.statusBarColor = Color.TRANSPARENT

    }

    fun statusBarTextColor(activity: Activity, dark: Boolean){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val decorView = activity.window.decorView
            if (decorView != null){
                var systemUiVisibility = decorView.systemUiVisibility
                if (dark){
                    systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }else{
                    systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }
                decorView.systemUiVisibility = systemUiVisibility
            }
        }
    }

}