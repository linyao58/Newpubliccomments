package com.example.newpubliccomments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newpubliccomments.message.ConversationListFragment
import com.example.newpubliccomments.tool.StatusBar
//import cn.bmob.v3.exception.BmobException
//import cn.bmob.v3.listener.SaveListener

class Fruits(val name:String, val imageId: Int, val aid: Int,val spholo: String)

class FruitAdapters(val fruitList : List<Fruits>) :
    RecyclerView.Adapter<FruitAdapters.ViewHolder>(){

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val fruitImage : ImageView = view.findViewById(R.id.fruitImage)
        val fruitName : TextView = view.findViewById(R.id.fruitName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitAdapters.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fruit_threr_item,parent,false)
        val viewHolder = ViewHolder(view)

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]

            val fruitid = fruit.aid
            val fruitpholo = fruit.spholo

            var bundle = Bundle()
            bundle.putString("gopholo",fruitpholo)
            bundle.putInt("data_id",fruitid)



            val intent = Intent(parent.context, SynopsisActivity::class.java)

            intent.putExtras(bundle)

            parent.context.startActivity(intent)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: FruitAdapters.ViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.fruitImage.setImageResource(fruit.imageId)
        holder.fruitName.text = fruit.name
    }

    override fun getItemCount() = fruitList.size





}


class home(intent: Intent) : Fragment(){
    var gopholo = intent.getStringExtra("gopholo").toString()

    private val fruitLists = ArrayList<Fruits>()
    @SuppressLint("WrongViewCast")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_home,container,false)

        var recyclerViews = view.findViewById(R.id.recyclerView) as androidx.recyclerview.widget.RecyclerView

        initFruits()

        val layoutManager = GridLayoutManager(context,2)
        //线性布局
        recyclerViews.layoutManager = layoutManager
        //完成适配器配置
        val adapter = FruitAdapters(fruitLists)
        recyclerViews.adapter = adapter


        var meishi = view.findViewById(R.id.Line_meishi) as LinearLayout
        meishi.setOnClickListener {
            val intent = Intent("com.example.newpubliccomment_Delicious.ACTION_START")
            intent.putExtra("delpholo",gopholo)
            startActivity(intent)
        }

        return view
    }



    private fun initFruits() {
        var s_pholo = gopholo
        val dbHelper = context?.let { MyDatabaseHelper(it, "Publiccomments.db",23) }
        val db = dbHelper?.writableDatabase
        val cursor = db?.query("Synopsis",null,null,null,null,null,null)
        if (cursor != null) {
            if(cursor.moveToFirst()){
                do {
                    val L_synopsis = cursor.getString(cursor.getColumnIndex("S_synopsis"))
                    val S_id = cursor.getInt(cursor.getColumnIndex("id"))
                    //val L_password = cursor.getString(cursor.getColumnIndex("userpassword"))
                    fruitLists.add(Fruits(L_synopsis,R.drawable.meijin,S_id,s_pholo))

                }while (cursor.moveToNext())
            }
        }

        if (cursor != null) {
            cursor.close()
        }

        
    }


}

class car : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_car,container,false)
    }
}

class setting(intent: Intent) : Fragment() {
    var gopholo = intent.getStringExtra("gopholo").toString()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_setting,container,false)

        var quxiao = view.findViewById(R.id.setting_quxiao) as TextView
        var edt_synopsis = view.findViewById(R.id.edt_synopsis) as EditText
        var Text_fabu = view.findViewById(R.id.Text_fabu) as TextView
        var vedioPhoto = view.findViewById<LinearLayout>(R.id.vediophoto)

        quxiao.setOnClickListener {
            val intent = Intent("com.example.newpubliccomment_Homepage.ACTION_START")
            intent.putExtra("gopholo",gopholo)
            startActivity(intent)
        }

        Text_fabu.setOnClickListener {
            var E_synopsis = edt_synopsis.text.toString()
            Log.e("显示",E_synopsis)
            val dbHelper = context?.let { it1 -> MyDatabaseHelper(it1, "Publiccomments.db",23) }
            val db = dbHelper?.writableDatabase
            val values1 = ContentValues().apply {
                put("userpholo",gopholo)
                put("S_synopsis",E_synopsis)
            }
            if (db != null) {
                db.insert("Synopsis",null,values1)
            }
            val intent = Intent("com.example.newpubliccomment_Homepage.ACTION_START")
            intent.putExtra("gopholo",gopholo)
            startActivity(intent)

        }

        vedioPhoto.setOnClickListener {
            Toast.makeText(it.context, "123465432", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}

class news : Fragment(){
    @SuppressLint("WrongViewCast")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_news,container,false)



        return view
    }

}

