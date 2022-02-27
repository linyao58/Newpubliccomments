package com.example.newpubliccomments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
//import cn.bmob.v3.Bmob
//import cn.bmob.v3.BmobQuery
//import cn.bmob.v3.datatype.BmobFile
//import cn.bmob.v3.exception.BmobException
//import cn.bmob.v3.listener.FindListener
//import cn.bmob.v3.listener.SaveListener
//import cn.bmob.v3.listener.UploadFileListener
import com.bumptech.glide.load.data.BufferedOutputStream
import kotlinx.android.synthetic.main.activity_peplo.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PeploActivity : BaseActivity() {

    val fromAlbum = 2
    var path = ""

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peplo)
//        Bmob.initialize(this, Constant.BMOB_APP_ID)


    }


}