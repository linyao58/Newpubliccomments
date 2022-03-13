package com.example.newpubliccomments.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.example.newpubliccomments.databinding.ActivityCarBinding
import com.example.newpubliccomments.message.ConversationListFragment

class LocationFragment: Fragment() {

    private var binding: ActivityCarBinding? = null

    companion object {
        fun newInstance(): LocationFragment {
            val args = Bundle()

            val fragment = LocationFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ActivityCarBinding.inflate(inflater, container, false)

        return binding?.root

    }

    override fun onResume() {
        super.onResume()

        binding?.bmapView?.onResume()

    }

    override fun onPause() {
        super.onPause()

        binding?.bmapView?.onPause()

    }

    override fun onDestroy() {
        super.onDestroy()

        binding?.bmapView?.onDestroy()

    }

}