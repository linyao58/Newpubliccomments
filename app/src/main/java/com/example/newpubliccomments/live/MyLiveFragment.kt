package com.example.newpubliccomments.live

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import com.bumptech.glide.Glide
import com.example.newpubliccomments.R
import com.example.newpubliccomments.databinding.FragmentBusinessCollectionBinding
import com.example.newpubliccomments.follow.Follow
import com.example.newpubliccomments.share.Evaluates
import com.example.newpubliccomments.signregister.PublicMyUser

class FruitMyLive(val name:String, val image: String, val profile: String,val aid : String,var pholo : String, var grade: Float)

class FruitAdapterMyLive(val fruitList: List<FruitMyLive>) :
    RecyclerView.Adapter<FruitAdapterMyLive.ViewHolder>(){

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val fruitImage : ImageView = view.findViewById(R.id.fruitImage)
        val fruitName : TextView = view.findViewById(R.id.fruitName)
        val fruitProfile : TextView = view.findViewById(R.id.fruitProfile)
        val fruitGrade : RatingBar = view.findViewById(R.id.rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitAdapterMyLive.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fruit_towitem,parent,false)

        val viewHolder = ViewHolder(view)

//            商家详情
//        viewHolder.itemView.setOnClickListener {
//            val position = viewHolder.adapterPosition
//            val fruit = fruitList[position]
//
//            val f_id = fruit.aid
//            var f_pholo = fruit.pholo
//
//            var bundle = Bundle()
//            bundle.putString("gopholo",f_pholo)
//            bundle.putString("data_id",f_id)
//
//            val intent = Intent(parent.context, BusinessMainActivity::class.java)
//            intent.putExtras(bundle)
//            parent.context.startActivity(intent)
//        }
//
////            商家详情
//        viewHolder.fruitImage.setOnClickListener {
//            val position = viewHolder.adapterPosition
//            val fruit = fruitList[position]
//
//            val f_id = fruit.aid
//            var f_pholo = fruit.pholo
//
//            var bundle = Bundle()
//            bundle.putString("gopholo",f_pholo)
//            bundle.putString("data_id",f_id)
//            //获取到该好友的id
//            //var send_id = fruit.aid
//            //跳转到好友信息页面
//            val intent = Intent(parent.context, BusinessMainActivity::class.java)
//
//            intent.putExtras(bundle)
//            //intent.putExtra("data_id", f_id)
//            //把该好友的id传输到好友信息页面
//            //intent.putExtra("touxiang_data", send_id)
//            parent.context.startActivity(intent)
//        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: FruitAdapterMyLive.ViewHolder, position: Int) {
        val fruit = fruitList[position]
        Glide.with(holder.itemView.context).load(fruit.image).into(holder.fruitImage)
        holder.fruitName.text = fruit.name
        holder.fruitProfile.text = fruit.profile
        holder.fruitGrade.rating = fruit.grade
    }

    override fun getItemCount() = fruitList.size

}

class MyLiveFragment: Fragment() {

    private var binding: FragmentBusinessCollectionBinding? = null

    private val fruitList = ArrayList<FruitMyLive>()

    companion object{
        fun newInstance(pholo: String): MyLiveFragment {
            val args = Bundle()
            val fragment = MyLiveFragment()
            args.putString("pholo", pholo)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBusinessCollectionBinding.inflate(inflater, container, false)

        initFruits()

        return binding?.root

    }

    private fun initFruits(){

        var gopholo = arguments?.getString("pholo")

        val bmobQuery = BmobQuery<Live>()
        bmobQuery.findObjects(object : FindListener<Live>() {
            override fun done(p0: MutableList<Live>?, p1: BmobException?) {
                if (p1 == null) {
                    if (p0 != null) {
                        p0.forEach {
                            if (it.phone == gopholo && it.live == "true") {
                                val colle = BmobQuery<Evaluates>()
                                colle.getObject(it.synopsisId, object : QueryListener<Evaluates>() {
                                    override fun done(p0: Evaluates?, p1: BmobException?) {
                                        if (p1 == null) {
                                            if (p0 != null) {
                                                var title = p0.phone
                                                var bavatar = p0.photo
                                                var introduce = p0.message
                                                var id = p0.objectId
                                                var grade = 4

                                                fruitList.add(
                                                    FruitMyLive(
                                                        title,
                                                        bavatar,
                                                        introduce,
                                                        id,
                                                        gopholo!!,
                                                        grade.toFloat()
                                                    )
                                                )

                                                val layoutManager = LinearLayoutManager(requireContext())
                                                //线性布局
                                                binding?.recyclerView?.layoutManager = layoutManager
                                                //完成适配器配置
                                                val adapter = FruitAdapterMyLive(fruitList)
                                                binding?.recyclerView?.adapter = adapter

                                            }
                                        } else {
                                            Toast.makeText(
                                                requireContext(),
                                                "查询失败",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }

                                })
                            }

                        }


                    }

//                    Toast.makeText(this@DeliciousfoodActivity, "查询成功${p0?.get(0)?.bavatar}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "查询失败", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "done123456789: ${p1?.message}, ${p1?.errorCode}")
                }
            }

        })

    }

}