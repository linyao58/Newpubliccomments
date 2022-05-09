package com.example.newpubliccomments.live

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newpubliccomments.BaseActivity
import com.example.newpubliccomments.R
import com.example.newpubliccomments.databinding.ActivityLiveorfollowBinding
import com.example.newpubliccomments.tool.StatusBar
import com.google.android.material.tabs.TabLayoutMediator


class LiveActivity: BaseActivity() {

    private var binding: ActivityLiveorfollowBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_liveorfollow)

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
                tab.text = "点赞我的攻略"
            }else{
                tab.text = "我点赞的攻略"
            }

        }.attach()

    }

    private fun getFragment(index: Int): Fragment {
        var xianpholo = intent.getStringExtra("phone").toString()
        return when(index){
            0 -> {
//                点赞我的攻略页面
                LiveMyFragment.newInstance(xianpholo)
            }
            else -> {
                //                我点赞攻略界面
                MyLiveFragment.newInstance(xianpholo)
            }
        }

    }

    fun start(context: Context, phone: String){
        val intent = Intent(context, LiveActivity::class.java)
        intent.putExtra("phone", phone)
        context.startActivity(intent)
    }

}