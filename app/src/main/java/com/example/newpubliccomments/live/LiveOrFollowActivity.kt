package com.example.newpubliccomments.live

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newpubliccomments.BaseActivity
import com.example.newpubliccomments.Homepage
import com.example.newpubliccomments.R
import com.example.newpubliccomments.collection.BusinessCollection
import com.example.newpubliccomments.collection.SynopsisCollection
import com.example.newpubliccomments.databinding.ActivityLiveorfollowBinding
import com.example.newpubliccomments.tool.StatusBar
import com.google.android.material.tabs.TabLayoutMediator

class LiveOrFollowActivity: BaseActivity() {

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
                tab.text = "关注我的"
            }else{
                tab.text = "我关注的"
            }

        }.attach()

    }

    private fun getFragment(index: Int): Fragment{
        var xianpholo = intent.getStringExtra("phone").toString()
        return when(index){
            0 -> {
//                关注我的页面
                FollowMyFragment.newInstance(xianpholo)
            }
            else -> {
                //                我的关注界面
                MyFollowFragment.newInstance(xianpholo)
            }
        }

    }

    fun start(context: Context, phone: String){
        val intent = Intent(context, LiveOrFollowActivity::class.java)
        intent.putExtra("phone", phone)
        context.startActivity(intent)
    }

}