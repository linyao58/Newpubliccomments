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
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newpubliccomments.tool.StatusBar
import kotlinx.android.synthetic.main.activity_deliciousfood.*
import kotlinx.android.synthetic.main.activity_deliciousfood.Del_back
import kotlinx.android.synthetic.main.activity_deliciousfood.recyclerView
import kotlinx.android.synthetic.main.activity_password.*
import kotlinx.android.synthetic.main.fragment_deliciousfood.*

class Fruit(val name:String, val imageId: Int, val profile: String,val aid : Int,var pholo : String)

class FruitAdapter(val fruitList: ArrayList<Fruit>) :
    RecyclerView.Adapter<FruitAdapter.ViewHolder>(){

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val fruitImage : ImageView = view.findViewById(R.id.fruitImage)
        val fruitName : TextView = view.findViewById(R.id.fruitName)
        val fruitProfile : TextView = view.findViewById(R.id.fruitProfile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fruit_towitem,parent,false)

        val viewHolder = ViewHolder(view)

        //点击姓名
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]

            val f_id = fruit.aid.toString()
            var f_pholo = fruit.pholo

            var bundle = Bundle()
            bundle.putString("gopholo",f_pholo)
            bundle.putString("data_id",f_id)


            //获取到该好友的id
            //val c_id = fruit.aid
            //跳转到聊天页面
            val intent = Intent(parent.context, BusinessMainActivity::class.java)

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
        holder.fruitImage.setImageResource(fruit.imageId)
        holder.fruitName.text = fruit.name
        holder.fruitProfile.text = fruit.profile
    }

    override fun getItemCount() = fruitList.size



}

class DeliciousfoodActivity : AppCompatActivity() {

    private val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_deliciousfood)

        //        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
//        设置状态栏图标颜色
        StatusBar().statusBarTextColor(this, true)

        var gopholo = intent.getStringExtra("delpholo").toString()

        Del_back.setOnClickListener {
//            val intent = Intent("com.example.newpubliccomment_Homepage.ACTION_START")
//            intent.putExtra("gopholo",gopholo)
//            startActivity(intent)

            onBackPressed()

        }

        location.setOnClickListener {
            Homepage().start(it.context, 3, 0.00, 0.00)
        }

        initFruits()
        val layoutManager = LinearLayoutManager(this)
        //线性布局
        recyclerView.layoutManager = layoutManager
        //完成适配器配置
        val adapter = FruitAdapter(fruitList)
        recyclerView.adapter = adapter
    }

    private fun initFruits() {
        var gopholo = intent.getStringExtra("delpholo").toString()
        val dbHelper = MyDatabaseHelper(this, "Publiccomments.db",23)
        val db = dbHelper.writableDatabase
        val cursor = db.query("Business",null,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do {
                val b_name = cursor.getString(cursor.getColumnIndex("B_name"))
                val b_profile = cursor.getString(cursor.getColumnIndex("B_profile"))
                val b_shenhe = cursor.getString(cursor.getColumnIndex("B_shenhe"))
                val b_id = cursor.getInt(cursor.getColumnIndex("id"))
                if (b_shenhe == "true"){
                    fruitList.add(Fruit(b_name,R.drawable.naicha,b_profile,b_id,gopholo))
                }
            }while (cursor.moveToNext())
        }

        cursor.close()

    }

    fun start(context: Context, gopholo: String){
        val intent = Intent(context, DeliciousfoodActivity::class.java)
        intent.putExtra("delpholo",gopholo)
        context.startActivity(intent)
    }

}