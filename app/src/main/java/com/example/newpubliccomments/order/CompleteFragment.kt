package com.example.newpubliccomments.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newpubliccomments.databinding.FragmentAllBinding

class CompleteFragment: Fragment() {

    private var binding: FragmentAllBinding? = null

    companion object{
        fun newInstance(): CompleteFragment{
            val args = Bundle()
            val fragment = CompleteFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllBinding.inflate(inflater, container, false)

        return binding?.root
    }

}