package com.example.newpubliccomments.order

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.bumptech.glide.Glide
import com.example.newpubliccomments.FruitAdaptershang
import com.example.newpubliccomments.Fruitshang
import com.example.newpubliccomments.OrderMainActivity
import com.example.newpubliccomments.R
import com.example.newpubliccomments.databinding.FragmentAllBinding

class FruitAll(val name:String, val image: String, val money: String,val aid : String,val bid : String,val pholo : String, val state: String, val fragmentManager: FragmentManager)

class FruitAdapterAll(val fruitList: ArrayList<FruitAll>) :
    RecyclerView.Adapter<FruitAdapterAll.ViewHolder>(){

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val fruitImage : ImageView = view.findViewById(R.id.fruitImage)
        val fruitName : TextView = view.findViewById(R.id.fruitName)
        val fruitmoney : TextView = view.findViewById(R.id.fruitmoney)
        val state : TextView = view.findViewById(R.id.state)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitAdapterAll.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fruit_si_new_item,parent,false)

        val viewHolder = ViewHolder(view)
        //点击姓名
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]

            if (fruit.state == "2"){
                val f_pholo = fruit.pholo
                val name = fruit.name
                val image = fruit.image

                dialogFragment.newInstance(f_pholo, name, image).show(fruit.fragmentManager, "")
            }else if (fruit.state == "1"){
                val f_id = fruit.aid.toString()
                val f_Cid = fruit.bid.toString()
                val f_pholo = fruit.pholo
                var bundle = Bundle()
                bundle.putString("shangjiaid",f_id)
                bundle.putString("shangping",f_Cid)
                bundle.putString("gopholo",f_pholo)
                //获取到该好友的id
                //var send_id = fruit.aid
                //跳转到好友信息页面
                val intent = Intent(parent.context, OrderMainActivity::class.java)

                intent.putExtras(bundle)

                //intent.putExtra("data_id", f_id)
                //把该好友的id传输到好友信息页面
                //intent.putExtra("touxiang_data", send_id)
                parent.context.startActivity(intent)
            }

        }

        //点击头像
        viewHolder.fruitImage.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]

            if (fruit.state == "2"){
                val f_pholo = fruit.pholo
                val name = fruit.name
                val image = fruit.image

                dialogFragment.newInstance(f_pholo, name, image).show(fruit.fragmentManager, "")
            }else if (fruit.state == "1"){
                val f_id = fruit.aid.toString()
                val f_Cid = fruit.bid.toString()
                val f_pholo = fruit.pholo
                var bundle = Bundle()
                bundle.putString("shangjiaid",f_id)
                bundle.putString("shangping",f_Cid)
                bundle.putString("gopholo",f_pholo)
                //获取到该好友的id
                //var send_id = fruit.aid
                //跳转到好友信息页面
                val intent = Intent(parent.context, OrderMainActivity::class.java)

                intent.putExtras(bundle)

                //intent.putExtra("data_id", f_id)
                //把该好友的id传输到好友信息页面
                //intent.putExtra("touxiang_data", send_id)
                parent.context.startActivity(intent)
            }

        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: FruitAdapterAll.ViewHolder, position: Int) {
        val fruit = fruitList[position]
        Glide.with(holder.itemView.context).load(fruit.image).into(holder.fruitImage)
        holder.fruitName.text = fruit.name
        holder.fruitmoney.text = "￥${fruit.money}"

        if (fruit.state == "1"){
            holder.state.text = "未支付"
        }else if (fruit.state == "2"){
            holder.state.text = "已支付"
        }else{
            holder.state.text = "已完成"
        }

    }

    override fun getItemCount() = fruitList.size

}

class AllFragment: Fragment() {

    private var binding: FragmentAllBinding? = null

    private val fruitListAll = ArrayList<FruitAll>()

    companion object{
        fun newInstance(pholo: String): AllFragment{
            val args = Bundle()
            val fragment = AllFragment()
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
        binding = FragmentAllBinding.inflate(inflater, container, false)

        initFruits()

        return binding?.root
    }

    private fun initFruits(){

        val pholo = arguments?.getString("pholo")

        val bmobQuery = BmobQuery<Order>()
        bmobQuery.findObjects(object : FindListener<Order>(){
            override fun done(p0: MutableList<Order>?, p1: BmobException?) {

                if (p1 == null){
                    if (p0 != null){

                        p0.forEach {
                            if (it.phone == pholo){
                                fruitListAll.add(FruitAll(it.commodityname, it.avatar, it.price, it.businessid, it.commodityid, pholo!!, it.state, childFragmentManager))
                            }
                        }

                        val layoutManager = LinearLayoutManager(requireContext())
                        //线性布局
                        binding?.recyclerView?.layoutManager = layoutManager
                        //完成适配器配置
                        val adapter = FruitAdapterAll(fruitListAll)
                        binding?.recyclerView?.adapter = adapter

                    }
                }else{
                    Toast.makeText(requireContext(), "查询失败", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "done123456789: ${p1?.message}, ${p1?.errorCode}")
                }

            }

        })

    }

}