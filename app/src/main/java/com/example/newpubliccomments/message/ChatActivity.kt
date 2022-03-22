package com.example.newpubliccomments.message

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.FragmentActivity
import com.example.newpubliccomments.BaseActivity
import com.example.newpubliccomments.R
import io.rong.imkit.fragment.ConversationFragment


class ChatActivity: BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.avtivity_chat)

        val conversationFragment = ConversationFragment()
        supportFragmentManager.beginTransaction().replace(R.id.FrameLayout, conversationFragment).commit()

    }

}