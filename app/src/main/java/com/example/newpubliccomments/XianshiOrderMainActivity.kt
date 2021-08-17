package com.example.newpubliccomments

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
import kotlinx.android.synthetic.main.activity_xianshi_order_main.*

class Fruitorder(val money:String, val imageId: Int, val zhuagntai: String,val aid : String,val bid : String,val pholo : String)

class FruitAdapterorder(val fruitList : ArrayList<Fruitorder>) :
    RecyclerView.Adapter<FruitAdapterorder.ViewHolder>(){
    inner class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val fruitImage : ImageView = view.findViewById(R.id.fruitImagexian)
        val fruitName : TextView = view.findViewById(R.id.fruitNamexian)
        val fruitzhuagn : TextView = view.findViewById(R.id.fruitzhifus)
        val fruitshangjia : TextView = view.findViewById(R.id.fruitshangjiahao)
        val fruitshangping : TextView = view.findViewById(R.id.fruitshangpinghao)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fruit_wu_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.fruitImage.setImageResource(fruit.imageId)
        holder.fruitName.text = fruit.money
        holder.fruitzhuagn.text = fruit.zhuagntai
        holder.fruitshangjia.text = fruit.aid
        holder.fruitshangping.text = fruit.bid
    }


    override fun getItemCount() = fruitList.size

}


class XianshiOrderMainActivity : AppCompatActivity() {

    private val fruitList = ArrayList<Fruitorder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xianshi_order_main)

        var xianpholo = intent.getStringExtra("setpholo")

        initFruits()

        val layoutManager = LinearLayoutManager(this)

        recyclerViewxian.layoutManager = layoutManager

        val adapter = FruitAdapterorder(fruitList)

        recyclerViewxian.adapter = adapter

    }

    private fun initFruits() {
        var c_id = ""
        var b_id = ""
        var b_name = ""
        var c_name = ""
        var c_moneyc = ""
        var xianpholo = intent.getStringExtra("setpholo").toString()
        val dbHelper = MyDatabaseHelper(this, "Publiccomments.db",23)
        val db = dbHelper.writableDatabase
        val cursor = db.query("Orders",null,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do {
                val O_ph = cursor.getString(cursor.getColumnIndex("O_pholo"))
                val O_states = cursor.getString(cursor.getColumnIndex("O_state"))
                val c_ids = cursor.getInt(cursor.getColumnIndex("C_id"))
                val b_ids = cursor.getInt(cursor.getColumnIndex("B_id"))
                c_id = c_ids.toString()
                b_id = b_ids.toString()
                if (O_ph == xianpholo){

                    val cursors = db.query("Business",null,null,null,null,null,null)
                    if(cursors.moveToFirst()){
                        do {


                            val b_ids = cursors.getInt(cursors.getColumnIndex("id")).toString()

                            if (b_ids == b_id){
                                 b_name = cursors.getString(cursors.getColumnIndex("B_name"))
                                Log.e("数据",b_name)

                                val cursorshang = db.query("Commodity",null,null,null,null,null,null)
                                if(cursorshang.moveToFirst()){
                                    do {


                                        val c_ids = cursorshang.getInt(cursorshang.getColumnIndex("id")).toString()

                                        if (c_ids == c_id){
                                            c_name = cursorshang.getString(cursorshang.getColumnIndex("C_name"))
                                            c_moneyc = cursorshang.getString(cursorshang.getColumnIndex("C_profile"))
                                            Log.e("数据是",c_name)
                                            Log.e("数据只是",c_moneyc)
                                            fruitList.add(Fruitorder(c_moneyc,R.drawable.naicha,O_states,b_name,c_name,O_ph))

                                        }


                                    }while (cursorshang.moveToNext())
                                }

                                cursorshang.close()

                            }


                        }while (cursors.moveToNext())
                    }

                    cursors.close()


                }

            }while (cursor.moveToNext())
        }

        cursor.close()


        Del_backxiaxian.setOnClickListener {
            val intent = Intent("com.example.newpubliccomment_Homepage.ACTION_START")
            intent.putExtra("gopholo",xianpholo)
            startActivity(intent)
        }


    }
}