package com.example.newpubliccomments

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.example.newpubliccomments.signregister.PublicMyUser
import com.example.newpubliccomments.tool.StatusBar
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
        StatusBar().statusBarTextColor(this, true)

        register_fanhui.setOnClickListener {
            onBackPressed()
        }

        userzuces.setOnClickListener {
            var r_pholo = R_pholo.text.toString()
            var r_pass = R_pass.text.toString()


            if(r_pholo.length == 11 && r_pass.length >= 6){

                val user = PublicMyUser()
                user.phone = r_pholo
                user.password = r_pass
                user.name = "用户${r_pholo}"
                user.avatar = "https://newpubliccomments-guangzhou.oss-cn-beijing.aliyuncs.com/userAvatar.jpeg"

                user.save(object : SaveListener<String>(){
                    override fun done(p0: String?, p1: BmobException?) {
                        if (p1 == null){

                            AlertDialog.Builder(this@RegisterActivity).apply {
                                setTitle("注册")
                                setMessage("注册成功，是否跳转到登录？")
                                setCancelable(false)
                                setPositiveButton("确定") { dialog, which ->
                                    val intent = Intent(context, LoginActivity::class.java)
                                    startActivity(intent)

                                }
                                setNegativeButton("取消") { dialog, which ->
                                }
                                show()
                            }

                            Toast.makeText(this@RegisterActivity, "注册成功", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this@RegisterActivity, "创建数据失败:${p1.message}", Toast.LENGTH_SHORT).show()
                            Log.e("TAG", "done1356: ${p1.message}")
                        }
                    }

                })

            }else{
                Toast.makeText(this, "注册失败,请输入正确的手机号", Toast.LENGTH_SHORT).show()
            }

        }
    }
}