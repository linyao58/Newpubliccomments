package com.example.newpubliccomments

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newpubliccomments.databinding.ActivityXianshiOrderMainBinding
import com.example.newpubliccomments.order.AllFragment
import com.example.newpubliccomments.order.CompleteFragment
import com.example.newpubliccomments.order.NoPaymentFragment
import com.example.newpubliccomments.order.PaymentFragment
import com.example.newpubliccomments.tool.StatusBar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_xianshi_order_main.*

class Fruitorder(val money:String, val imageId: Int, val zhuagntai: String,val aid : String,val bid : String,val pholo : String)

class FruitAdapterorder(val fruitList : ArrayList<Fruitorder>) :
    RecyclerView.Adapter<FruitAdapterorder.ViewHolder>(){
    inner class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val fruitImage : ImageView = view.findViewById(R.id.fruitImagexian)
        val fruitName : TextView = view.findViewById(R.id.fruitNamexian)
        val fruitzhuagn : TextView = view.findViewById(R.id.fruitzhifus)
        val fruitshangjia : TextView = view.findViewById(R.id.fruitshangjiahao)
        val fruitshangping : TextView = view.findViewById(R.id.fruitshangpinghao)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fruit_wu_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.fruitImage.setImageResource(fruit.imageId)
        holder.fruitName.text = fruit.money
        holder.fruitzhuagn.text = fruit.zhuagntai
        holder.fruitshangjia.text = fruit.aid
        holder.fruitshangping.text = fruit.bid
    }


    override fun getItemCount() = fruitList.size

}


class XianshiOrderMainActivity : AppCompatActivity() {

    private var binding: ActivityXianshiOrderMainBinding? = null

    private val fruitList = ArrayList<Fruitorder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_xianshi_order_main)

        //        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
//        设置状态栏图标颜色
        StatusBar().statusBarTextColor(this, true)

        var xianpholo = intent.getStringExtra("setpholo")

        binding?.viewpager?.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return 4
            }

            override fun createFragment(position: Int): Fragment {
                return getFragment(position)
            }

        }

        TabLayoutMediator(binding?.tab!!, binding?.viewpager!!){
            tab, position ->

                    if (position == 0){
                        tab.text = "全部"
                    }else if (position == 1){
                        tab.text = "未支付"
                    }else if (position == 2){
                        tab.text = "已支付"
                    }else{
                        tab.text = "已完成"
                    }

        }.attach()

        initFruits()

//        val layoutManager = LinearLayoutManager(this)
//
//        recyclerViewxian.layoutManager = layoutManager
//
//        val adapter = FruitAdapterorder(fruitList)
//
//        recyclerViewxian.adapter = adapter

        binding?.DelBackxiaxian?.setOnClickListener {
            onBackPressed()
        }

    }

    fun start(context: Context, gopholo: String) {
        val intent = Intent(context, XianshiOrderMainActivity::class.java)
        intent.putExtra("setpholo", gopholo)
        context.startActivity(intent)
    }

    private fun initFruits() {

        var xianpholo = intent.getStringExtra("setpholo").toString()

    }

    private fun getFragment(index: Int): Fragment{
        var xianpholo = intent.getStringExtra("setpholo").toString()
        return when(index){
            0 -> {
//                全部页面
                AllFragment.newInstance(xianpholo)
            }
            1 -> {
//                未支付页面
                NoPaymentFragment.newInstance(xianpholo)
            }
            2 -> {
//                已支付页面
                PaymentFragment.newInstance(xianpholo)
            }
            else -> {
//                已完成页面
                CompleteFragment.newInstance()
            }
        }

    }

}