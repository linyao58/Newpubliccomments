package com.example.newpubliccomments

import android.app.AlertDialog
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
import cn.bmob.v3.listener.UpdateListener
import com.example.newpubliccomments.commodity.Commodity
import com.example.newpubliccomments.databinding.ActivityZhifuMainBinding
import com.example.newpubliccomments.order.Order
import com.example.newpubliccomments.tool.StatusBar
import kotlinx.android.synthetic.main.activity_xiadan_main.*
import kotlinx.android.synthetic.main.activity_zhifu_main.*

class ZhifuMainActivity : AppCompatActivity() {

    private var binding: ActivityZhifuMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_zhifu_main)

        //        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
//        设置状态栏图标颜色
        StatusBar().statusBarTextColor(this, true)

        var bundle = intent.extras
        var getpholos = bundle?.getString("gopholo").toString()
        var shangjiaids = bundle?.getString("shangjiaid").toString()
        var shangpings = bundle?.getString("shangping").toString()


        Del_backzhifu.setOnClickListener {
            onBackPressed()
        }

        val bmobQuery = BmobQuery<Commodity>()
        bmobQuery.getObject(shangpings, object : QueryListener<Commodity>(){
            override fun done(p0: Commodity?, p1: BmobException?) {
                if (p1 == null){
                    if (p0 != null){
                        binding?.zhifumoney?.text = p0.price
                        binding?.zhifushangping?.text = p0.commodityname
                    }
                }else{
                    Toast.makeText(this@ZhifuMainActivity, "查询失败", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "done123456789: ${p1?.message}, ${p1?.errorCode}")
                }
            }

        })

        binding?.quedingzhifu?.setOnClickListener {

            var orderId = bundle?.getString("orderId").toString()

            val order = Order()
            order.state = "2"
            order.update(orderId, object : UpdateListener(){
                override fun done(p0: BmobException?) {
                    if (p0 == null){

                        AlertDialog.Builder(this@ZhifuMainActivity).apply {
                            setTitle("订单支付")
                            setMessage("支付成功，是否返回首页？")
                            setCancelable(false)
                            setPositiveButton("确定") { dialog, which ->
                                val intent = Intent("com.example.newpubliccomment_Homepage.ACTION_START")
                                intent.putExtra("gopholo",getpholos)
                                startActivity(intent)

                            }
                            setNegativeButton("取消") { dialog, which ->
                            }
                            show()
                        }

                        Toast.makeText(this@ZhifuMainActivity, "支付成功", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@ZhifuMainActivity, "支付失败", Toast.LENGTH_SHORT).show()
                    }
                }

            })

        }
    }
}