class accont(intent: Intent) : Fragment(){
    var gopholo = intent.getStringExtra("gopholo").toString()
    @SuppressLint("WrongViewCast")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_accont,container,false)
        var os = view.findViewById(R.id.Overall_situation) as LinearLayout
        var setting = view.findViewById(R.id.setting) as ImageView
        var dingdanshu = view.findViewById(R.id.dingdanxianshi) as LinearLayout

        setting.setOnClickListener {
            val intent = Intent("com.example.newpubliccomment_set.ACTION_START")
            intent.putExtra("setpholo",gopholo)
            startActivity(intent)
        }

        dingdanshu.setOnClickListener {
            val intent = Intent("com.example.newpublicXianshiOrder.ACTION_START")
            intent.putExtra("setpholo",gopholo)
            startActivity(intent)
        }

        if (gopholo == ""){
            var click : TextView = view.findViewById(R.id.but_click) as TextView
            var zushi : TextView = view.findViewById(R.id.zushi) as TextView
            var accont_tou : de.hdodenhof.circleimageview.CircleImageView = view.findViewById(R.id.accont_touxiang) as de.hdodenhof.circleimageview.CircleImageView
            click.text = "点击登录"
            zushi.text = "登录更精彩"
            accont_tou.setImageResource(R.drawable.touxiang)
            os.setOnClickListener {
                val intent = Intent("com.example.newpubliccomment_LoginActivity.ACTION_START")
                startActivity(intent)
            }
        }else{
            var click : TextView = view.findViewById(R.id.but_click) as TextView
            var zushi : TextView = view.findViewById(R.id.zushi) as TextView
            var geren : TextView = view.findViewById(R.id.geren) as TextView
            var accont_tou : de.hdodenhof.circleimageview.CircleImageView = view.findViewById(R.id.accont_touxiang) as de.hdodenhof.circleimageview.CircleImageView
            click.text = gopholo
            zushi.text = "粉丝|关注"
            geren.text = "个人主页>"
            accont_tou.setImageResource(R.drawable.jiutou)
            os.setOnClickListener {
                val intent = Intent("com.example.newpubliccomment_Peplo.ACTION_START")
                startActivity(intent)
            }
        }

        return view
    }
}

class Homepage : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

