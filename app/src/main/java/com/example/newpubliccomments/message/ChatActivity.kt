package com.example.newpubliccomments.message

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.newpubliccomments.BaseActivity
import com.example.newpubliccomments.R
import com.example.newpubliccomments.databinding.AvtivityChatBinding
import com.example.newpubliccomments.tool.StatusBar
import io.rong.imkit.fragment.ConversationFragment


class ChatActivity: BaseActivity(){

    private var binding: AvtivityChatBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.avtivity_chat)

        //        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
//        设置状态栏图标颜色
        StatusBar().statusBarTextColor(this, true)

        val conversationFragment = ConversationFragment()
        supportFragmentManager.beginTransaction().replace(R.id.FrameLayout, conversationFragment).commit()

        val name = intent.data!!.getQueryParameter("title")
        binding?.data = name

        binding?.back?.setOnClickListener {
            onBackPressed()
        }

    }

}