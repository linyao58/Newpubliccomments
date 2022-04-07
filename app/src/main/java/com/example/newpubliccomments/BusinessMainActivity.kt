package com.example.newpubliccomments

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener
import com.bumptech.glide.Glide
import com.example.newpubliccomments.business.Business
import com.example.newpubliccomments.commodity.Commodity
import com.example.newpubliccomments.databinding.ActivityBusinessMainBinding
import com.example.newpubliccomments.tool.StatusBar
import kotlinx.android.synthetic.main.activity_business_main.*
import kotlinx.android.synthetic.main.activity_deliciousfood.*

//      aid:商家id    bid:商品id
class Fruitshang(val name:String, val image: String, val money: String,val aid : String,val bid : String,val pholo : String)

class FruitAdaptershang(val fruitList: ArrayList<Fruitshang>) :
    RecyclerView.Adapter<FruitAdaptershang.ViewHolder>(){

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val fruitImage : ImageView = view.findViewById(R.id.fruitImage)
        val fruitName : TextView = view.findViewById(R.id.fruitName)
        val fruitmoney : TextView = view.findViewById(R.id.fruitmoney)
        val qianggou : Button = view.findViewById(R.id.qinggou)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitAdaptershang.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fruit_si_item,parent,false)

        val viewHolder = ViewHolder(view)
        //点击姓名
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]

            val f_id = fruit.aid.toString()
            val f_Cid = fruit.bid.toString()
            val f_pholo = fruit.pholo
            var bundle = Bundle()
            bundle.putString("shangjiaid",f_id)
            bundle.putString("shangping",f_Cid)
            bundle.putString("gopholo",f_pholo)
            //获取到该好友的id
            //val c_id = fruit.aid
            //跳转到聊天页面
            val intent = Intent(parent.context, OrderMainActivity::class.java)

            intent.putExtras(bundle)
            //intent.putExtra("data_id", f_id)
            //把获取到该好友的id传输到聊天页面
            //intent.putExtra("chat_data_id", c_id)
            parent.context.startActivity(intent)
        }

        //点击头像
        viewHolder.fruitImage.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]

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

        viewHolder.qianggou.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]
            val f_id = fruit.aid.toString()
            val f_Cid = fruit.bid.toString()
            val f_pholo = fruit.pholo

            var bundle = Bundle()
            bundle.putString("shangjiaid",f_id)
            bundle.putString("shangping",f_Cid)
            bundle.putString("gopholo",f_pholo)
            val intent = Intent(parent.context,OrderMainActivity::class.java)
            intent.putExtras(bundle)
            parent.context.startActivity(intent)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: FruitAdaptershang.ViewHolder, position: Int) {
        val fruit = fruitList[position]
        Glide.with(holder.itemView.context).load(fruit.image).into(holder.fruitImage)
        holder.fruitName.text = fruit.name
        holder.fruitmoney.text = fruit.money
    }

    override fun getItemCount() = fruitList.size

}

class BusinessMainActivity : AppCompatActivity() {

    private val fruitListshang = ArrayList<Fruitshang>()

    private var binding: ActivityBusinessMainBinding? = null

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
//        设置状态栏图标颜色
        StatusBar().statusBarTextColor(this, true)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_business_main)

        var bundle = intent.extras
        var getpholos = bundle?.getString("gopholo").toString()
        var getdata_id = bundle?.getString("data_id").toString()

        val bmobQuery = BmobQuery<Business>()
        bmobQuery.getObject(getdata_id, object : QueryListener<Business>(){
            override fun done(p0: Business?, p1: BmobException?) {
                if (p1 == null){
                    if (p0 != null){
                        Glide.with(this@BusinessMainActivity).load(p0.bavatar).into(binding?.avatar!!)
                        binding?.biaoti?.text = p0.title
                        binding?.jianj?.text = p0.introduce
                        binding?.type = p0.collection
                        binding?.address?.text = p0.address
                    }
                }else{
                    Toast.makeText(this@BusinessMainActivity, "查询失败", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "done123456789: ${p1?.message}, ${p1?.errorCode}")
                }
            }

        })

        initFruits()


        binding?.back?.setOnClickListener {
            onBackPressed()
        }

        binding?.all?.setOnClickListener {
            Toast.makeText(this, "全部", Toast.LENGTH_SHORT).show()
        }

        binding?.collection?.setOnClickListener {

            var getdata_id = bundle?.getString("data_id").toString()

            val bmobQuery = BmobQuery<Business>()
            bmobQuery.getObject(getdata_id, object : QueryListener<Business>(){
                override fun done(p0: Business?, p1: BmobException?) {
                    if (p1 == null){
                        if (p0 != null){
                            val business = Business()
                            if (p0.collection){
                                business.collection = false
                                binding?.type = false
                                Toast.makeText(this@BusinessMainActivity, "取消收藏", Toast.LENGTH_SHORT).show()
                            }else{
                                business.collection = true
                                binding?.type = true
                                Toast.makeText(this@BusinessMainActivity, "已收藏", Toast.LENGTH_SHORT).show()
                            }

                            business.update(getdata_id, object : UpdateListener(){
                                override fun done(e: BmobException?) {
                                    if (e == null){
                                        Log.e("TAG", "done123456789: 修改成功")
                                    }else{
                                        Log.e("TAG", "done123456789: 修改失败")
                                    }
                                }

                            })

                        }
                    }else{
                        Toast.makeText(this@BusinessMainActivity, "查询失败", Toast.LENGTH_SHORT).show()
                        Log.e("TAG", "done123456789: ${p1?.message}, ${p1?.errorCode}")
                    }
                }

            })

        }

    }

    private fun initFruits() {

        var bundle = intent.extras
        var getpholos = bundle?.getString("gopholo").toString()

        val getdata_id = intent.getStringExtra("data_id").toString()

        val bmobQuery = BmobQuery<Commodity>()
        bmobQuery.findObjects(object : FindListener<Commodity>(){
            override fun done(p0: MutableList<Commodity>?, p1: BmobException?) {
                if (p1 == null){

                    if (p0 != null){
                        p0.forEach {
                            if (it.businessid == getdata_id){
                                fruitListshang.add(Fruitshang(it.commodityname, it.avatar, it.price, it.businessid, it.objectId, getpholos))
                            }
                        }

                        val layoutManager = LinearLayoutManager(this@BusinessMainActivity)
                        //线性布局
                        binding?.recyclerViewshang?.layoutManager = layoutManager
                        //完成适配器配置
                        val adapter = FruitAdaptershang(fruitListshang)
                        binding?.recyclerViewshang?.adapter = adapter

                    }

                }else{
                    Toast.makeText(this@BusinessMainActivity, "查询失败", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "done123456789: ${p1?.message}, ${p1?.errorCode}")
                }
            }

        })

    }

//    override fun  onCreateOptionsMenu(menu: Menu?):Boolean{
//        menuInflater.inflate(R.menu.toolbar,menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        var bundle = intent.extras
//        var getpholos = bundle?.getString("gopholo").toString()
//
//
//        val intent = Intent(this,DeliciousfoodActivity::class.java)
//        intent.putExtra("delpholo",getpholos)
//        when (item.itemId){
//            R.id.backup ->
////                startActivity(intent)
//                onBackPressed()
//            R.id.sshoucang -> Toast.makeText(this,"收藏",Toast.LENGTH_SHORT).show()
//            R.id.quanbu -> Toast.makeText(this,"全部",Toast.LENGTH_SHORT).show()
//        }
//
//        return true
//
//    }

}