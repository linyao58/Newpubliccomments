package com.example.newpubliccomments.complaint

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import com.bumptech.glide.Glide
import com.example.newpubliccomments.BaseActivity
import com.example.newpubliccomments.Homepage
import com.example.newpubliccomments.R
import com.example.newpubliccomments.business.Business
import com.example.newpubliccomments.databinding.ActivityComplaintBinding
import com.example.newpubliccomments.tool.StatusBar

class ComplaintActivity : BaseActivity() {

    private var binding: ActivityComplaintBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_complaint)

        //        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
//        设置状态栏图标颜色
        StatusBar().statusBarTextColor(this, true)

        val businessId = intent.getStringExtra("businessId")

        binding?.back?.setOnClickListener {
            onBackPressed()
        }

        binding?.dianji?.setOnClickListener {

            val context = binding?.content?.text?.toString()?.trim()

            if (TextUtils.isEmpty(context)){
                Toast.makeText(this@ComplaintActivity, "请输入内容", Toast.LENGTH_SHORT).show()
            }else{

                val bmobQuery = BmobQuery<Business>()
                bmobQuery.getObject(businessId, object : QueryListener<Business>(){
                    override fun done(p0: Business?, p1: BmobException?) {
                        if (p1 == null){
                            if (p0 != null){
                                val complaint = Complaint()
                                complaint.businessid = p0.objectId
                                complaint.businessname = p0.title
                                complaint.content = context
                                complaint.save(object : SaveListener<String>(){
                                    override fun done(p0: String?, p1: BmobException?) {
                                        if (p1 == null){
                                            Toast.makeText(this@ComplaintActivity, "投诉成功", Toast.LENGTH_SHORT).show()
                                        }else{
                                            Toast.makeText(this@ComplaintActivity, "投诉失败", Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                })
                            }
                        }else{
                            Toast.makeText(this@ComplaintActivity, "查询失败", Toast.LENGTH_SHORT).show()
                            Log.e("TAG", "done123456789: ${p1?.message}, ${p1?.errorCode}")
                        }
                    }

                })

            }

        }

    }

    fun start(context: Context, businessId: String){
        val intent = Intent(context, ComplaintActivity::class.java)
        intent.putExtra("businessId", businessId)
        context.startActivity(intent)
    }

}