//        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
//        设置状态栏图标颜色
        StatusBar().statusBarTextColor(this, true)

        var gopholo = intent.getStringExtra("gopholo").toString()
        Log.e("xianshi",gopholo)

        val icon1: RelativeLayout =findViewById(R.id.icon1)
        val icon2: RelativeLayout =findViewById(R.id.icon2)
        val icon3: RelativeLayout =findViewById(R.id.icon3)
        val icon4: RelativeLayout =findViewById(R.id.icon4)
        val icon5: RelativeLayout =findViewById(R.id.icon5)
        val image1: ImageView =findViewById(R.id.iconimage1)
        val image2: ImageView =findViewById(R.id.iconimage2)
        val image3: ImageView =findViewById(R.id.iconimage3)
        val image4: ImageView =findViewById(R.id.iconimage4)
        val image5: ImageView =findViewById(R.id.iconimage5)
        val text1: TextView =findViewById(R.id.icontext1)
        val text2: TextView =findViewById(R.id.icontext2)
        val text3: TextView =findViewById(R.id.icontext3)
        val text4: TextView =findViewById(R.id.icontext4)
        val text5: TextView =findViewById(R.id.icontext5)

        /*val frag1 = fragHome()
        val frag2 = fragChess()
        val frag3 = fragUser()*/

        image1.setImageResource(R.drawable.home)
        image1.setColorFilter(Color.parseColor("#d81e06"))
        text1.setTextColor(Color.parseColor("#d81e06"))
        text2.setTextColor(Color.parseColor("#8E8E8E"))
        text3.setTextColor(Color.parseColor("#8E8E8E"))
        text4.setTextColor(Color.parseColor("#8E8E8E"))
        text5.setTextColor(Color.parseColor("#8E8E8E"))
        fraghome(home(intent))
        icon1.setOnClickListener{

            image1.setImageResource(R.drawable.home)
            image2.setImageResource(R.drawable.location)
            image3.setImageResource(R.drawable.newadd)
            image4.setImageResource(R.drawable.news)
            image5.setImageResource(R.drawable.account)

            image1.setColorFilter(Color.parseColor("#d81e06"))
            image2.setColorFilter(Color.parseColor("#8E8E8E"))
            image3.setColorFilter(Color.parseColor("#8E8E8E"))
            image4.setColorFilter(Color.parseColor("#8E8E8E"))
            image5.setColorFilter(Color.parseColor("#8E8E8E"))

            text1.setTextColor(Color.parseColor("#d81e06"))
            text2.setTextColor(Color.parseColor("#8E8E8E"))
            text3.setTextColor(Color.parseColor("#8E8E8E"))
            text4.setTextColor(Color.parseColor("#8E8E8E"))
            text5.setTextColor(Color.parseColor("#8E8E8E"))

            fraghome(home(intent))


        }

        icon2.setOnClickListener{
            image1.setImageResource(R.drawable.home)
            image2.setImageResource(R.drawable.location)
            image3.setImageResource(R.drawable.newadd)
            image4.setImageResource(R.drawable.news)
            image5.setImageResource(R.drawable.account)

            image1.setColorFilter(Color.parseColor("#8E8E8E"))
            image2.setColorFilter(Color.parseColor("#d81e06"))
            image3.setColorFilter(Color.parseColor("#8E8E8E"))
            image4.setColorFilter(Color.parseColor("#8E8E8E"))
            image5.setColorFilter(Color.parseColor("#8E8E8E"))

            text1.setTextColor(Color.parseColor("#8E8E8E"))
            text2.setTextColor(Color.parseColor("#d81e06"))
            text3.setTextColor(Color.parseColor("#8E8E8E"))
            text4.setTextColor(Color.parseColor("#8E8E8E"))
            text5.setTextColor(Color.parseColor("#8E8E8E"))

            fragChess(car())
        }

        icon3.setOnClickListener{

            image1.setImageResource(R.drawable.home)
            image2.setImageResource(R.drawable.location)
            image3.setImageResource(R.drawable.newadd)
            image4.setImageResource(R.drawable.news)
            image5.setImageResource(R.drawable.account)

            image1.setColorFilter(Color.parseColor("#8E8E8E"))
            image2.setColorFilter(Color.parseColor("#8E8E8E"))
            image3.setColorFilter(Color.parseColor("#d81e06"))
            image4.setColorFilter(Color.parseColor("#8E8E8E"))
            image5.setColorFilter(Color.parseColor("#8E8E8E"))

            text1.setTextColor(Color.parseColor("#8E8E8E"))
            text2.setTextColor(Color.parseColor("#8E8E8E"))
            text3.setTextColor(Color.parseColor("#d81e06"))
            text4.setTextColor(Color.parseColor("#8E8E8E"))
            text5.setTextColor(Color.parseColor("#8E8E8E"))

            if (gopholo != ""){
                fragUser(setting(intent))
            }else{
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }

        }

        icon4.setOnClickListener{

            image1.setImageResource(R.drawable.home)
            image2.setImageResource(R.drawable.location)
            image3.setImageResource(R.drawable.newadd)
            image4.setImageResource(R.drawable.news)
            image5.setImageResource(R.drawable.account)

            image1.setColorFilter(Color.parseColor("#8E8E8E"))
            image2.setColorFilter(Color.parseColor("#8E8E8E"))
            image3.setColorFilter(Color.parseColor("#8E8E8E"))
            image4.setColorFilter(Color.parseColor("#d81e06"))
            image5.setColorFilter(Color.parseColor("#8E8E8E"))

            text1.setTextColor(Color.parseColor("#8E8E8E"))
            text2.setTextColor(Color.parseColor("#8E8E8E"))
            text3.setTextColor(Color.parseColor("#8E8E8E"))
            text4.setTextColor(Color.parseColor("#d81e06"))
            text5.setTextColor(Color.parseColor("#8E8E8E"))

            if (gopholo != ""){
//                fragnews(news())
//              进入消息页面
                fragnews(ConversationListFragment.newInstance())
                
            }else{
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }

        }

        icon5.setOnClickListener{

            image1.setImageResource(R.drawable.home)
            image2.setImageResource(R.drawable.location)
            image3.setImageResource(R.drawable.newadd)
            image4.setImageResource(R.drawable.news)
            image5.setImageResource(R.drawable.account)

            image1.setColorFilter(Color.parseColor("#8E8E8E"))
            image2.setColorFilter(Color.parseColor("#8E8E8E"))
            image3.setColorFilter(Color.parseColor("#8E8E8E"))
            image4.setColorFilter(Color.parseColor("#8E8E8E"))
            image5.setColorFilter(Color.parseColor("#d81e06"))

            text1.setTextColor(Color.parseColor("#8E8E8E"))
            text2.setTextColor(Color.parseColor("#8E8E8E"))
            text3.setTextColor(Color.parseColor("#8E8E8E"))
            text4.setTextColor(Color.parseColor("#8E8E8E"))
            text5.setTextColor(Color.parseColor("#d81e06"))

            fragaccont(accont(intent))

        }

    }




    private fun fragaccont(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frag,fragment)
        transaction.commit()
    }

    private fun fragnews(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frag,fragment)
        transaction.commit()
    }

    private fun fragUser(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frag,fragment)
        transaction.commit()
    }

    private fun fragChess(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frag,fragment)
        transaction.commit()
    }

    private fun fraghome(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frag,fragment)
        transaction.commit()
    }

}