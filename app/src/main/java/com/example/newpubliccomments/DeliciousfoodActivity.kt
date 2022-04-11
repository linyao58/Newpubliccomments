package com.example.newpubliccomments

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.bumptech.glide.Glide
import com.example.newpubliccomments.business.Business
import com.example.newpubliccomments.databinding.FragmentDeliciousfoodBinding
import com.example.newpubliccomments.tool.StatusBar
import kotlinx.android.synthetic.main.activity_deliciousfood.*
import kotlinx.android.synthetic.main.activity_deliciousfood.Del_back
import kotlinx.android.synthetic.main.activity_deliciousfood.recyclerView
import kotlinx.android.synthetic.main.activity_password.*
import kotlinx.android.synthetic.main.fragment_deliciousfood.*

class Fruit(val name:String, val image: String, val profile: String,val aid : String,var pholo : String, var rating: Float)

class FruitAdapter(val fruitList: List<Fruit>) :
    RecyclerView.Adapter<FruitAdapter.ViewHolder>(){

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val fruitImage : ImageView = view.findViewById(R.id.fruitImage)
        val fruitName : TextView = view.findViewById(R.id.fruitName)
        val fruitProfile : TextView = view.findViewById(R.id.fruitProfile)
        val fruitRating: RatingBar = view.findViewById(R.id.rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fruit_towitem,parent,false)

        val viewHolder = ViewHolder(view)

//            商家详情
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]

            val f_id = fruit.aid
            var f_pholo = fruit.pholo

            var bundle = Bundle()
            bundle.putString("gopholo",f_pholo)
            bundle.putString("data_id",f_id)

            val intent = Intent(parent.context, BusinessMainActivity::class.java)
            intent.putExtras(bundle)
            parent.context.startActivity(intent)
        }

//            商家详情
        viewHolder.fruitImage.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]

            val f_id = fruit.aid
            var f_pholo = fruit.pholo

            var bundle = Bundle()
            bundle.putString("gopholo",f_pholo)
            bundle.putString("data_id",f_id)
            //获取到该好友的id
            //var send_id = fruit.aid
            //跳转到好友信息页面
            val intent = Intent(parent.context, BusinessMainActivity::class.java)

            intent.putExtras(bundle)
            //intent.putExtra("data_id", f_id)
            //把该好友的id传输到好友信息页面
            //intent.putExtra("touxiang_data", send_id)
            parent.context.startActivity(intent)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: FruitAdapter.ViewHolder, position: Int) {
        val fruit = fruitList[position]
        Glide.with(holder.itemView.context).load(fruit.image).into(holder.fruitImage)
        holder.fruitName.text = fruit.name
        holder.fruitProfile.text = fruit.profile
        holder.fruitRating.rating = fruit.rating
    }

    override fun getItemCount() = fruitList.size

}

class DeliciousfoodActivity : BaseActivity() {

    private val fruitList = ArrayList<Fruit>()

    private var binding: FragmentDeliciousfoodBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.fragment_deliciousfood)

        //        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
//        设置状态栏图标颜色
        StatusBar().statusBarTextColor(this, true)

        var gopholo = intent.getStringExtra("delpholo").toString()

        binding?.DelBack?.setOnClickListener {
//            val intent = Intent("com.example.newpubliccomment_Homepage.ACTION_START")
//            intent.putExtra("gopholo",gopholo)
//            startActivity(intent)

            onBackPressed()

        }

        binding?.location?.setOnClickListener {
            Homepage().start(it.context, 3, 0.00, 0.00, gopholo)
        }

        initFruits()
//        val layoutManager = LinearLayoutManager(this)
//        //线性布局
//        binding?.recyclerView?.layoutManager = layoutManager
//        //完成适配器配置
//        val adapter = FruitAdapter(fruitList)
//        binding?.recyclerView?.adapter = adapter

    }


    private fun initFruits() {
        var gopholo = intent.getStringExtra("delpholo").toString()

        val bmobQuery = BmobQuery<Business>()
        bmobQuery.findObjects(object : FindListener<Business>(){
            override fun done(p0: MutableList<Business>?, p1: BmobException?) {
                if (p1 == null){
                    if (p0 != null){
                        p0.forEach {
                            var title = it.title
                            var bavatar = it.bavatar
                            var introduce = it.introduce
                            var id = it.objectId
                            var grade = it.grade
                            fruitList.add(Fruit(title, bavatar, introduce, id, gopholo, grade.toFloat()))
                        }

                        val layoutManager = LinearLayoutManager(this@DeliciousfoodActivity)
                        //线性布局
                        binding?.recyclerView?.layoutManager = layoutManager
                        //完成适配器配置
                        val adapter = FruitAdapter(fruitList)
                        binding?.recyclerView?.adapter = adapter

                    }

//                    Toast.makeText(this@DeliciousfoodActivity, "查询成功${p0?.get(0)?.bavatar}", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@DeliciousfoodActivity, "查询失败", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "done123456789: ${p1?.message}, ${p1?.errorCode}")
                }
            }

        })

    }

    fun start(context: Context, gopholo: String){
        val intent = Intent(context, DeliciousfoodActivity::class.java)
        intent.putExtra("delpholo",gopholo)
        context.startActivity(intent)
    }

}