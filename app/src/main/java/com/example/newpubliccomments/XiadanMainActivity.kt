package com.example.newpubliccomments

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import com.bumptech.glide.Glide
import com.example.newpubliccomments.commodity.Commodity
import com.example.newpubliccomments.databinding.ActivityXiadanMainBinding
import com.example.newpubliccomments.order.Order
import com.example.newpubliccomments.tool.StatusBar
import kotlinx.android.synthetic.main.activity_order_main.*
import kotlinx.android.synthetic.main.activity_xiadan_main.*

class XiadanMainActivity : AppCompatActivity() {

    private var binding: ActivityXiadanMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_xiadan_main)

        //        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
//        设置状态栏图标颜色
        StatusBar().statusBarTextColor(this, true)

        var bundle = intent.extras
        var getpholos = bundle?.getString("gopholo").toString()
        var shangjiaids = bundle?.getString("shangjiaid").toString()
        var shangpings = bundle?.getString("shangping").toString()

        binding?.DelBackxiadan?.setOnClickListener {
            onBackPressed()
        }

        binding?.xiapholo?.text = getpholos

        val bmobQuery = BmobQuery<Commodity>()
        bmobQuery.getObject(shangpings, object : QueryListener<Commodity>(){
            override fun done(p0: Commodity?, p1: BmobException?) {
                if (p1 == null){
                    if (p0 != null){
                        binding?.xiashang?.text = p0.commodityname
                        Glide.with(this@XiadanMainActivity).load(p0.avatar).into(binding?.fruitImage!!)
                        binding?.xiamoney?.text = p0.price
                        binding?.xiazhongjia?.text = p0.price
                        binding?.fumoney?.text = p0.price
                    }
                }else{
                    Toast.makeText(this@XiadanMainActivity, "查询失败", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "done123456789: ${p1?.message}, ${p1?.errorCode}")
                }
            }

        })

        binding?.shangchuandingdan?.setOnClickListener {

            var bundle = intent.extras
            var getpholos = bundle?.getString("gopholo").toString()
            var shangjiaids = bundle?.getString("shangjiaid").toString()
            var shangpings = bundle?.getString("shangping").toString()

            var bundless = Bundle()
            bundless.putString("gopholo",getpholos)
            bundless.putString("shangjiaid",shangjiaids)
            bundless.putString("shangping",shangpings)

            val bmob = BmobQuery<Commodity>()
            bmob.getObject(shangpings, object : QueryListener<Commodity>(){
                override fun done(p0: Commodity?, p1: BmobException?) {
                    if (p1 == null){
                        if (p0 != null){
                            val order = Order()
                            order.avatar = p0.avatar
                            order.businessname = p0.businessname
                            order.commodityname = p0.commodityname
                            order.price = p0.price
                            order.number = "1"
//                            1:未支付 2：已支付 3：已完成
                            order.state = "1"
                            order.businessid = p0.businessid
                            order.commodityid = p0.objectId
                            order.save(object : SaveListener<String>(){
                                override fun done(p0: String?, p1: BmobException?) {
                                    if (p1 == null){

                                        bundless?.putString("orderId", p0)

                                        val intent = Intent(this@XiadanMainActivity,ZhifuMainActivity::class.java)
                                        intent.putExtras(bundless)
                                        startActivity(intent)

                                        Toast.makeText(this@XiadanMainActivity, "下单成功", Toast.LENGTH_SHORT).show()
                                    }else{
                                        Toast.makeText(this@XiadanMainActivity, "下单失败", Toast.LENGTH_SHORT).show()
                                        Log.e("TAG", "done123456789: ${p1?.message}, ${p1?.errorCode}")
                                    }
                                }

                            })
                        }
                    }else{
                        Toast.makeText(this@XiadanMainActivity, "查询失败", Toast.LENGTH_SHORT).show()
                        Log.e("TAG", "done123456789: ${p1?.message}, ${p1?.errorCode}")
                    }
                }

            })

        }

    }
}