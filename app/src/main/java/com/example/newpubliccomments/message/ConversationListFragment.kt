package com.example.newpubliccomments.message

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.newpubliccomments.databinding.FragmentNewsBinding

class ConversationListFragment : Fragment() {

    private var binding : FragmentNewsBinding? = null

    companion object {
        fun newInstance(): ConversationListFragment {
            val args = Bundle()

            val fragment = ConversationListFragment()
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

        return binding!!.root
    }


}