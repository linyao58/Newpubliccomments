package com.example.newpubliccomments.message

import android.content.Context
import android.net.Uri
import android.os.Bundle
import io.rong.imkit.RongIM
import io.rong.imkit.fragment.ConversationListFragment
import io.rong.imlib.model.Conversation


class LetterFragment: ConversationListFragment() {

    companion object {
        fun newInstance(context: Context): LetterFragment {
            val args = Bundle()

            val uri = Uri.parse(
                "rong://" +
                        context.packageName
            ).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(
                    Conversation.ConversationType.PRIVATE.getName(),
                    "false"
                ) //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false") //群组
                .appendQueryParameter(
                    Conversation.ConversationType.PUBLIC_SERVICE.getName(),
                    "false"
                ) //公共服务号
                .appendQueryParameter(
                    Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(),
                    "false"
                ) //订阅号
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true") //系统
                .build()

            val fragment = LetterFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val supportedConversation: MutableMap<String, Boolean> =
            HashMap()
        supportedConversation[Conversation.ConversationType.PRIVATE.getName()] = false
        RongIM.getInstance().startConversationList(requireContext(), supportedConversation)

    }


}