package com.example.newpubliccomments.message

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.example.newpubliccomments.R
import com.example.newpubliccomments.databinding.FragmentNewsBinding
import com.example.newpubliccomments.tool.StatusBar
import io.rong.imkit.RongIM
import io.rong.imlib.model.Conversation

class ConverFragment : Fragment() {

    private var binding : FragmentNewsBinding? = null

    private var letterFragment: LetterFragment? = null

    companion object {
        fun newInstance(): ConverFragment {
            val args = Bundle()

            val fragment = ConverFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewsBinding.inflate(inflater, container, false)

        binding?.item?.setOnClickListener {
            RongIM.getInstance().startConversation(requireContext(), Conversation.ConversationType.PRIVATE, "100", "官方客服")
        }

        return binding!!.root
    }

    override fun onResume() {
        super.onResume()

//        letterFragment = LetterFragment.newInstance(requireContext())
//        childFragmentManager.beginTransaction().add(R.id.FrameLayout, letterFragment!!).commit()



    }


}