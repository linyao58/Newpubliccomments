package com.example.newpubliccomments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_business_main.*
import kotlinx.android.synthetic.main.activity_deliciousfood.*

class Fruitshang(val name:String, val imageId: Int, val money: String,val aid : Int,val bid : Int,val pholo : String)

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
        holder.fruitImage.setImageResource(fruit.imageId)
        holder.fruitName.text = fruit.name
        holder.fruitmoney.text = fruit.money
    }

    override fun getItemCount() = fruitList.size

}

class BusinessMainActivity : AppCompatActivity() {

    private val fruitListshang = ArrayList<Fruitshang>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_main)

        var bundle = intent.extras
        var getpholos = bundle?.getString("gopholo").toString()
        var getdata_id = bundle?.getString("data_id").toString()

        //val getdata_id = intent.getStringExtra("data_id")
        val getdata_ids = getdata_id?.toInt()

        val dbHelper = MyDatabaseHelper(this, "Publiccomments.db",23)
        val db = dbHelper.writableDatabase
        val cursor = db.query("Business",null,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do {
                val b_name = cursor.getString(cursor.getColumnIndex("B_name"))
                val b_profile = cursor.getString(cursor.getColumnIndex("B_profile"))

                val b_id = cursor.getInt(cursor.getColumnIndex("id"))
                if (getdata_ids == b_id){
                    biaoti.text = b_name
                    jianj.text = b_profile
                }
            }while (cursor.moveToNext())
        }

        cursor.close()


        initFruits()
        val layoutManager = LinearLayoutManager(this)
        //线性布局
        recyclerViewshang.layoutManager = layoutManager
        //完成适配器配置
        val adapter = FruitAdaptershang(fruitListshang)
        recyclerViewshang.adapter = adapter

    }

    private fun initFruits() {

        var bundle = intent.extras
        var getpholos = bundle?.getString("gopholo").toString()

        val getdata_id = intent.getStringExtra("data_id")
        val getdata_ids = getdata_id?.toInt()

        val dbHelper = MyDatabaseHelper(this, "Publiccomments.db",23)
        val db = dbHelper.writableDatabase
        val cursor = db.query("Commodity",null,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do {
                val c_name = cursor.getString(cursor.getColumnIndex("C_name"))
                val c_profile = cursor.getString(cursor.getColumnIndex("C_profile"))
                val c_shen = cursor.getString(cursor.getColumnIndex("C_shenhe"))
                val c_id = cursor.getInt(cursor.getColumnIndex("B_id"))
                val c_quanjuid = cursor.getInt(cursor.getColumnIndex("id"))
                if (getdata_ids == c_id){
                    if (c_shen == "true"){
                        fruitListshang.add(Fruitshang(c_name,R.drawable.naicha,c_profile,c_id,c_quanjuid,getpholos))
                    }
                }
            }while (cursor.moveToNext())
        }

        cursor.close()

    }

    override fun   onCreateOptionsMenu(menu: Menu?):Boolean{
        menuInflater.inflate(R.menu.toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var bundle = intent.extras
        var getpholos = bundle?.getString("gopholo").toString()


        val intent = Intent(this,DeliciousfoodActivity::class.java)
        intent.putExtra("delpholo",getpholos)
        when (item.itemId){
            R.id.backup ->
                startActivity(intent)
            R.id.sshoucang -> Toast.makeText(this,"收藏",Toast.LENGTH_SHORT).show()
            R.id.quanbu -> Toast.makeText(this,"全部",Toast.LENGTH_SHORT).show()
        }

        return true

    }

}