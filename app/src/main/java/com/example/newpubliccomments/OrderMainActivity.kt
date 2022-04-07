package com.example.newpubliccomments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import com.bumptech.glide.Glide
import com.example.newpubliccomments.commodity.Commodity
import com.example.newpubliccomments.databinding.ActivityOrderMainBinding
import com.example.newpubliccomments.tool.StatusBar
import kotlinx.android.synthetic.main.activity_order_main.*

class OrderMainActivity : AppCompatActivity() {

    private var binding: ActivityOrderMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_main)

        //        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
//        设置状态栏图标颜色
        StatusBar().statusBarTextColor(this, true)

        var bundle = intent.extras
        var getpholos = bundle?.getString("gopholo").toString()
        var shangjiaids = bundle?.getString("shangjiaid").toString()
        var shangpings = bundle?.getString("shangping").toString()

        var bundles = Bundle()
        bundles.putString("gopholo",getpholos)
        bundles.putString("data_id",shangjiaids)

        Del_backshang.setOnClickListener {
            onBackPressed()
        }

        val bmobQuery = BmobQuery<Commodity>()
        bmobQuery.getObject(shangpings, object : QueryListener<Commodity>(){
            override fun done(p0: Commodity?, p1: BmobException?) {
                if (p1 == null){
                    if (p0 != null){

                        binding?.xiangjiaxinxi?.text = p0.businessname
                        Glide.with(this@OrderMainActivity).load(p0.avatar).into(binding?.shangpingtubian!!)
                        binding?.jiage?.text = p0.price

                    }
                }else{
                    Toast.makeText(this@OrderMainActivity, "查询失败", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "done123456789: ${p1?.message}, ${p1?.errorCode}")
                }
            }

        })

        xiadan.setOnClickListener {
            var bundless = Bundle()
            bundless.putString("gopholo",getpholos)
            bundless.putString("shangjiaid",shangjiaids)
            bundless.putString("shangping",shangpings)

            val intent = Intent(this,XiadanMainActivity::class.java)
            intent.putExtras(bundless)
            startActivity(intent)
        }

    }


}