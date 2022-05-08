package com.example.newpubliccomments.collection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.bumptech.glide.Glide
import com.example.newpubliccomments.*
import com.example.newpubliccomments.business.Business
import com.example.newpubliccomments.databinding.ActivityCollectionBinding
import com.example.newpubliccomments.order.AllFragment
import com.example.newpubliccomments.order.CompleteFragment
import com.example.newpubliccomments.order.NoPaymentFragment
import com.example.newpubliccomments.order.PaymentFragment
import com.example.newpubliccomments.tool.StatusBar
import com.google.android.material.tabs.TabLayoutMediator


class CollectionActivity : BaseActivity(){



    private var binding: ActivityCollectionBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_collection)

        //        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
//        设置状态栏图标颜色
        StatusBar().statusBarTextColor(this, true)

        binding?.back?.setOnClickListener {

            onBackPressed()

        }

        binding?.viewpager?.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return 2
            }

            override fun createFragment(position: Int): Fragment {
                return getFragment(position)
            }

        }

        TabLayoutMediator(binding?.tab!!, binding?.viewpager!!){
                tab, position ->

            if (position == 0){
                tab.text = "商家收藏"
            }else{
                tab.text = "攻略收藏"
            }

        }.attach()

    }

    private fun getFragment(index: Int): Fragment{
        var xianpholo = intent.getStringExtra("delpholo").toString()
        return when(index){
            0 -> {
//                商家收藏界面
                BusinessCollection.newInstance(xianpholo)
            }
            else -> {
//                攻略收藏页面
                SynopsisCollection.newInstance(xianpholo)
            }
        }

    }

    fun start(context: Context, gopholo: String){
        val intent = Intent(context, CollectionActivity::class.java)
        intent.putExtra("delpholo",gopholo)
        context.startActivity(intent)
    }

}