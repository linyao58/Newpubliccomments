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
import androidx.databinding.DataBindingUtil
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.UpdateListener
import com.bumptech.glide.Glide
//import cn.bmob.v3.Bmob
//import cn.bmob.v3.BmobQuery
//import cn.bmob.v3.datatype.BmobFile
//import cn.bmob.v3.exception.BmobException
//import cn.bmob.v3.listener.FindListener
//import cn.bmob.v3.listener.SaveListener
//import cn.bmob.v3.listener.UploadFileListener
import com.bumptech.glide.load.data.BufferedOutputStream
import com.example.newpubliccomments.databinding.ActivityPeploBinding
import com.example.newpubliccomments.signregister.PublicMyUser
import com.example.newpubliccomments.tool.StatusBar
import kotlinx.android.synthetic.main.activity_peplo.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PeploActivity : BaseActivity() {

    private var binding: ActivityPeploBinding? = null

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_peplo)

        //        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
//        设置状态栏图标颜色
        StatusBar().statusBarTextColor(this, true)

        val phone = intent.getStringExtra("phone").toString()

        var id: String = ""

        binding?.back?.setOnClickListener {
            onBackPressed()
        }

        val bmobQuery = BmobQuery<PublicMyUser>()
        bmobQuery.findObjects(object : FindListener<PublicMyUser>(){
            override fun done(p0: MutableList<PublicMyUser>?, p1: BmobException?) {
                if (p1 == null){
                    if (p0 != null){
                        p0.forEach {
                            if (it.phone == phone){
                                Glide.with(this@PeploActivity).load(it.avatar).into(binding?.avatar!!)
                                binding?.names = it.name
                                binding?.sexs = it.sex
                                binding?.phones = it.phone
                                binding?.mails = it.mailbox
                                id = it.objectId
                            }
                        }
                    }
                }else{
                    Toast.makeText(this@PeploActivity, "查询失败", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding?.update?.setOnClickListener {

            var name = binding?.name?.text.toString()
            var sex = binding?.sex?.text.toString()
            var phone = binding?.phone?.text.toString()
            var mail = binding?.mail?.text.toString()

            if (name.isNullOrEmpty()){
                Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show()
            } else if (sex.isNullOrEmpty()){
                Toast.makeText(this, "请输入性别", Toast.LENGTH_SHORT).show()
            } else if (phone.isNullOrEmpty()){
                Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show()
            }else if (mail.isNullOrEmpty()){
                Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show()
            }

            if (name.isNotEmpty() && sex.isNotEmpty() && phone.isNotEmpty() && mail.isNotEmpty()){
                val user = PublicMyUser()
                user.name = name
                user.sex = sex
                user.phone = phone
                user.mailbox = mail
                user.update(id, object : UpdateListener(){
                    override fun done(p0: BmobException?) {
                        if (p0 == null){
                            Toast.makeText(this@PeploActivity, "修改成功", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this@PeploActivity, "修改失败", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
            }



        }

    }


}