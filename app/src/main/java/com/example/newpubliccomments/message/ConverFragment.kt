package com.example.newpubliccomments.message

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.bumptech.glide.Glide
import com.example.newpubliccomments.FruitAdapters
import com.example.newpubliccomments.Fruits
import com.example.newpubliccomments.R
import com.example.newpubliccomments.SynopsisActivity
import com.example.newpubliccomments.databinding.FragmentNewsBinding
import com.example.newpubliccomments.share.Evaluates
import com.example.newpubliccomments.signregister.PublicMyUser
import com.example.newpubliccomments.tool.StatusBar
import de.hdodenhof.circleimageview.CircleImageView
import io.rong.imkit.RongIM
import io.rong.imlib.model.Conversation

class FruitsRong(val name:String, val avatar: String, val rongid: String)

class FruitAdaptersRong(val fruitList : List<FruitsRong>) :
    RecyclerView.Adapter<FruitAdaptersRong.ViewHolder>(){

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val avatar : CircleImageView = view.findViewById(R.id.avatar)
        val name : TextView = view.findViewById(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitAdaptersRong.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_ron,parent,false)
        val viewHolder = ViewHolder(view)

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]

            RongIM.getInstance().startConversation(it.context, Conversation.ConversationType.PRIVATE, fruit.rongid, fruit.name)

        }

        return viewHolder
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: FruitAdaptersRong.ViewHolder, position: Int) {
        val fruit = fruitList[position]

//        glide自带实现图片圆角方法
//        val roundedCorners = RoundedCorners(10)
//        val options = RequestOptions.bitmapTransform(roundedCorners)

        Glide.with(holder.itemView.context).load(fruit.avatar).into(holder.avatar)
        holder.name.text = fruit.name

    }

    override fun getItemCount() = fruitList.size

}

class ConverFragment : Fragment() {

    private var binding : FragmentNewsBinding? = null

    private var letterFragment: LetterFragment? = null

    private val fruitLists = ArrayList<FruitsRong>()

    companion object {
        fun newInstance(phone: String): ConverFragment {
            val args = Bundle()

            val fragment = ConverFragment()
            args.putString("phone", phone)
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

//        binding?.item?.setOnClickListener {
//            RongIM.getInstance().startConversation(requireContext(), Conversation.ConversationType.PRIVATE, "100", "官方客服")
//        }

        val phone = arguments?.getString("phone")

        val bmobQuery = BmobQuery<PublicMyUser>()
        bmobQuery.findObjects(object : FindListener<PublicMyUser>(){
            override fun done(p0: MutableList<PublicMyUser>?, p1: BmobException?) {
                if (p1 == null){
                    if (p0 != null){
                        p0.forEach {
                            if (it.phone == phone){
                                fruitLists.add(FruitsRong("文件管理助手", "https://newpubliccomments-guangzhou.oss-cn-beijing.aliyuncs.com/weixin.jpeg", it.rongid))
                            }else{
                                fruitLists.add(FruitsRong(it.name, it.avatar, it.rongid))
                            }
                        }

                        binding?.data = p0.size.toString()

                        val layoutManager = LinearLayoutManager(requireContext())
                        //线性布局
                        binding?.recyclerView?.layoutManager = layoutManager
                        //完成适配器配置
                        val adapter = FruitAdaptersRong(fruitLists)
                        binding?.recyclerView?.adapter = adapter

                    }
                }else{
                    Toast.makeText(requireContext(), "查询失败", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "done123456789: ${p1?.message}, ${p1?.errorCode}")
                }
            }

        })

        return binding!!.root
    }

    override fun onResume() {
        super.onResume()

//        letterFragment = LetterFragment.newInstance(requireContext())
//        childFragmentManager.beginTransaction().add(R.id.FrameLayout, letterFragment!!).commit()



    }


}